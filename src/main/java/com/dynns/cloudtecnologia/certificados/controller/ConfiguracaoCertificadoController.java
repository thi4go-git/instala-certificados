package com.dynns.cloudtecnologia.certificados.controller;

import com.dynns.cloudtecnologia.certificados.model.dao.IConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;

public class ConfiguracaoCertificadoController {
    
    private final IConfiguracaoCertificado configuracaoCertificadoDAO;
    
    public ConfiguracaoCertificadoController(IConfiguracaoCertificado configuracaoCertificadoDAO) {
        this.configuracaoCertificadoDAO = configuracaoCertificadoDAO;
    }
    
    public ConfiguracaoCertificado obterConfiguracaoCertificado() {
        return configuracaoCertificadoDAO.obterConfiguracaoCertificado();
    }
    
    public void atualizarConfiguracaoCetificado(ConfiguracaoCertificado configuracaoCertificado) {
        configuracaoCertificadoDAO.atualizarConfiguracaoCetificado(configuracaoCertificado);
    }
}
