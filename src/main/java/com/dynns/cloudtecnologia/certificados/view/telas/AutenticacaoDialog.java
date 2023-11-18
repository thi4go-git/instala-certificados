package com.dynns.cloudtecnologia.certificados.view.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class AutenticacaoDialog extends JDialog {

    private JPasswordField passwordField;
    private final JButton okButton;
    private final JButton cancelButton;

    private String senha;
    private boolean autenticado = false;

    public AutenticacaoDialog(Frame parent, String senhaMaster) {
        super(parent, "Digite a Senha master", true);
        JPanel panel = new JPanel(new BorderLayout());

        passwordField = new JPasswordField();
        panel.add(passwordField, BorderLayout.CENTER);

        okButton = new JButton("OK");
        okButton.addActionListener((ActionEvent e) -> {
            senha = new String(passwordField.getPassword());
            autenticar(senhaMaster);
        });

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener((ActionEvent e) -> {
            senha = null;
            fecharDialog();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel);
        this.setSize(new Dimension(300, 150));
        setLocationRelativeTo(parent);
        setLocationRelativeTo(parent);
    }

    public void fecharDialog() {
        this.dispose();
    }

    private void autenticar(String senhaMaster) {
        if (senha == null || senha.equals("")) {
            JOptionPane.showMessageDialog(null, "Informe a senha master");
        } else {
            if (!senha.equals(senhaMaster)) {
                JOptionPane.showMessageDialog(null, "Senha master Incorreta");
            } else {
                this.autenticado = true;
                fecharDialog();
            }
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }

}
