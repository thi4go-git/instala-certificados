package com.dynns.cloudtecnologia.certificados.model.entity;

import java.sql.Date;

public class Certificado {

    private int id;
    private String nome;
    private String alias;
    private String caminho;
    private Date dataVencimento;
    private String horaVencimento;
    private String descricaoVencimento;
    private int expira;
    private byte[] certificadoByte;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the caminho
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * @param caminho the caminho to set
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * @return the dataVencimento
     */
    public Date getDataVencimento() {
        return dataVencimento;
    }

    /**
     * @param dataVencimento the dataVencimento to set
     */
    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * @return the horaVencimento
     */
    public String getHoraVencimento() {
        return horaVencimento;
    }

    /**
     * @param horaVencimento the horaVencimento to set
     */
    public void setHoraVencimento(String horaVencimento) {
        this.horaVencimento = horaVencimento;
    }

    /**
     * @return the descricaoVencimento
     */
    public String getDescricaoVencimento() {
        return descricaoVencimento;
    }

    /**
     * @param descricaoVencimento the descricaoVencimento to set
     */
    public void setDescricaoVencimento(String descricaoVencimento) {
        this.descricaoVencimento = descricaoVencimento;
    }

    /**
     * @return the expira
     */
    public int getExpira() {
        return expira;
    }

    /**
     * @param expira the expira to set
     */
    public void setExpira(int expira) {
        this.expira = expira;
    }

    /**
     * @return the certificadoByte
     */
    public byte[] getCertificadoByte() {
        return certificadoByte;
    }

    /**
     * @param certificadoByte the certificadoByte to set
     */
    public void setCertificadoByte(byte[] certificadoByte) {
        this.certificadoByte = certificadoByte;
    }

}
