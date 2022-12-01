package br.com.instalador.model.entity;

import java.sql.Date;

public class Certificado {

    private int ID;
    private String NOME;
    private String ALIAS;
    private String CAMINHO;
    private Date DT_VENCIMENTO;
    private String HORA_VENCIMENTO;
    private String DESC_VENCIMENTO;
    private int EXPIRA;
    private byte[] BYTES_CERTIFICADO;

    //
    public Certificado(int ID, String NOME, String ALIAS, java.sql.Date DT_VENCIMENTO,
            String HORA_VENCIMENTO, String DESC_VENCIMENTO, int EXPIRA, byte[] BYTES_CERTIFICADO) {
        this.ID = ID;
        this.NOME = NOME;
        this.ALIAS = ALIAS;
        this.DT_VENCIMENTO = DT_VENCIMENTO;
        this.HORA_VENCIMENTO = HORA_VENCIMENTO;
        this.DESC_VENCIMENTO = DESC_VENCIMENTO;
        this.EXPIRA = EXPIRA;
        this.BYTES_CERTIFICADO = BYTES_CERTIFICADO;

    }

    public Certificado(int ID, byte[] BYTES_CERTIFICADO) {
        this.ID = ID;
        this.BYTES_CERTIFICADO = BYTES_CERTIFICADO;
    }

    public Certificado() {

    }

    //
    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the NOME
     */
    public String getNOME() {
        return NOME;
    }

    /**
     * @param NOME the NOME to set
     */
    public void setNOME(String NOME) {
        this.NOME = NOME;
    }

    /**
     * @return the CAMINHO
     */
    public String getCAMINHO() {
        return CAMINHO;
    }

    /**
     * @param CAMINHO the CAMINHO to set
     */
    public void setCAMINHO(String CAMINHO) {
        this.CAMINHO = CAMINHO;
    }

    /**
     * @return the DESC_VENCIMENTO
     */
    public String getDESC_VENCIMENTO() {
        return DESC_VENCIMENTO;
    }

    /**
     * @param DESC_VENCIMENTO the DESC_VENCIMENTO to set
     */
    public void setDESC_VENCIMENTO(String DESC_VENCIMENTO) {
        this.DESC_VENCIMENTO = DESC_VENCIMENTO;
    }

    /**
     * @return the EXPIRA
     */
    public int getEXPIRA() {
        return EXPIRA;
    }

    /**
     * @param EXPIRA the EXPIRA to set
     */
    public void setEXPIRA(int EXPIRA) {
        this.EXPIRA = EXPIRA;
    }

    /**
     * @return the ALIAS
     */
    public String getALIAS() {
        return ALIAS;
    }

    /**
     * @param ALIAS the ALIAS to set
     */
    public void setALIAS(String ALIAS) {
        this.ALIAS = ALIAS;
    }

    /**
     * @return the HORA_VENCIMENTO
     */
    public String getHORA_VENCIMENTO() {
        return HORA_VENCIMENTO;
    }

    /**
     * @param HORA_VENCIMENTO the HORA_VENCIMENTO to set
     */
    public void setHORA_VENCIMENTO(String HORA_VENCIMENTO) {
        this.HORA_VENCIMENTO = HORA_VENCIMENTO;
    }

    /**
     * @return the BYTES_CERTIFICADO
     */
    public byte[] getBYTES_CERTIFICADO() {
        return BYTES_CERTIFICADO;
    }

    /**
     * @param BYTES_CERTIFICADO the BYTES_CERTIFICADO to set
     */
    public void setBYTES_CERTIFICADO(byte[] BYTES_CERTIFICADO) {
        this.BYTES_CERTIFICADO = BYTES_CERTIFICADO;
    }

    /**
     * @return the DT_VENCIMENTO
     */
    public Date getDT_VENCIMENTO() {
        return DT_VENCIMENTO;
    }

    /**
     * @param DT_VENCIMENTO the DT_VENCIMENTO to set
     */
    public void setDT_VENCIMENTO(Date DT_VENCIMENTO) {
        this.DT_VENCIMENTO = DT_VENCIMENTO;
    }

}
