package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.extrator.CertificadoExtrator;
import com.dynns.cloudtecnologia.certificados.model.dao.CertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.ICertificado;
import com.dynns.cloudtecnologia.certificados.model.dto.EmailSendDTO;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ContatoCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import com.dynns.cloudtecnologia.certificados.utils.EmailUtils;
import com.dynns.cloudtecnologia.certificados.view.table.CertificadoModelTable;
import com.dynns.cloudtecnologia.certificados.view.telas.BarraProgresso;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class CertificadoController {

    private final ICertificado certificadoDAO;
    private final CertificadoModelTable modelo;
    private final ConfiguracaoCertificadoController configuracaoCertificadoController;
    private final ContatoCertificadoController contatoCertificadoController;
    private LogCertificadoController logCertificadoController;

    public CertificadoController() {
        this.certificadoDAO = new CertificadoDAO();
        this.modelo = new CertificadoModelTable();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.contatoCertificadoController = new ContatoCertificadoController();
        this.logCertificadoController = new LogCertificadoController();
    }

    public void save(Certificado certificado) {
        certificadoDAO.save(certificado);
    }

    public Certificado findById(int id) {
        return certificadoDAO.findById(id);
    }

    //findByIdNotBytes
    public Certificado findByIdNotBytes(int id) {
        return certificadoDAO.findByIdNotBytes(id);
    }

    public AbstractTableModel preencherTabelaCertificados(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificados(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosVencidos(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosVencidos(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosAtivos(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosAtivos(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosVencemAte30Dias(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificadosVencemAte30Dias(certificadosList);
        return modelo;
    }

    public AbstractTableModel preencherTabelaCertificadosPesquisa(Certificado filter) {
        List<Certificado> certificadosList = certificadoDAO.findAllFilter(filter);
        modelo.preencherTabelaCertificados(certificadosList);
        return modelo;
    }

    public int retornarQtdeRegistros() {
        return modelo.retornarQtdeRegistros();
    }

    public int retornarExpira(int linhaSelecionada) {
        return modelo.retornarExpira(linhaSelecionada);
    }

    public String retornarNome(int linhaSelecionada) {
        return modelo.retornarNome(linhaSelecionada);
    }

    public String retornarDataVencimentoSTR(int linhaSelecionada) {
        return modelo.retornarDataVencimentoSTR(linhaSelecionada);
    }

    public String retornarAlias(int linhaSelecionada) {
        return modelo.retornarAlias(linhaSelecionada);
    }

    public String retornarDescricao(int linhaSelecionada) {
        return modelo.retornarDescricao(linhaSelecionada);
    }

    public int retornarId(int linhaSelecionada) {
        return modelo.retornarId(linhaSelecionada);
    }

    public void deletarCertificado(int idCertificado) {
        if (contatoCertificadoController.deletarContatoCertificadoByIdCertificado(idCertificado)) {
            certificadoDAO.deletarCertificado(idCertificado);
        }
    }

    public void deletarCertificadosVencidos() {
        ProcessoDeletarVencidos processoDeletar = new ProcessoDeletarVencidos();
        Thread threadDeletar = new Thread(processoDeletar);
        threadDeletar.setName("Thread ProcessoDeletarVencidos");
        threadDeletar.start();
    }

    public boolean certificadoExists(Certificado certificado) {
        return certificadoDAO.certificadoExists(certificado);
    }

    public void processarCertificadosPasta(String caminhoPasta) {
        ProcessoExtrator processoExtrator = new ProcessoExtrator(caminhoPasta);
        Thread threadExtrator = new Thread(processoExtrator);
        threadExtrator.setName("Thread threadUpdatCerts");
        threadExtrator.start();
    }

    public void enviarCertificadoEmail(int idCertificado) {
        ProcessoEnviarCertificado processoEnvCert = new ProcessoEnviarCertificado(idCertificado);
        Thread threadEnvCert = new Thread(processoEnvCert);
        threadEnvCert.setName("Thread threadEnvCert");
        threadEnvCert.start();
    }

    /*
    Classe para executar thread dentro do CertificadoExtrator
     */
    public class ProcessoExtrator implements Runnable {

        private final String caminhoPasta;

        public ProcessoExtrator(String caminhoPasta) {
            this.caminhoPasta = caminhoPasta;
        }

        @Override
        public void run() {
            CertificadoExtrator extrator = new CertificadoExtrator(caminhoPasta);
            extrator.processarCertificadosPasta();
        }
    }

    /*
    Classe para executar thread Deletar Vencidos
     */
    public class ProcessoDeletarVencidos implements Runnable {

        @Override
        public void run() {
            List<Certificado> certificadosVencidos = certificadoDAO.findAllVencidos();
            BarraProgresso progresso = new BarraProgresso();
            progresso.definirLimites(1, 100);
            int cont = 1;
            for (Certificado certificadoVencido : certificadosVencidos) {
                progresso.atualizarBarra(cont, "Aguarde! Deletando Certificado (" + cont + "/" + certificadosVencidos.size() + ") - " + certificadoVencido.getNome());
                if (contatoCertificadoController.deletarContatoCertificadoByIdCertificado(certificadoVencido.getId())) {
                    certificadoDAO.deletarCertificado(certificadoVencido.getId());
                }
                cont++;
            }
            progresso.dispose();

            String detalhes = "Todos os certificados vencidos foram deletados!";
            logCertificadoController.salvarLog(TipoLog.ADMIN_DELETAR_CERTIFICADOS_VENCIDOS, detalhes);
            JOptionPane.showMessageDialog(null, "Os Certificados vencidos foram excluídos com sucesso!");
        }
    }

    /*
    Classe para executar thread Envio certificado
     */
    public class ProcessoEnviarCertificado implements Runnable {

        private final int idCertificado;

        public ProcessoEnviarCertificado(int idCertificado) {
            this.idCertificado = idCertificado;
        }

        @Override
        public void run() {
            Certificado certificado = certificadoDAO.findById(idCertificado);
            ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();
            ContatoCertificado contato = contatoCertificadoController.retornarContatoCertificado(idCertificado);

            if (contato.getEmailContato() == null || contato.getNomeContato() == null) {
                JOptionPane.showMessageDialog(null, "*** Não foi possível enviar o Certificado ***\n "
                        + "Favor cadastrar NOME e EMAIL para o Responsável referente ao certificado: \n "
                        + "" + certificado.getNome() + " - " + certificado.getAlias());
            } else {
                String mensagemEnvioCertificado
                        = "Prezado Cliente,\n"
                        + "\n"
                        + "Segue em anexo o seu certificado digital A1 (" + certificado.getNome() + " - " + certificado.getAlias() + ") \n"
                        + "Vencimento:" + DataUtils.formataParaBR(certificado.getDataVencimento()) + " às " + certificado.getHoraVencimento() + " \n"
                        + "A SENHA PARA INSTALAÇÃO é:   " + configuracaoCertificado.getSenhaCertificado() + ""
                        + "\n"
                        + "\n"
                        + "Atenciosamente,\n"
                        + DataUtils.formataParaBR(new Date()) + "\n"
                        + new Date().toString();

                EmailSendDTO emailSendDTO = new EmailSendDTO();
                emailSendDTO.setUsername(configuracaoCertificado.getUserEmail());
                emailSendDTO.setPassword(configuracaoCertificado.getPassEmail());
                emailSendDTO.setSmtpEmail(configuracaoCertificado.getSmtpEmail());
                emailSendDTO.setSmtpPortEmail(configuracaoCertificado.getSmtpPortEmail());
                emailSendDTO.setTlsEmail(configuracaoCertificado.getTlsEmail());
                emailSendDTO.setAssunto("CERTIFICADO DIGITAL A1");
                emailSendDTO.setMensagemPadrao(mensagemEnvioCertificado);
                emailSendDTO.setDestinatario(contato.getEmailContato());
                emailSendDTO.setAnexoBytes(certificado.getCertificadoByte());
                emailSendDTO.setAnexoNome(certificado.getNome());
                emailSendDTO.setCertificado(certificado);

                EmailUtils.enviarEmail(emailSendDTO);
            }
        }
    }
}
