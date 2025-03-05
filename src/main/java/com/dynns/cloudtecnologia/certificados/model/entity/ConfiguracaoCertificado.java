package com.dynns.cloudtecnologia.certificados.model.entity;

public class ConfiguracaoCertificado {

    private String senhaMaster;
    private String localPasta;
    private String senhaCertificado;
    private String userEmail;
    private String passEmail;
    private String smtpEmail;
    private String smtpPortEmail;
    private String tlsEmail;
    private String assuntoEmail;
    private String mensagemPadraoEmail;
    private boolean processarVencidos;


    public String getSenhaMaster() {
        return senhaMaster;
    }


    public void setSenhaMaster(String senhaMaster) {
        this.senhaMaster = senhaMaster;
    }

  
    public String getLocalPasta() {
        return localPasta;
    }

 
    public void setLocalPasta(String localPasta) {
        this.localPasta = localPasta;
    }

   
    public String getSenhaCertificado() {
        return senhaCertificado;
    }

 
    public void setSenhaCertificado(String senhaCertificado) {
        this.senhaCertificado = senhaCertificado;
    }

 
    public String getUserEmail() {
        return userEmail;
    }

   
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

   
    public String getPassEmail() {
        return passEmail;
    }

   
    public void setPassEmail(String passEmail) {
        this.passEmail = passEmail;
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

   
    public String getAssuntoEmail() {
        return assuntoEmail;
    }

   
    public void setAssuntoEmail(String assuntoEmail) {
        this.assuntoEmail = assuntoEmail;
    }

    
    public String getMensagemPadraoEmail() {
        return mensagemPadraoEmail;
    }

  
    public void setMensagemPadraoEmail(String mensagemPadraoEmail) {
        this.mensagemPadraoEmail = mensagemPadraoEmail;
    }

    
    public boolean isProcessarVencidos() {
        return processarVencidos;
    }

   
    public void setProcessarVencidos(boolean processarVencidos) {
        this.processarVencidos = processarVencidos;
    }   

}
