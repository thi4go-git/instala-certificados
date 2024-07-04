package com.dynns.cloudtecnologia.certificados.model.entity;

import com.dynns.cloudtecnologia.certificados.model.enums.StatusAtualizacaoEnum;
import java.util.Date;

public class DetalhesAtualizacaoDTO {

    private String caminhoCertificado;
    private String nomeCertificado;
    private Date dataVencimentoCertificado;
    private StatusAtualizacaoEnum statusAtualizacaoEnum;

    public DetalhesAtualizacaoDTO(
            String caminhoCertificado,
            String nomeCertificado,
            Date dataVencimentoCertificado,
            StatusAtualizacaoEnum statusAtualizacaoEnum
    ) {
        this.caminhoCertificado = caminhoCertificado;
        this.nomeCertificado = nomeCertificado;
        this.dataVencimentoCertificado = dataVencimentoCertificado;
        this.statusAtualizacaoEnum = statusAtualizacaoEnum;
    }

    public String getCaminhoCertificado() {
        return caminhoCertificado;
    }

    public void setCaminhoCertificado(String caminhoCertificado) {
        this.caminhoCertificado = caminhoCertificado;
    }

    public String getNomeCertificado() {
        return nomeCertificado;
    }

    public void setNomeCertificado(String nomeCertificado) {
        this.nomeCertificado = nomeCertificado;
    }

    public Date getDataVencimentoCertificado() {
        return dataVencimentoCertificado;
    }

    public void setDataVencimentoCertificado(Date dataVencimentoCertificado) {
        this.dataVencimentoCertificado = dataVencimentoCertificado;
    }

    public StatusAtualizacaoEnum getStatusAtualizacaoEnum() {
        return statusAtualizacaoEnum;
    }

    public void setStatusAtualizacaoEnum(StatusAtualizacaoEnum statusAtualizacaoEnum) {
        this.statusAtualizacaoEnum = statusAtualizacaoEnum;
    }

}
