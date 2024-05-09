package com.dynns.cloudtecnologia.certificados.view.telas;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.ConfiguracaoCertificadoController;
import com.dynns.cloudtecnologia.certificados.controller.InstaladorController;
import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.model.entity.ConfiguracaoCertificado;
import com.dynns.cloudtecnologia.certificados.model.enums.TipoLog;
import com.dynns.cloudtecnologia.certificados.utils.CertificadoUtils;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import com.dynns.cloudtecnologia.certificados.utils.DialogUtils;
import com.dynns.cloudtecnologia.certificados.utils.LicUtils;
import com.dynns.cloudtecnologia.certificados.view.table.BotaoDetalhesImpl;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TelaPrincipal extends javax.swing.JFrame {

    private InstaladorController instalador;

    private CertificadoController certificadoControler;
    private ConfiguracaoCertificadoController configuracaoCertificadoController;
    private LogCertificadoController logCertificadoController;

    private static final String MSG_SELECIONA_CERTIFICADO = "Favor selecionar o Certificado!";
    private static final String MSG_SENHA_INVALIDA = "A Senha do Certificado não confere com a senha do Instalador!";
    private static final String FILTRO_TODOS = "TODOS";
    private static final String FILTRO_VENCIDOS = "VENCIDOS";
    private static final String FILTRO_VENCER_30_DIAS = "VENCEM_EM_30_DIAS";
    private static final Integer INDEX_COLUNA_BTN = 6;
    private static final Integer INDEX_COLUNA_DATA_VENCIMENTO = 2;
    private static final String MSG_PROCESSO_CANCELADO = "Processo cancelado!";

    public TelaPrincipal() {
        LicUtils.validarLic();
        initComponents();
        this.inicializarVariaveis();
        this.configurarExibicaoTela();
        this.processoAutomatico();

    }

    private void inicializarVariaveis() {
        this.instalador = new InstaladorController();
        this.certificadoControler = new CertificadoController();
        this.configuracaoCertificadoController = new ConfiguracaoCertificadoController();
        this.logCertificadoController = new LogCertificadoController();
    }

    private void configurarExibicaoTela() {
        this.setLocationRelativeTo(null);
        this.setEnabled(true);
        this.setVisible(true);
        this.setResizable(false);
        this.version.setText("Version: 2024.05.09");
        this.infoRodape.setText("Contato: thi4go19@gmail.com | 62-981204102");
    }

    private void processoAutomatico() {
        preencherTabelaCertificados();
        new BotaoDetalhesImpl(tabela, INDEX_COLUNA_BTN);
    }

    private void preencherTabelaCertificados() {
        tabela.setModel(certificadoControler.preencherTabelaCertificados(new Certificado()));
        definirInformacoesTabela();
    }

    private void preencherTabelaCertificadosVencidos() {
        tabela.setModel(certificadoControler.preencherTabelaCertificadosVencidos(new Certificado()));
        definirInformacoesTabela();
    }

    private void preencherTabelaCertificadosAtivos() {
        tabela.setModel(certificadoControler.preencherTabelaCertificadosAtivos(new Certificado()));
        definirInformacoesTabela();
    }

    private void preencherTabelaCertificadosVencemAte30Dias() {
        tabela.setModel(certificadoControler.preencherTabelaCertificadosVencemAte30Dias(new Certificado()));
        definirInformacoesTabela();
    }

    private void preencherTabelaCertificadosPesquisa() {
        String nomePesquisa = nomeCertificado.getText().trim();
        if (nomePesquisa != null && !nomePesquisa.equals("")) {
            Certificado filter = new Certificado();
            filter.setNome(nomePesquisa);
            tabela.setModel(certificadoControler.preencherTabelaCertificadosPesquisa(filter));
            definirInformacoesTabela();
        }
    }

    private void definirInformacoesTabela() {
        int totalRegistros = certificadoControler.retornarQtdeRegistros();
        qtdeRegistros.setText("Registros: " + totalRegistros);
        definirTamanhoColunas();
        colorirLinhaAtraso();
    }

    private void definirTamanhoColunas() {
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);//ID 
        tabela.getColumnModel().getColumn(1).setPreferredWidth(500);//NOME 
        tabela.getColumnModel().getColumn(2).setPreferredWidth(90);//DATA VENC
        tabela.getColumnModel().getColumn(3).setPreferredWidth(60);//HORA VENC
        tabela.getColumnModel().getColumn(4).setPreferredWidth(115);//EXPIRA
        tabela.getColumnModel().getColumn(5).setPreferredWidth(250);//DETALHES
        tabela.getColumnModel().getColumn(6).setPreferredWidth(30);//BTN

        // Centralizar o texto em todas as colunas   
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabela.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

    private void colorirLinhaAtraso() {
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                Object dataVencimento = tabela.getValueAt(row, INDEX_COLUNA_DATA_VENCIMENTO);
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
        btnEnviarCertificado = new javax.swing.JButton();
        version = new javax.swing.JLabel();
        infoRodape = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nomeCertificado = new javax.swing.JTextField();
        filtroListagem = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuPreferencias = new javax.swing.JMenu();
        itemMenuPreferencias = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Certificados"));

        tabela.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
        btnInstalar.setIcon(new ImageIcon(getClass().getResource("/img/downloadIcon.png")));
        btnInstalar.setText("Instalar");
        btnInstalar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInstalar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInstalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstalarActionPerformed(evt);
            }
        });

        btnDeletar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDeletar.setIcon(new ImageIcon(getClass().getResource("/img/deleteIcon.png")));
        btnDeletar.setText("Deletar");
        btnDeletar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnEnviarCertificado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEnviarCertificado.setIcon(new ImageIcon(getClass().getResource("/img/emailIcon.png")));
        btnEnviarCertificado.setText("Enviar Certificado");
        btnEnviarCertificado.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEnviarCertificado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCertificadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInstalar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviarCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(qtdeRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(qtdeRegistros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInstalar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEnviarCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        version.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        version.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        version.setText("Informações Rodapé");

        infoRodape.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        infoRodape.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        infoRodape.setText("Informações Rodapé");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Nome Certificado: ");

        nomeCertificado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nomeCertificado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomeCertificadoKeyPressed(evt);
            }
        });

        filtroListagem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        filtroListagem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "VENCIDOS", "VENCEM_EM_30_DIAS", "ATIVOS" }));
        filtroListagem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        filtroListagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroListagemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nomeCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filtroListagem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(nomeCertificado, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filtroListagem))
                .addContainerGap())
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        menuPreferencias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        menuPreferencias.setIcon(new javax.swing.ImageIcon("C:\\Users\\thiago-am\\Desktop\\DEVELOP\\PESSOAL\\instala-certificados\\src\\main\\resources\\img\\preferencias.png")); // NOI18N
        menuPreferencias.setText("Preferências");
        menuPreferencias.setAlignmentX(1.0F);
        menuPreferencias.setAlignmentY(1.0F);
        menuPreferencias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPreferencias.setMargin(new java.awt.Insets(6, 9, 6, 9));

        itemMenuPreferencias.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        itemMenuPreferencias.setIcon(new javax.swing.ImageIcon("C:\\Users\\thiago-am\\Desktop\\DEVELOP\\PESSOAL\\instala-certificados\\src\\main\\resources\\img\\edit1.png")); // NOI18N
        itemMenuPreferencias.setText("Editar Preferências");
        itemMenuPreferencias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        itemMenuPreferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuPreferenciasActionPerformed(evt);
            }
        });
        menuPreferencias.add(itemMenuPreferencias);

        jMenuBar1.add(menuPreferencias);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(version, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(infoRodape, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(498, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(version)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(488, Short.MAX_VALUE)
                    .addComponent(infoRodape, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        if (tabela.getSelectedRow() != -1) {
            if (isAutenticado()) {
                int linhaSelecionada = tabela.getSelectedRow();
                int idCertificado = certificadoControler.retornarId(linhaSelecionada);
                Certificado certDeletar = certificadoControler.findById(idCertificado);
                String nomeCert = certDeletar.getNome();
                if (DialogUtils.confirmarOperacao("Confirmar exclusão do certificado? " + nomeCert)) {
                    certificadoControler.deletarCertificado(idCertificado);
                    JOptionPane.showMessageDialog(null, "Sucesso ao deletar Certificado id: " + idCertificado + " - " + nomeCert + "!");
                    preencherTabelaCertificados();
                    String detalhes = "Certificado deletado: " + CertificadoUtils.converterObjetoParaJson(certDeletar);
                    logCertificadoController.salvarLog(TipoLog.ADMIN_CERTIFICADO_DELETADO, detalhes);
                } else {
                    JOptionPane.showMessageDialog(null, MSG_PROCESSO_CANCELADO);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, MSG_SELECIONA_CERTIFICADO);
        }
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
                ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();
                Certificado certificado = certificadoControler.findById(certificadoControler.retornarId(linhaSelecionada));
                int statusCode = instalador.instalarCertificado(configuracaoCertificado, certificado);

                if (statusCode == 0 && !descricao.contains(MSG_SENHA_INVALIDA)) {
                    JOptionPane.showMessageDialog(null, "Sucesso ao Instalar o certificado: " + nome + ", CÓD: " + statusCode);
                    String detalhes = "Certificado instalado: " + CertificadoUtils.converterObjetoParaJson(certificado);
                    logCertificadoController.salvarLog(TipoLog.USER_CERTIFICADO_INSTALADO, detalhes);
                } else {
                    JOptionPane.showMessageDialog(null, "*** NÃO FOI POSSÍVEL INSTALAR: Senha Inválida. CÓD: " + statusCode + " ***");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, MSG_SELECIONA_CERTIFICADO);
        }
    }//GEN-LAST:event_btnInstalarActionPerformed

    private void itemMenuPreferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuPreferenciasActionPerformed
        if (isAutenticado()) {
            new TelaPreferencias();
        }
    }//GEN-LAST:event_itemMenuPreferenciasActionPerformed

    private void filtroListagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtroListagemActionPerformed
        String filtro = "" + filtroListagem.getSelectedItem();
        switch (filtro) {
            case FILTRO_TODOS:
                preencherTabelaCertificados();
                limpaBarraPesquisa();
                break;
            case FILTRO_VENCIDOS:
                preencherTabelaCertificadosVencidos();
                break;
            case FILTRO_VENCER_30_DIAS:
                preencherTabelaCertificadosVencemAte30Dias();
                break;
            default:
                preencherTabelaCertificadosAtivos();
                break;
        }
    }//GEN-LAST:event_filtroListagemActionPerformed

    private void nomeCertificadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomeCertificadoKeyPressed
        preencherTabelaCertificadosPesquisa();
    }//GEN-LAST:event_nomeCertificadoKeyPressed

    private void btnEnviarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCertificadoActionPerformed
        if (tabela.getSelectedRow() != -1) {
            if (isAutenticado()) {
                if (DialogUtils.confirmarOperacao("*** ATENÇÃO: Você está prestes a enviar o arquivo de certificado digital com a SENHA "
                        + "de instalação!!!, DESEJA PROSSEGUIR? ***")) {
                    if (DialogUtils.confirmarOperacao("*** Tem Certeza? ... ***")) {
                        int linhaSelecionada = tabela.getSelectedRow();
                        int idCertificado = certificadoControler.retornarId(linhaSelecionada);
                        certificadoControler.enviarCertificadoEmail(idCertificado);
                    } else {
                        JOptionPane.showMessageDialog(null, MSG_PROCESSO_CANCELADO);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, MSG_PROCESSO_CANCELADO);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, MSG_SELECIONA_CERTIFICADO);
        }
    }//GEN-LAST:event_btnEnviarCertificadoActionPerformed

    private void limpaBarraPesquisa() {
        nomeCertificado.setText("");
    }

    private boolean isAutenticado() {
        ConfiguracaoCertificado configuracaoCertificado = configuracaoCertificadoController.obterConfiguracaoCertificado();
        AutenticacaoDialog dialogAutenticacao = new AutenticacaoDialog(this, configuracaoCertificado.getSenhaMaster());
        dialogAutenticacao.setVisible(true);
        return dialogAutenticacao.isAutenticado();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEnviarCertificado;
    private javax.swing.JButton btnInstalar;
    private javax.swing.JComboBox<String> filtroListagem;
    private javax.swing.JLabel infoRodape;
    private javax.swing.JMenuItem itemMenuPreferencias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menuPreferencias;
    private javax.swing.JTextField nomeCertificado;
    private javax.swing.JLabel qtdeRegistros;
    private javax.swing.JTable tabela;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
