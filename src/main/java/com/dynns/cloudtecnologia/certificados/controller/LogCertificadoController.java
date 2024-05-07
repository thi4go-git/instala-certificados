package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.dao.ILogCertificado;
import com.dynns.cloudtecnologia.certificados.model.dao.LogCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

}
