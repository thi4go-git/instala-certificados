package com.dynns.cloudtecnologia.certificados.exception;

import javax.swing.JOptionPane;

public class GeralException extends RuntimeException {

    public GeralException(String mensagem) {
        super(mensagem);
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
