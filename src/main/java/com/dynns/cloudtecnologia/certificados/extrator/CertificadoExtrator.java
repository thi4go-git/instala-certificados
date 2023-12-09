package com.dynns.cloudtecnologia.certificados.extrator;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.utils.DialogUtils;
import com.dynns.cloudtecnologia.certificados.utils.FilesUtils;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
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

public class CertificadoExtrator {

    BarraProgresso progresso;
    String caminhoPasta;

    private final CertificadoController certificadoController;
    private final ConfiguracaoCertificadoController configuracaoCertificadoController;

    public CertificadoExtrator(String caminhoPasta) {
        this.certificadoController = new CertificadoController();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.caminhoPasta = caminhoPasta;
    }

    List<String> certificadosSenhaDivergente;
    List<String> certificadosSenhaCorreta;
    List<String> certificadosExistentesBD;

    private static final String DIVERGENTE = "SENHA_DIVERGENTE          ";

    public void processarCertificadosPasta() {
        File pastaCertificados = new File(caminhoPasta);
        if (pastaCertificados.exists() && pastaCertificados.isDirectory()) {

            ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();

            certificadosSenhaDivergente = new ArrayList<>();
            certificadosSenhaCorreta = new ArrayList<>();
            certificadosExistentesBD = new ArrayList<>();

            progresso = new BarraProgresso();
            progresso.definirLimites(1, 100);
            progresso.atualizarBarra(1, "AGUARDE... CARREGANDO CERTIFICADOS NA PASTA!!!");

            String[] extensions = new String[]{"pfx"};
            List<File> listaPfxs = (List<File>) FileUtils.listFiles(pastaCertificados, extensions, true);

            progresso.definirLimites(1, listaPfxs.size());

            String senhaCertificado = configuracaoCertificado.getSenhaCertificado();

            int cont = 0;
            int instalados = 0;
            for (File certificadoPfx : listaPfxs) {
                cont++;
                String pathCertificado = certificadoPfx.getAbsolutePath();
                String dataVencimentoSTR = CertificadoUtils.retornarVencimentoCertificado(pathCertificado, senhaCertificado);

                progresso.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + listaPfxs.size() + ") - " + certificadoPfx.getName());

                if (!dataVencimentoSTR.contains("keystore password was incorrect")) {
                    certificadosSenhaCorreta.add(pathCertificado);

                    Date dtVencimento;
                    String format = "EEE MMM dd HH:mm:ss zzz yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                    try {
                        dtVencimento = dateFormat.parse(dataVencimentoSTR);
                    } catch (ParseException ex) {
                        throw new GeralException("""
                                                 ::: Erro ao converter data vencimento :::  
                                                   Certificado provavelmente corrompido! 
                                              Para proseguir EXCLUA esse certificado da pasta. 
                                               Acesse o caminho e tente instalar manualmente: 
                                                 """
                                + ex.getMessage() + ""
                                + "\n " + pathCertificado);
                    }

                    Certificado certificado = new Certificado();
                    certificado.setNome(certificadoPfx.getName());
                    certificado.setAlias(CertificadoUtils.retornarAliasCertificado(pathCertificado, senhaCertificado));
                    java.sql.Date dataSQL = new java.sql.Date(dtVencimento.getTime());
                    certificado.setDataVencimento(dataSQL);
                    certificado.setHoraVencimento(new SimpleDateFormat("HH:mm:ss").format(dtVencimento));
                    certificado.setDescricaoVencimento("DESCRIÇÃO");
                    certificado.setExpira(0);
                    certificado.setCertificadoByte(FilesUtils.fileTobyte(new File(certificadoPfx.getAbsolutePath())));

                    if (!certificadoController.certificadoExists(certificado)) {
                        certificadoController.save(certificado);
                        instalados++;
                        progresso.atualizarBarra(cont, "SUCESSO AO SALVAR NO BANCO: " + certificado.getNome());
                    } else {
                        //Certificados que já existem no BD são colcoados em uma lista
                        certificadosExistentesBD.add(certificadoPfx.getAbsolutePath());
                    }

                } else {
                    //Certificados com senha divergente são adicionados em uma lista
                    certificadosSenhaDivergente.add(pathCertificado);
                }
            }//Fim do laço de repetição
            progresso.dispose();

            JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CONCLUÍDA!!!");
            JOptionPane.showMessageDialog(null,
                    "\n********************RESUMO********************"
                    + " \n Certificados Processados:      " + listaPfxs.size() + ""
                    + " \n Certificados Instalados:       " + instalados
                    + " \n Certificados senha Divergente: " + certificadosSenhaDivergente.size()
                    + " \n Certificados Já existentes:    " + certificadosExistentesBD.size()
                    + " \n*******************************************");

            organizarSenhasDivergente();

        } else {
            JOptionPane.showMessageDialog(null, "A PASTA INFORMADA É INVÁLIDA OU ESSE COMPUTADOR"
                    + " NÃO POSSUI ACESSO À ELA!");
        }
    }//Final método processarCertificadosPasta

    private void organizarSenhasDivergente() {
        if (!certificadosSenhaDivergente.isEmpty()) {
            boolean confirmar = DialogUtils.confirmarOperacao("DESEJA MARCAR COM 'SENHA_DIVERGENTE' NOS CERTIFICADOS COM SENHA DIVERGENTE? ");
            if (confirmar) {
                for (String caminhoCertificadoAtual : certificadosSenhaDivergente) {
                    File pfx = new File(caminhoCertificadoAtual);
                    renomearCertificadoSenhaDivergente(pfx);
                }
            }
            JOptionPane.showMessageDialog(null, "Processo Concluído!");
        }
    }

    private void renomearCertificadoSenhaDivergente(File pfx) {
        String[] objeto = pfx.getName().split("[.]", -1);
        if (!objeto[0].contains(DIVERGENTE)) {
            String novoNome = DIVERGENTE + objeto[0] + "." + objeto[1];
            String nomeNovo = pfx.getParent() + "\\" + novoNome;
            File newName = new File(nomeNovo);
            boolean renameCertificado = pfx.renameTo(newName);
            if (!renameCertificado) {
                throw new GeralException("Erro ao marcar certificado com Senha Divergente");
            }
        }
    }

}
