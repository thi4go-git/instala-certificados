package com.dynns.cloudtecnologia.certificados.view.telas;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.utils.DialogUtils;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class TelaPreferencias extends javax.swing.JFrame {

    private CertificadoController certificadoControler;
    private ConfiguracaoCertificadoController configuracaoCertificadoController;
    private LogCertificadoController logCertificadoController;

    private static final String FONTE = "Segoe UI";

    public TelaPreferencias() {
        initComponents();
        this.inicializarVariaveis();
        this.configurarExibicaoTela();
        this.carregarConfiguracaoCertificadoNaTela();
    }

    private void inicializarVariaveis() {
        this.certificadoControler = new CertificadoController();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.logCertificadoController = new LogCertificadoController();
    }

    private void configurarExibicaoTela() {
        this.setLocationRelativeTo(null);
        this.setEnabled(true);
        this.setVisible(true);
        this.setResizable(false);
    }

    private void carregarConfiguracaoCertificadoNaTela() {
        ConfiguracaoCertificado configuracao = configuracaoCertificadoController.obterConfiguracaoCertificado();
        cSenhaMaster.setText(configuracao.getSenhaMaster());
        cSenhaCertificados.setText(configuracao.getSenhaCertificado());
        cPastaCertificados.setText(configuracao.getLocalPasta());
        cUserEmail.setText(configuracao.getUserEmail());
        cPassEmail.setText(configuracao.getPassEmail());
        cSmtpEmail.setText(configuracao.getSmtpEmail());
        cSmtpPortEmail.setText(configuracao.getSmtpPortEmail());
        if (configuracao.getTlsEmail() == null) {
            cTlsEmail.setSelectedItem("true");
        } else {
            cTlsEmail.setSelectedItem(configuracao.getTlsEmail());
        }
        cAssuntoEmail.setText(configuracao.getAssuntoEmail());
        cMsgPadraoEmail.setText(configuracao.getMensagemPadraoEmail());
    }

    private boolean validarCampos() {
        String senhaMasterStr = cSenhaMaster.getText().trim();
        String pastaCertificadosStr = cPastaCertificados.getText().trim();
        String senhaCertificadosStr = cSenhaCertificados.getText().trim();

        if (senhaMasterStr == null || senhaMasterStr.equals("")
                || pastaCertificadosStr == null || pastaCertificadosStr.equals("")
                || senhaCertificadosStr == null || senhaCertificadosStr.equals("")) {

            JOptionPane.showMessageDialog(null, "Campos obrigatórios: senhaMasterStr"
                    + ", pastaCertificadosStr e senhaCertificadosStr!");
            return false;
        } else {
            File pastaCertificados = new File(cPastaCertificados.getText().trim());
            if (pastaCertificados.exists() && pastaCertificados.isDirectory()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "O caminho para a pasta certificados é inválido!");
                return false;
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cSenhaMaster = new javax.swing.JTextField();
        cPastaCertificados = new javax.swing.JTextField();
        cSenhaCertificados = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btDeletarVencidos = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btAtualizarCertificados = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btAlterar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cPassEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cAssuntoEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cSmtpPortEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cSmtpEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cTlsEmail = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cUserEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cMsgPadraoEmail = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Preferências"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Senha master: ");

        cSenhaMaster.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        cPastaCertificados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        cSenhaCertificados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Senha Certificados: ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Pasta Certificados:");

        btDeletarVencidos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btDeletarVencidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deleteIcon.png"))); // NOI18N
        btDeletarVencidos.setText("Deletar Certificados Vencidos");
        btDeletarVencidos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
        btDeletarVencidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btDeletarVencidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeletarVencidosActionPerformed(evt);
            }
        });

        btSalvar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/saveIcon.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
        btSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btAtualizarCertificados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAtualizarCertificados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btAtualizarCertificados.setText("Atualizar Certificados");
        btAtualizarCertificados.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
        btAtualizarCertificados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAtualizarCertificados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarCertificadosActionPerformed(evt);
            }
        });

        btAlterar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btAlterar.setText("Alterar");
        btAlterar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
        btAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Configurações para Envio de Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Pass:");

        cPassEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("User:");

        cAssuntoEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("SMTP:");

        cSmtpPortEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("TLS:");

        cSmtpEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Port:");

        cTlsEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cTlsEmail.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "true", "false" }));
        cTlsEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Assunto EMAIL: ");

        cUserEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        cMsgPadraoEmail.setColumns(20);
        cMsgPadraoEmail.setRows(5);
        cMsgPadraoEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mensagem PADRÃO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jScrollPane1.setViewportView(cMsgPadraoEmail);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cAssuntoEmail))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cPassEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cSmtpEmail)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cSmtpPortEmail)
                            .addComponent(cTlsEmail, 0, 91, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cSmtpPortEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cSmtpEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cPassEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cAssuntoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cTlsEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btDeletarVencidos, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAtualizarCertificados, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cPastaCertificados, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cSenhaMaster, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cSenhaCertificados, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cSenhaCertificados, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cSenhaMaster))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cPastaCertificados, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btDeletarVencidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAtualizarCertificados, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resposta = fileChooser.showOpenDialog(null);
        if (resposta == JFileChooser.APPROVE_OPTION) {
            File pasta = fileChooser.getSelectedFile();
            File caminhoPasta = new File(pasta.getPath());
            if (caminhoPasta.exists()) {
                cPastaCertificados.setText(caminhoPasta.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(null, "Selecione a pasta dos Certificados!");
            }
        }
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        if (validarCampos()) {
            if (DialogUtils.confirmarOperacao("Confirmar alteração das Preferências?")) {
                ConfiguracaoCertificado configUpdate = new ConfiguracaoCertificado();
                configUpdate.setLocalPasta(cPastaCertificados.getText().trim());
                configUpdate.setSenhaCertificado(cSenhaCertificados.getText().trim());
                configUpdate.setSenhaMaster(cSenhaMaster.getText().trim());
                configUpdate.setUserEmail(cUserEmail.getText().trim());
                configUpdate.setPassEmail(cPassEmail.getText().trim());
                configUpdate.setSmtpEmail(cSmtpEmail.getText().trim());
                configUpdate.setSmtpPortEmail(cSmtpPortEmail.getText().trim());
                configUpdate.setTlsEmail(cTlsEmail.getSelectedItem().toString().trim());
                configUpdate.setAssuntoEmail(cAssuntoEmail.getText().trim());
                configUpdate.setMensagemPadraoEmail(cMsgPadraoEmail.getText().trim());

                ConfiguracaoCertificado configCertificadoAntiga = configuracaoCertificadoController.obterConfiguracaoCertificado();

                configuracaoCertificadoController.atualizarConfiguracaoCetificado(configUpdate);

                String detalhes = "Configuração certificado Anterior: " + CertificadoUtils.converterObjetoParaJson(configCertificadoAntiga);
                logCertificadoController.salvarLog(TipoLog.ADMIN_ALTERACAO_PREFERENCIAS, detalhes);
            } else {
                JOptionPane.showMessageDialog(null, "Alteração cancelada!");
            }
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btDeletarVencidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeletarVencidosActionPerformed
        if (DialogUtils.confirmarOperacao("Confirmar exclusão de TODOS os Certificados vencidos? ")) {
            certificadoControler.deletarCertificadosVencidos();
        } else {
            JOptionPane.showMessageDialog(null, "Exclusão cancelada!");
        }
    }//GEN-LAST:event_btDeletarVencidosActionPerformed

    private void btAtualizarCertificadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarCertificadosActionPerformed
        if (DialogUtils.confirmarOperacao("Deseja processar todos certificados da Pasta?")) {
            String caminhoPasta = "" + cPastaCertificados.getText().trim();
            certificadoControler.processarCertificadosPasta(caminhoPasta);
        } else {
            JOptionPane.showMessageDialog(null, "Processo cancelado!");
        }
    }//GEN-LAST:event_btAtualizarCertificadosActionPerformed

    public static void main() {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPreferencias().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btAtualizarCertificados;
    private javax.swing.JButton btDeletarVencidos;
    private javax.swing.JButton btSalvar;
    private javax.swing.JTextField cAssuntoEmail;
    private javax.swing.JTextArea cMsgPadraoEmail;
    private javax.swing.JTextField cPassEmail;
    private javax.swing.JTextField cPastaCertificados;
    private javax.swing.JTextField cSenhaCertificados;
    private javax.swing.JTextField cSenhaMaster;
    private javax.swing.JTextField cSmtpEmail;
    private javax.swing.JTextField cSmtpPortEmail;
    private javax.swing.JComboBox<String> cTlsEmail;
    private javax.swing.JTextField cUserEmail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
