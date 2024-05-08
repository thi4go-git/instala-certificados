package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.LogCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<LogCertificado> findAll() {
        String sql = "select * from log_certificado";
        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            try (ResultSet rs = pst.executeQuery()) {
                List<LogCertificado> logs = new ArrayList<>();
                while (rs.next()) {
                    LogCertificado log = new LogCertificado();
                    log.setId(rs.getInt("id"));
                    log.setTipoLog(TipoLog.fromString(rs.getString("tipo_log")));
                    Timestamp timestamp = rs.getTimestamp("data_log");
                    log.setDataLog(timestamp.toLocalDateTime());
                    log.setUsuario(rs.getString("usuario"));
                    log.setIpUsuario(rs.getString("ip_usuario"));
                    log.setDetalhes(rs.getString("detalhes"));

                    logs.add(log);
                }
                return logs;
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao listar Logs (LogCertificadoDAO) " + ex);
        }
    }

}
