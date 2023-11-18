package com.dynns.cloudtecnologia.certificados.extrator;

import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.dao.ConfiguracaoCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.IConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

public class CertificadoExtrator {

    BarraProgresso progresso;
    String caminhoPasta;

    IConfiguracaoCertificado configuracaoCertificadoDAO;
    private ConfiguracaoCertificadoController configuracaoCertificadoController;

    public CertificadoExtrator(String caminhoPasta) {
        configuracaoCertificadoDAO = new ConfiguracaoCertificadoDAO();
        configuracaoCertificadoController = new ConfiguracaoCertificadoController(configuracaoCertificadoDAO);
        this.caminhoPasta = caminhoPasta;
    }

    List<String> certificadosSenhaDivergente;
    List<String> certificadosSenhaCorreta;
    List<String> certificadosExistentesBD;

    private static final String DIVERGENTE = "SENHA_DIVERGENTE          ";

    public void processarCertificadosPasta() {
        ProcessoExtrator processoExtrator = new ProcessoExtrator();
        Thread threadExtrator = new Thread(processoExtrator);
        threadExtrator.setName("Thread threadUpdatCerts");
        threadExtrator.start();
    }

    public class ProcessoExtrator implements Runnable {

        @Override
        public void run() {
            iniciarProcesso();
        }
    }

    void iniciarProcesso() {
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

            int cont = 0;
            int instalados = 0;
            for (File certificadoPfx : listaPfxs) {
                cont++;
                String pathCertificado = certificadoPfx.getAbsolutePath();
                String dataVencimentoSTR = CertificadoUtils.retornarVencimentoCertificado(caminhoPasta, configuracaoCertificado.getSenhaCertificado());

                progresso.atualizarBarra(cont, "Aguarde! Lendo Certificado (" + cont + "/" + listaPfxs.size() + ") - " + certificadoPfx.getName());

                if (!dataVencimentoSTR.contains("keystore password was incorrect")) {
                    certificadosSenhaCorreta.add(pathCertificado);

                    try {
                        String format = "EEE MMM dd HH:mm:ss zzz yyyy";
                        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
                        Date dtVencimento = dateFormat.parse(dataVencimentoSTR);
                    } catch (ParseException ex) {
                        throw new GeralException("Erro ao converter data vencimento " + ex.getMessage());
                    }

                    Certificado certificado = new Certificado();
                    certificado.setNome(certificadoPfx.getName());

                } else {
                    //Certificados com senha divergente são adicionados em uma lista
                    certificadosSenhaDivergente.add(pathCertificado);
                }

            }
            progresso.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "A PASTA INFORMADA É INVÁLIDA OU ESSE COMPUTADOR"
                    + " NÃO POSSUI ACESSO À ELA!");
        }
    }

}
