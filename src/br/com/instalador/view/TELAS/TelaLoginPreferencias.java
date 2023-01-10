/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.instalador.view.TELAS;

import br.com.instalador.controle.TelaController;
import java.sql.Statement;
import javax.swing.JOptionPane;


public final class TelaLoginPreferencias extends javax.swing.JFrame {

    private String SENHA_MASTER = "NULL";
    //
    private Statement stmtGlob;
    private String PASTA_CERTIFICADOS;
    private String SENHA_CERTIFICADO;
    //
    public String USO_LOGIN = "";// PREFERENCIAS OU DELETAR_CERTIFICADO
    public TelaController controle;
    //

    public TelaLoginPreferencias() {
        initComponents();
        mostrarTela();
    }

    public void mostrarTela() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setEnabled(true);
        this.setVisible(true);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SENHA_DIGITADA = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DIGITE A SENHA MASTER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton1.setText("OK");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SENHA_DIGITADA, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SENHA_DIGITADA)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (USO_LOGIN.equals("PREFERENCIAS")) {
            LOGAR_PREFERENCIAS();
        } else {
            if (USO_LOGIN.equals("DELETAR_CERTIFICADO")) {

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public void LOGAR_PREFERENCIAS() {
        String SENHA_INF = new String(SENHA_DIGITADA.getPassword());
        SENHA_INF = SENHA_INF.trim();
        if (SENHA_INF.equals(SENHA_MASTER)) {
            //
            this.dispose();
            //
            TelaEditarPreferencias edit = new TelaEditarPreferencias();
            edit.setControle(controle);
            edit.atualizaTela();
            //
        } else {
            SENHA_DIGITADA.setText("");
            JOptionPane.showMessageDialog(null, "*** SENHA MASTER INCORRETA ***");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaLoginPreferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLoginPreferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLoginPreferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLoginPreferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaLoginPreferencias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField SENHA_DIGITADA;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the SENHA_MASTER
     */
    public String getSENHA_MASTER() {
        return SENHA_MASTER;
    }

    /**
     * @param SENHA_MASTER the SENHA_MASTER to set
     */
    public void setSENHA_MASTER(String SENHA_MASTER) {
        this.SENHA_MASTER = SENHA_MASTER;
    }

    /**
     * @return the stmtGlob
     */
    public Statement getStmtGlob() {
        return stmtGlob;
    }

    /**
     * @param stmtGlob the stmtGlob to set
     */
    public void setStmtGlob(Statement stmtGlob) {
        this.stmtGlob = stmtGlob;
    }

    /**
     * @return the PASTA_CERTIFICADOS
     */
    public String getPASTA_CERTIFICADOS() {
        return PASTA_CERTIFICADOS;
    }

    /**
     * @param PASTA_CERTIFICADOS the PASTA_CERTIFICADOS to set
     */
    public void setPASTA_CERTIFICADOS(String PASTA_CERTIFICADOS) {
        this.PASTA_CERTIFICADOS = PASTA_CERTIFICADOS;
    }

    /**
     * @return the SENHA_CERTIFICADO
     */
    public String getSENHA_CERTIFICADO() {
        return SENHA_CERTIFICADO;
    }

    /**
     * @param SENHA_CERTIFICADO the SENHA_CERTIFICADO to set
     */
    public void setSENHA_CERTIFICADO(String SENHA_CERTIFICADO) {
        this.SENHA_CERTIFICADO = SENHA_CERTIFICADO;
    }

    /**
     * @return the USO_LOGIN
     */
    public String getUSO_LOGIN() {
        return USO_LOGIN;
    }

    /**
     * @param USO_LOGIN the USO_LOGIN to set
     */
    public void setUSO_LOGIN(String USO_LOGIN) {
        this.USO_LOGIN = USO_LOGIN;
    }

    /**
     * @param controle the controle to set
     */
    public void setControle(TelaController controle) {
        this.controle = controle;
    }

}
