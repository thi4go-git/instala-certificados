package br.com.instalador.controle;

import br.com.instalador.model.entity.Certificado;
import br.com.instalador.utils.ArquivoUtils;
import br.com.instalador.view.PROGRESSO.Progresso;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class CERTIFICADOS_PASTA {

    CERTIFICADO_CLASS certClass = new CERTIFICADO_CLASS();
    //
    List<String> LISTA_CERTIFICADOS_SENHA_DIVERGENTE;
    List<String> LISTA_CERTIFICADOS_SENHA_CORRETA;
    List<String> LISTA_CERTIFICADOS_JA_EXISTENTES_BD;
    //
    private final String DIVERGENTE = "SENHA_DIVERGENTE          ";

    //
    public void atualizarCertificadosBD(String CAMINHO_PASTA_CERTIFICADOS,
            String senhaCert, TelaController controle) {
        //
        LISTA_CERTIFICADOS_SENHA_DIVERGENTE = new ArrayList<>();
        LISTA_CERTIFICADOS_SENHA_CORRETA = new ArrayList<>();
        LISTA_CERTIFICADOS_JA_EXISTENTES_BD = new ArrayList<>();
        //
        Progresso p = new Progresso();
        p.definirLimites(1, 100);
        p.atualizarBarra(1, "AGUARDE... CARREGANDO CERTIFICADOS NA PASTA!!!");
        //-------------------------------------------
        File PASTA_CERTIFICADOS = new File(CAMINHO_PASTA_CERTIFICADOS);
        String[] extensions = new String[]{"pfx"};
        List<File> PFXS = (List<File>) FileUtils.listFiles(PASTA_CERTIFICADOS, extensions, true);
        //------------------------------------        
        p.definirLimites(1, PFXS.size());
        int cont = 0;
        int instalados = 0;
        for (File arquivoPFX_ATUAL : PFXS) {
            cont++;
            //
            String dtVenc = certClass.retornarVencimentoCERTIFICADO(arquivoPFX_ATUAL.getAbsolutePath(), senhaCert);
            String format = "EEE MMM dd HH:mm:ss zzz yyyy";
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            Date dtVencimento;
            //-- Tue Nov 24 17:30:00 BRT 2020
            if (!dtVenc.contains("keystore password was incorrect")) {
                try {
                    //
                    LISTA_CERTIFICADOS_SENHA_CORRETA.add(arquivoPFX_ATUAL.getAbsolutePath());
                    //
                    dtVencimento = dateFormat.parse(dtVenc);
                    Certificado pfx = new Certificado();
                    pfx.setNOME(arquivoPFX_ATUAL.getName());
                    p.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + PFXS.size() + ") - " + pfx.getNOME());
                    pfx.setALIAS(certClass.retornarNomeOriginalCertificado(arquivoPFX_ATUAL.getAbsolutePath(), senhaCert));
                    java.sql.Date dats = new java.sql.Date(dtVencimento.getTime());
                    pfx.setDT_VENCIMENTO(dats);
                    pfx.setHORA_VENCIMENTO(new SimpleDateFormat("HH:mm:ss").format(dtVencimento));
                    pfx.setDESC_VENCIMENTO("DESCRIÇÃO");
                    pfx.setEXPIRA(0);
                    //CERTIFICADO EM BYTES 
                    pfx.setBYTES_CERTIFICADO(ArquivoUtils.fileTobyte(new File(arquivoPFX_ATUAL.getAbsolutePath())));
                    if (controle.consultarExistenciaCertifiado(pfx) != true) {
                        //CADASTRA NO BANCO
                        if (controle.inserirCertificado(pfx) == true) {
                            instalados++;
                            p.atualizarBarra(cont, "SUCESSO AO SALVAR NO BANCO: " + pfx.getNOME());
                        } else {
                            p.atualizarBarra(cont, "ERRO AO SALVAR NO BANCO: " + pfx.getNOME());
                            JOptionPane.showMessageDialog(null, "ERRO AO SALVAR NO BANCO: " + pfx.getNOME());
                        }
                    } else {
                        //Certificados que já existem no BD são colcoados em uma lista
                        LISTA_CERTIFICADOS_JA_EXISTENTES_BD.add(arquivoPFX_ATUAL.getAbsolutePath());
                    }
                } catch (ParseException ex) {
                    System.out.println(ex);
                }
            } else {
                //Certificados com senha divergente são adicionados em uma lista
                LISTA_CERTIFICADOS_SENHA_DIVERGENTE.add(arquivoPFX_ATUAL.getAbsolutePath());
            }

        }//fim do laço de repetição-------------------------------------------
        JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CONCLUÍDA!!!");
        //
        JOptionPane.showMessageDialog(null,
                "\n********************RESUMO********************"
                + " \n Certificados Processados:      " + PFXS.size() + ""
                + " \n Certificados Instalados:       " + instalados
                + " \n Certificados senha Divergente: " + LISTA_CERTIFICADOS_SENHA_DIVERGENTE.size()
                + " \n Certificados Já existentes:    " + LISTA_CERTIFICADOS_JA_EXISTENTES_BD.size()
                + " \n*******************************************");

        //
        organizarSenhasDivergente(CAMINHO_PASTA_CERTIFICADOS);
        p.dispose();
    }

    public void renomearSenhaDivergente(String CAMINHO_PASTA_CERTIFICADOS) {
        if (!LISTA_CERTIFICADOS_SENHA_DIVERGENTE.isEmpty()) {
            organizarSenhasDivergente(CAMINHO_PASTA_CERTIFICADOS);
        }
    }

    public void organizarSenhasDivergente(String CAMINHO_PASTA_CERTIFICADOS) {

        int response = JOptionPane.showConfirmDialog(null, "DESEJA MARCAR COM 'SENHA_DIVERGENTE' NOS CERTIFICADOS COM SENHA DIVERGENTE? ", "Confirma ?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "OK!");
        } else {
            if (response == JOptionPane.YES_NO_OPTION) {
                for (String cert_atual_caminho : LISTA_CERTIFICADOS_SENHA_DIVERGENTE) {
                    File pfx = new File(cert_atual_caminho);
                    renomearCertificadoPFX_SENHA_DIVERGENTE(pfx);
                }
            }
        }
    }

    public void renomearCertificadoPFX_SENHA_DIVERGENTE(File pfx) {
        //
        String OBJ[] = pfx.getName().split("[.]", -1);
        if (OBJ[0].contains(DIVERGENTE)) {
            System.out.println("Ja contem divergente : ");
            System.out.println(OBJ[0]);
        } else {
            String novoNome = DIVERGENTE + OBJ[0] + "." + OBJ[1];
            String nomeNovo = pfx.getParent() + "\\" + novoNome;
            //
            File newName = new File(nomeNovo);
            //
            if (pfx.renameTo(newName)) {

            }
        }
    }

}
