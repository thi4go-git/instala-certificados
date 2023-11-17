package com.dynns.cloudtecnologia.certificados.view.telas;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.model.dao.CertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.ConfiguracaoCertificadoDAO;
import com.dynns.cloudtecnologia.certificados.model.dao.ICertificado;
import com.dynns.cloudtecnologia.certificados.model.dao.IConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import com.dynns.cloudtecnologia.certificados.utils.Instalador;
import io.github.cdimascio.dotenv.Dotenv;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TelaPrincipal extends javax.swing.JFrame {

    private Dotenv dotenv;
    private Instalador instalador;

    ICertificado certificadoDAO;
    private CertificadoController certificadoControler;

    IConfiguracaoCertificado configuracaoCertificadoDAO;
    private ConfiguracaoCertificadoController configuracaoCertificadoController;

    private static final String MSG_SELECIONA_CERTIFICADO = "Favor selecionar o Certificado!";
    private static final String MSG_SENHA_INVALIDA = "A Senha do Certificado não confere com a senha do Instalador!";

    public TelaPrincipal() {
        initComponents();
        this.inicializarVariaveis();
        this.configurarExibicaoTela();
        this.processoAutomatico();
    }

    private void inicializarVariaveis() {
        dotenv = Dotenv.configure().load();
        instalador = new Instalador();

        certificadoDAO = new CertificadoDAO();
        certificadoControler = new CertificadoController(certificadoDAO);

        configuracaoCertificadoDAO = new ConfiguracaoCertificadoDAO();
        configuracaoCertificadoController = new ConfiguracaoCertificadoController(configuracaoCertificadoDAO);
    }

    private void configurarExibicaoTela() {
        this.setLocationRelativeTo(null);
        this.setEnabled(true);
        this.setVisible(true);
        this.setResizable(false);
        this.version.setText(dotenv.get("VERSION"));
        this.infoRodape.setText(dotenv.get("INFO_RODAPE"));
    }

    private void processoAutomatico() {
        preencherTabelaCertificados();
    }

    private void preencherTabelaCertificados() {
        tabela.setModel(certificadoControler.preencherTabelaCertificados());
        int registros = certificadoControler.retornarQtdeRegistros();
        qtdeRegistros.setText("Registros: " + registros);
        definirTamanhoColunas();
        colorirLinhaAtraso();
        if (registros == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }
    }

    private void definirTamanhoColunas() {
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(500);//NOME 
        tabela.getColumnModel().getColumn(1).setPreferredWidth(90);//DATA VENC
        tabela.getColumnModel().getColumn(2).setPreferredWidth(60);//HORA VENC
        tabela.getColumnModel().getColumn(3).setPreferredWidth(115);//EXPIRA
        tabela.getColumnModel().getColumn(4).setPreferredWidth(250);//DETALHES
    }

    private void colorirLinhaAtraso() {
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                Object dataVencimento = tabela.getValueAt(row, 1);
                int diferencaEmDias = 0;
                if (dataVencimento != null) {
                    Date dtHoje = new Date();
                    String dataMenor = DataUtils.formataParaBD(dtHoje);
                    String dataMaior = "" + dataVencimento;
                    dataMaior = DataUtils.formataParaBD(DataUtils.parseDataBrStringToDate(dataMaior));
                    diferencaEmDias = DataUtils.retornarDiferencaEmDias(dataMenor, dataMaior);
                }
                if (diferencaEmDias == 0) {
                    label.setForeground(new Color(254, 139, 0));
                    return label;
                } else {
                    if (diferencaEmDias < 0) {
                        label.setForeground(Color.BLACK);
                        return label;
                    } else {
                        if (diferencaEmDias > 0 && diferencaEmDias <= 60) {
                            label.setForeground(new Color(169, 0, 0));
                            return label;
                        } else {
                            label.setForeground(new Color(0, 0, 200));
                            return label;
                        }
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        qtdeRegistros = new javax.swing.JLabel();
        btnInstalar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        version = new javax.swing.JLabel();
        infoRodape = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabela);

        qtdeRegistros.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        qtdeRegistros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        qtdeRegistros.setText("Registros: 12564");
        qtdeRegistros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnInstalar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInstalar.setText("Instalar");
        btnInstalar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInstalar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInstalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstalarActionPerformed(evt);
            }
        });

        btnDeletar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletar.setText("Deletar");
        btnDeletar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInstalar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtdeRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qtdeRegistros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInstalar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        version.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        version.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        version.setText("Informações Rodapé");

        infoRodape.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        infoRodape.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        infoRodape.setText("Informações Rodapé");

        jMenu1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenu1.setText("Preferências");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setMargin(new java.awt.Insets(4, 7, 4, 7));
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(version, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(infoRodape, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(472, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(version)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(490, Short.MAX_VALUE)
                    .addComponent(infoRodape)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed


    }//GEN-LAST:event_btnDeletarActionPerformed

    private void btnInstalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstalarActionPerformed
        if (tabela.getSelectedRow() != -1) {
            int linhaSelecionada = tabela.getSelectedRow();
            int expira = certificadoControler.retornarExpira(linhaSelecionada);
            String nome = certificadoControler.retornarNome(linhaSelecionada);
            String dataVencimento = certificadoControler.retornarDataVencimentoSTR(linhaSelecionada);
            String alias = certificadoControler.retornarAlias(linhaSelecionada);

            if (expira < 0) {
                JOptionPane.showMessageDialog(null, "Não foi possível instalar o Certificado: \r\n (" + nome + "-" + alias + ")  \r\n "
                        + " CAUSA: Certificado expirado: " + dataVencimento);
            } else {
                if (expira < 30) {
                    JOptionPane.showMessageDialog(null, "*** ATENÇÃO:(" + nome + "-" + alias + ") \r\n  Esse Certificado Vence em " + expira + ""
                            + " DIAS, Favor informar o Responsável pela renovação");
                }
                String descricao = certificadoControler.retornarDescricao(linhaSelecionada);
                if (dataVencimento != null && !descricao.equals(MSG_SENHA_INVALIDA)) {

                    ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();
                    Certificado certificado = certificadoDAO.findById(certificadoControler.retornarId(linhaSelecionada));
                    instalador.instalarCertificado(configuracaoCertificado, certificado);

                } else {
                    JOptionPane.showMessageDialog(null, "*** Não foi possível instalar, " + MSG_SENHA_INVALIDA);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, MSG_SELECIONA_CERTIFICADO);
        }
    }//GEN-LAST:event_btnInstalarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnInstalar;
    private javax.swing.JLabel infoRodape;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel qtdeRegistros;
    private javax.swing.JTable tabela;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
