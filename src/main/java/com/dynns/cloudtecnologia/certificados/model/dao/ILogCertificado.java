package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;

public interface ILogCertificado {

    void salvarLog(TipoLog tipoLog, String usuario, String ipUsuario, String detalhes);
}
