package com.dynns.cloudtecnologia.certificados.model.entity;

public class ConfiguracaoCertificado {

    private String senhaMaster;
    private String localPasta;
    private String senhaCertificado;

    /**
     * @return the senhaMaster
     */
    public String getSenhaMaster() {
        return senhaMaster;
    }

    /**
     * @param senhaMaster the senhaMaster to set
     */
    public void setSenhaMaster(String senhaMaster) {
        this.senhaMaster = senhaMaster;
    }

    /**
     * @return the localPasta
     */
    public String getLocalPasta() {
        return localPasta;
    }

    /**
     * @param localPasta the localPasta to set
     */
    public void setLocalPasta(String localPasta) {
        this.localPasta = localPasta;
    }

    /**
     * @return the senhaCertificado
     */
    public String getSenhaCertificado() {
        return senhaCertificado;
    }

    /**
     * @param senhaCertificado the senhaCertificado to set
     */
    public void setSenhaCertificado(String senhaCertificado) {
        this.senhaCertificado = senhaCertificado;
    }

}
