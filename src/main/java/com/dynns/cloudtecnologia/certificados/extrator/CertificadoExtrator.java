package com.dynns.cloudtecnologia.certificados.extrator;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.CertificadoInformacoesDTO;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.utils.DialogUtils;
import com.dynns.cloudtecnologia.certificados.utils.FilesUtils;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import javax.swing.*;
import java.io.File;

public class CertificadoExtrator {

    BarraProgresso progresso;
    String caminhoPasta;

    private final CertificadoController certificadoController;
    private final ConfiguracaoCertificadoController configuracaoCertificadoController;
    private final LogCertificadoController logCertificadoController;

    public CertificadoExtrator(String caminhoPasta) {
        this.certificadoController = new CertificadoController();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.logCertificadoController = new LogCertificadoController();
        this.caminhoPasta = caminhoPasta;
    }

    List<String> certificadosSenhaDivergente;
    List<String> certificadosSenhaCorreta;
    List<String> certificadosExistentesBD;
    List<String> certificadosErroProcessamento;

    private static final String DIVERGENTE = "SENHA_DIVERGENTE          ";

    public void processarCertificadosPasta() {
        File pastaCertificados = new File(caminhoPasta);
        if (pastaCertificados.exists() && pastaCertificados.isDirectory()) {

            ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();

            certificadosSenhaDivergente = new ArrayList<>();
            certificadosSenhaCorreta = new ArrayList<>();
            certificadosExistentesBD = new ArrayList<>();
            certificadosErroProcessamento = new ArrayList<>();

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
                CertificadoInformacoesDTO infoCert = CertificadoUtils.retornarInformacoesCertificado(pathCertificado, senhaCertificado);
                progresso.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + listaPfxs.size() + ") - " + certificadoPfx.getName());
                if (!infoCert.getDataVencimento().contains("keystore password was incorrect")) {
                    certificadosSenhaCorreta.add(pathCertificado);
                    Date dtVencimento = null;
                    String format = "EEE MMM dd HH:mm:ss zzz yyyy";
                    DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                    try {
                        dtVencimento = dateFormat.parse(infoCert.getDataVencimento());
                    } catch (ParseException ex) {
                        certificadosErroProcessamento.add(pathCertificado);
                        this.notificarErroProcessamentoCertificado(pathCertificado, ex.getMessage(), certificadoPfx.length(), certificadoPfx.getName());
                    }
                    if (Objects.nonNull(dtVencimento)) {
                        Certificado certificado = new Certificado();
                        certificado.setNome(certificadoPfx.getName());
                        certificado.setAlias(infoCert.getAlias());
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
                            certificadosExistentesBD.add(certificadoPfx.getAbsolutePath());
                        }
                    }
                } else {
                    certificadosSenhaDivergente.add(pathCertificado);
                }
            }
            progresso.dispose();

            String detalhes = "Rotina de atualização de certificados realizada com sucesso,"
                    + " RESUMO: Certificados Processados: " + listaPfxs.size() + ","
                    + "Certificados Instalados: " + instalados
                    + ", Certificados senha Divergente: " + certificadosSenhaDivergente.size()
                    + ", Certificados Já existentes: " + certificadosExistentesBD.size();
            logCertificadoController.salvarLog(TipoLog.ADMIN_ATUALIZAR_CERTIFICADOS, detalhes);

            JOptionPane.showMessageDialog(null, "ATUALIZAÇÃO CONCLUÍDA!!!");
            JOptionPane.showMessageDialog(null,
                    "\n********************RESUMO********************"
                    + " \n Certificados Processados:      " + listaPfxs.size() + ""
                    + " \n Certificados Instalados:       " + instalados
                    + " \n Certificados senha Divergente: " + certificadosSenhaDivergente.size()
                    + " \n Certificados Já existentes:    " + certificadosExistentesBD.size()
                    + " \n Certificados não processados:  " + certificadosErroProcessamento.size()
                    + " \n*******************************************");

            organizarSenhasDivergente();

        } else {
            JOptionPane.showMessageDialog(null, "A PASTA INFORMADA É INVÁLIDA OU ESSE COMPUTADOR"
                    + " NÃO POSSUI ACESSO À ELA!");
        }
    }

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

    private void notificarErroProcessamentoCertificado(String pathCertificado, String erro, long tamanhoCertificado, String nomeCertificado) {
        String msg = "<html>::::::::::::::: Erro ao ler CERTIFICADO :::::::::::::::<br>"
                + "Certificado: " + nomeCertificado + " <br>"
                + "Tamanho (bytes): " + tamanhoCertificado + " <br>"
                + "Causa do erro: " + erro + " <br>"
                + "Caminho: <a href='" + pathCertificado + "'>" + pathCertificado + "</a></html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(msg);
        textPane.setEditable(true);
        textPane.setBackground(null);
        textPane.setBorder(null);
        JOptionPane.showMessageDialog(null, textPane, "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
