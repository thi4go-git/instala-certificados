package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.dao.ILogCertificado;
import com.dynns.cloudtecnologia.certificados.model.dao.LogCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.entity.LogCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

public class LogCertificadoController {

    private final ILogCertificado logCertificadoDAO;

    public LogCertificadoController() {
        this.logCertificadoDAO = new LogCertificadoDAO();
    }

    public void salvarLog(TipoLog tipoLog, String detalhes) {
        final String usuarioLogado = System.getProperty("user.name");
        String ipUsuario = "0.0.0.0";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            ipUsuario = ip.getHostAddress();
        } catch (UnknownHostException e) {
            throw new GeralException("Erro ao obter IP da máquina!");
        }
        logCertificadoDAO.salvarLog(tipoLog, usuarioLogado, ipUsuario, detalhes);
    }

    public List<LogCertificado> findAllByMesAtual() {
        return this.logCertificadoDAO.findAllByMesAtual();
    }

    public List<LogCertificado> findFilter(
            Date dtInicio, Date dtFim, String tipoLog, String usuario, String ipUsuario, String detalhes
    ) {
         return this.logCertificadoDAO.findFilter(dtInicio, dtFim, tipoLog, usuario, ipUsuario, detalhes);
    }

}
