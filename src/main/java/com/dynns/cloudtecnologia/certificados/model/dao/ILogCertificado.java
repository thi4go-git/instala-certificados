package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.model.entity.LogCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.util.Date;
import java.util.List;

public interface ILogCertificado {

    void salvarLog(TipoLog tipoLog, String usuario, String ipUsuario, String detalhes);

    List<LogCertificado> findAllByMesAtual();

    List<LogCertificado> findFilter(
            Date dtInicio, Date dtFim, String tipoLog, String usuario, String ipUsuario, String detalhes
    );
}
