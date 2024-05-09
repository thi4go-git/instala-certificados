package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.LogCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public List<LogCertificado> findAllByMesAtual() {
        String sql = "SELECT * FROM log_certificado "
                + "WHERE EXTRACT(YEAR FROM data_log) = EXTRACT(YEAR FROM CURRENT_DATE) "
                + "AND EXTRACT(MONTH FROM data_log) = EXTRACT(MONTH FROM CURRENT_DATE)";
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

    @Override
    public List<LogCertificado> findFilter(Date dtInicio, Date dtFim, String tipoLog, String usuario, String ipUsuario, String detalhes) {

        String sql = "SELECT * FROM log_certificado WHERE 1=1";
        List<Object> parameters = new ArrayList<>();

        if (Objects.nonNull(tipoLog) && !tipoLog.isEmpty()) {
            sql += " AND tipo_log ilike ?";
            parameters.add("%" + tipoLog + "%");
        }

        if (Objects.nonNull(usuario) && !usuario.isEmpty()) {
            sql += " AND usuario ilike ?";
            parameters.add("%" + usuario + "%");
        }

        if (Objects.nonNull(ipUsuario) && !ipUsuario.isEmpty()) {
            sql += " AND ip_usuario ilike ?";
            parameters.add("%" + ipUsuario + "%");
        }

        if (Objects.nonNull(detalhes) && !detalhes.isEmpty()) {
            sql += " AND detalhes ilike ?";
            parameters.add("%" + detalhes + "%");
        }

        if (Objects.nonNull(dtInicio) && Objects.nonNull(dtFim)) {
            sql += " AND data_log BETWEEN ? AND ?";
            String dtInicioStr = DataUtils.formataParaBD(dtInicio);
            String dtFimStr = DataUtils.formataParaBD(dtFim);
            Timestamp dtInicioTimestamp = Timestamp.valueOf(dtInicioStr + " 00:00:00");
            Timestamp dtFimTimestamp = Timestamp.valueOf(dtFimStr + " 23:59:59");
            parameters.add(dtInicioTimestamp);
            parameters.add(dtFimTimestamp);
        }

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                pst.setObject(parameterIndex, parameter);
                parameterIndex++;
            }
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
            throw new GeralException("Erro ao obter LOGS by Filter (LogCertificadoDAO) " + ex);
        }

    }

}
