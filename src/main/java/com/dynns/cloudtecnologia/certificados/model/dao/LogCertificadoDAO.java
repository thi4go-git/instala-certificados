package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LogCertificadoDAO implements ILogCertificado {

    @Override
    public void salvarLog(TipoLog tipoLog, String usuario, String ipUsuario, String detalhes) {
        String sql = "INSERT INTO log_certificado "
                + "(tipo_log,data_log,usuario,ip_usuario,detalhes)"
                + " values (?,?,?,?,?)";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, tipoLog.toString());
            pst.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            pst.setString(3, usuario);
            pst.setString(4, ipUsuario);
            pst.setString(5, detalhes);

            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new GeralException("Erro ao salvar LOG: " + ex.getMessage());
        }
    }

}
