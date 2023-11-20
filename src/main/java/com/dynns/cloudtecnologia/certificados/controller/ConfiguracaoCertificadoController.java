package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.model.dao.ConfiguracaoCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.IConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;

public class ConfiguracaoCertificadoController {

    private final IConfiguracaoCertificado configuracaoCertificadoDAO;

    public ConfiguracaoCertificadoController() {
        this.configuracaoCertificadoDAO = new ConfiguracaoCertificadoDAO();
    }

    public ConfiguracaoCertificado obterConfiguracaoCertificado() {
        return configuracaoCertificadoDAO.obterConfiguracaoCertificado();
    }

    public void atualizarConfiguracaoCetificado(ConfiguracaoCertificado configuracaoCertificado) {
        configuracaoCertificadoDAO.atualizarConfiguracaoCetificado(configuracaoCertificado);
    }
}
