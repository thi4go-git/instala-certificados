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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getHoraVencimento() {
        return horaVencimento;
    }

    public void setHoraVencimento(String horaVencimento) {
        this.horaVencimento = horaVencimento;
    }

    public String getDescricaoVencimento() {
        return descricaoVencimento;
    }

    public void setDescricaoVencimento(String descricaoVencimento) {
        this.descricaoVencimento = descricaoVencimento;
    }

    public int getExpira() {
        return expira;
    }

    public void setExpira(int expira) {
        this.expira = expira;
    }

    public byte[] getCertificadoByte() {
        return certificadoByte;
    }

    public void setCertificadoByte(byte[] certificadoByte) {
        this.certificadoByte = certificadoByte;
    }

}
