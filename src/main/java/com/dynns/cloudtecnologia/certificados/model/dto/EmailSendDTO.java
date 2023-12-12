package com.dynns.cloudtecnologia.certificados.model.dto;

public class EmailSendDTO {

    private String username;
    private String password;
    private String smtpEmail;
    private String smtpPortEmail;
    private String tlsEmail;
    private String assunto;
    private String mensagemPadrao;
    private String destinatario;
    private byte[] anexoBytes = null;
    private String anexoNome = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public void setSmtpEmail(String smtpEmail) {
        this.smtpEmail = smtpEmail;
    }

    public String getSmtpPortEmail() {
        return smtpPortEmail;
    }

    public void setSmtpPortEmail(String smtpPortEmail) {
        this.smtpPortEmail = smtpPortEmail;
    }

    public String getTlsEmail() {
        return tlsEmail;
    }

    public void setTlsEmail(String tlsEmail) {
        this.tlsEmail = tlsEmail;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagemPadrao() {
        return mensagemPadrao;
    }

    public void setMensagemPadrao(String mensagemPadrao) {
        this.mensagemPadrao = mensagemPadrao;
    }

    public byte[] getAnexoBytes() {
        return anexoBytes;
    }

    public void setAnexoBytes(byte[] anexoBytes) {
        this.anexoBytes = anexoBytes;
    }

    public String getAnexoNome() {
        return anexoNome;
    }

    public void setAnexoNome(String anexoNome) {
        this.anexoNome = anexoNome;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

}
