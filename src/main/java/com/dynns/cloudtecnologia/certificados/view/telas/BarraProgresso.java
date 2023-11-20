package com.dynns.cloudtecnologia.certificados.view.telas;

import java.text.DecimalFormat;

public class BarraProgresso extends javax.swing.JFrame {

    public BarraProgresso() {
        initComponents();
        ajustarTela();
    }

    private void ajustarTela() {
        this.setResizable(false);
        this.setEnabled(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public void definirLimites(int minimo, int maximo) {
        progresso.setMinimum(minimo);
        progresso.setMaximum(maximo);
    }

    public void atualizarBarra(int valor, String status) {
        DecimalFormat formataDouble = new DecimalFormat("###.00");
        descricao.setText(status);
        progresso.setValue(valor);
        percent.setText(formataDouble.format(progresso.getPercentComplete() * 100) + " %");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progresso = new javax.swing.JProgressBar();
        descricao = new javax.swing.JLabel();
        percent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        descricao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        percent.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        percent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        percent.setText("98.78%");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(progresso, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                            .addComponent(descricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(percent, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descricao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresso, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(percent, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new BarraProgresso().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descricao;
    private javax.swing.JLabel percent;
    private javax.swing.JProgressBar progresso;
    // End of variables declaration//GEN-END:variables
}
