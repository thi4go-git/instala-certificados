package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoCertificadoDAO implements IConfiguracaoCertificado {

    private static final String COLUNA_SENHA_MASTER = "senha_master";
    private static final String COLUNA_LOCAL_PASTA = "local_pasta";
    private static final String COLUNA_SENHA_CERTIFICADO = "senha_certificado";

    @Override
    public ConfiguracaoCertificado obterConfiguracaoCertificado() {
        String sql = "SELECT * FROM configuracao_certificado";
        
        try (Connection connection = Conexao.getConexao(); 
                PreparedStatement pst = connection.prepareStatement(sql)) {
            
            try (ResultSet rs = pst.executeQuery()) {
                ConfiguracaoCertificado config = new ConfiguracaoCertificado();
                while (rs.next()) {
                    config.setSenhaMaster(rs.getString(COLUNA_SENHA_MASTER));
                    config.setLocalPasta(rs.getString(COLUNA_LOCAL_PASTA));
                    config.setSenhaCertificado(rs.getString(COLUNA_SENHA_CERTIFICADO));
                    return config;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter ConfiguracaoCertificado");
        }
        throw new GeralException("Configuração Certificado não Localizada");
    }

}
