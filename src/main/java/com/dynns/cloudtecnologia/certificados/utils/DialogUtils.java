package com.dynns.cloudtecnologia.certificados.utils;

import javax.swing.JOptionPane;

public class DialogUtils {

    private DialogUtils() {
    }

    public static boolean confirmarOperacao(String msg) {
        int response = JOptionPane.showConfirmDialog(null, msg, "Confirma?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response == JOptionPane.YES_NO_OPTION;
    }

}
