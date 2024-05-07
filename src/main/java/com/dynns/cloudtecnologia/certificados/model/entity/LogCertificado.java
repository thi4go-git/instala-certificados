package com.dynns.cloudtecnologia.certificados.model.entity;

import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import java.time.LocalDateTime;

public class LogCertificado {

    private Integer id;
    private TipoLog tipoLog;
    private LocalDateTime dataLog;
    private String usuario;
    private String ipUsuario;
    private String detalhes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoLog getTipoLog() {
        return tipoLog;
    }

    public void setTipoLog(TipoLog tipoLog) {
        this.tipoLog = tipoLog;
    }

    public LocalDateTime getDataLog() {
        return dataLog;
    }

    public void setDataLog(LocalDateTime dataLog) {
        this.dataLog = dataLog;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIpUsuario() {
        return ipUsuario;
    }

    public void setIpUsuario(String ipUsuario) {
        this.ipUsuario = ipUsuario;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

}
