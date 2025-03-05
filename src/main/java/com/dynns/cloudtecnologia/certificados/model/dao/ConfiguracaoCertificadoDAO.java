package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConfiguracaoCertificadoDAO implements IConfiguracaoCertificado {

    private static final String COLUNA_SENHA_MASTER = "senha_master";
    private static final String COLUNA_LOCAL_PASTA = "local_pasta";
    private static final String COLUNA_SENHA_CERTIFICADO = "senha_certificado";
    private static final String COLUNA_USER_EMAIL = "user_email";
    private static final String COLUNA_PASS_EMAIL = "pass_email";
    private static final String COLUNA_SMTP_EMAIL = "smtp_email";
    private static final String COLUNA_SMTP_PORT_EMAIL = "smtp_port_email";
    private static final String COLUNA_TLS_EMAIL = "tls_email";
    private static final String COLUNA_ASSUNTO_EMAIL = "assunto_email";
    private static final String COLUNA_MSG_PADRAO_EMAIL = "mensagem_padrao_email";
    private static final String COLUNA_PROCESSAR_VENCIDOS = "processar_vencidos";

    @Override
    public ConfiguracaoCertificado obterConfiguracaoCertificado() {
        String sql = "SELECT * FROM configuracao_certificado";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {

            try (ResultSet rs = pst.executeQuery()) {
                ConfiguracaoCertificado config = new ConfiguracaoCertificado();
                while (rs.next()) {
                    config.setSenhaMaster(rs.getString(COLUNA_SENHA_MASTER));
                    config.setLocalPasta(rs.getString(COLUNA_LOCAL_PASTA));
                    config.setSenhaCertificado(rs.getString(COLUNA_SENHA_CERTIFICADO));
                    config.setUserEmail(rs.getString(COLUNA_USER_EMAIL));
                    config.setPassEmail(rs.getString(COLUNA_PASS_EMAIL));
                    config.setSmtpEmail(rs.getString(COLUNA_SMTP_EMAIL));
                    config.setSmtpPortEmail(rs.getString(COLUNA_SMTP_PORT_EMAIL));
                    config.setTlsEmail(rs.getString(COLUNA_TLS_EMAIL));
                    config.setAssuntoEmail(rs.getString(COLUNA_ASSUNTO_EMAIL));
                    config.setMensagemPadraoEmail(rs.getString(COLUNA_MSG_PADRAO_EMAIL));
                    config.setProcessarVencidos(rs.getBoolean(COLUNA_PROCESSAR_VENCIDOS));

                    return config;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter ConfiguracaoCertificado");
        }
        throw new GeralException("Configuração Certificado não Localizada");
    }

    @Override
    public void atualizarConfiguracaoCetificado(ConfiguracaoCertificado configuracaoCertificado) {
        String sql = "update configuracao_certificado SET local_pasta = ?,senha_certificado = ?,"
                + "senha_master = ?, user_email = ?,pass_email = ?,smtp_email = ?,smtp_port_email = ?,tls_email = ?,"
                + "assunto_email = ?,mensagem_padrao_email = ?, processar_vencidos = ?";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, configuracaoCertificado.getLocalPasta());
            pst.setString(2, configuracaoCertificado.getSenhaCertificado());
            pst.setString(3, configuracaoCertificado.getSenhaMaster());
            pst.setString(4, configuracaoCertificado.getUserEmail());
            pst.setString(5, configuracaoCertificado.getPassEmail());
            pst.setString(6, configuracaoCertificado.getSmtpEmail());
            pst.setString(7, configuracaoCertificado.getSmtpPortEmail());
            pst.setString(8, configuracaoCertificado.getTlsEmail());
            pst.setString(9, configuracaoCertificado.getAssuntoEmail());
            pst.setString(10, configuracaoCertificado.getMensagemPadraoEmail());
            pst.setBoolean(11, configuracaoCertificado.isProcessarVencidos());
            
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Sucesso ao atualizar preferências!");

        } catch (SQLException ex) {
            throw new GeralException("Erro ao atualizar preferências (Configuração certificado):  " + ex.getMessage());
        }

    }

}
