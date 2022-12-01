package br.com.instalador.view.VISAO.TELAS;

import br.com.instalador.controle.CERTIFICADO_CLASS;
import br.com.instalador.controle.TelaController;
import br.com.instalador.utils.Data;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public final class Tela extends javax.swing.JFrame {

    Data dt = new Data();
    String SENHA_MASTER = "895674";
    String PASTA_CERTIFICADOS = "NULL";
    String SENHA_CERTIFICADO = "123456";
    int ID_CERTIFICADO;

    SimpleDateFormat formataDataBD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataBR = new SimpleDateFormat("dd/MM/yyyy");

    //
    TelaController controle;

    public class ProcessoCARREGA_CERTS implements Runnable {

        @Override
        public void run() {
            START();
        }
    }

    public void START() {
        controle = new TelaController();
        preencherTela();
        mostrar();
    }

    //--------------------------------------------------------------------------------------
    public Tela() {
        initComponents();
        this.START();
    }

    public void mostrar() {
        this.setLocationRelativeTo(null);
        this.setEnabled(true);
        this.setVisible(true);
        String caminhoIcon = "/IMAGENS/LOGO_SYSTEM.jpeg";
        this.setIconImage(createImage(caminhoIcon,
                "tray icon"));
    }

    public void processoAutomatico() {
        ProcessoCARREGA_CERTS procCerts = new ProcessoCARREGA_CERTS();
        Thread threadCerts = new Thread(procCerts);
        threadCerts.setName("Thread resumo");
        threadCerts.start();
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = Tela.class.getResource(path);
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    public void preencherTela() {
        //
        carregaConfigBD();
        //         
        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_BD());
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
        if (TOT_CERTIFICADOS == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }

    }

    public void preencherTelaTodos() {
        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_TODOS());
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
        if (TOT_CERTIFICADOS == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }
    }

    public void preencherTelaVencidos() {

        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_VENCIDOS());
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
        if (TOT_CERTIFICADOS == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }

    }

    public void preencherTelaAtivos() {
        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_ATIVOS());
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
        if (TOT_CERTIFICADOS == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }

    }

    public void preencherTelaPersonalizada() {
        String nome_pesquisa = PESQUISA.getText().trim();
        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_BD_PERSONALIZADA(nome_pesquisa));
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
    }

    public void preencherTelaVencerEm1Mes() {
        TABELA_CERTIFICADOS.setModel(controle.preencherTabelaCERTIFICADOS_VENCER_1_MES());
        int TOT_CERTIFICADOS = controle.retornarTOTAL_CERTIFICADOS();
        TOTAL.setText("Total Certificados: " + TOT_CERTIFICADOS);
        definirTamanhoColunas();
        colorirLinhaATRASO();
        if (TOT_CERTIFICADOS == 0) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO: Não existem certificados no banco de dados,"
                    + " favor informar ao Responsável! ");
        }
    }

    public void carregaConfigBD() {
        SENHA_MASTER = controle.retornarSenhaMaster();
        PASTA_CERTIFICADOS = controle.retornarCaminhoDaPasta();
        SENHA_CERTIFICADO = controle.retornarSenhaCertificado();
    }

    public void definirTamanhoColunas() {
        TABELA_CERTIFICADOS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TABELA_CERTIFICADOS.getColumnModel().getColumn(0).setPreferredWidth(500);//NOME 
        TABELA_CERTIFICADOS.getColumnModel().getColumn(1).setPreferredWidth(90);//DATA VENC
        TABELA_CERTIFICADOS.getColumnModel().getColumn(2).setPreferredWidth(60);//HORA VENC
        TABELA_CERTIFICADOS.getColumnModel().getColumn(3).setPreferredWidth(115);//EXPIRA
        TABELA_CERTIFICADOS.getColumnModel().getColumn(4).setPreferredWidth(400);//DETALHES
    }

    //
    public void colorirLinhaATRASO() {
        TABELA_CERTIFICADOS.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                Color c;
                Object DATA_VENCIMENTO = TABELA_CERTIFICADOS.getValueAt(row, 1);
                int DIFERENCA_EM_DIAS = 0;
                if (DATA_VENCIMENTO != null) {
                    Date dtHoje = new Date();
                    String DATA_MENOR = formataDataBD.format(dtHoje);
                    //---------
                    String DT_MAIOR = "" + DATA_VENCIMENTO;
                    try {
                        DT_MAIOR = formataDataBD.format(formataBR.parse(DT_MAIOR));
                    } catch (ParseException ex) {

                    }
                    DIFERENCA_EM_DIAS = dt.retornarDiferencaEmDias(DATA_MENOR, DT_MAIOR);
                }
                // PRETO VENCIDO
                //VERMELHO 1 ATE 30 DIAS
                //AZUL > 30 DIAS
                if (DIFERENCA_EM_DIAS <= 0) {
                    c = Color.BLACK;
                    label.setForeground(c);
                    return label;
                } else {
                    if (DIFERENCA_EM_DIAS > 0 & DIFERENCA_EM_DIAS <= 30) {
                        label.setForeground(new Color(169, 0, 0));
                        return label;
                    } else {
                        label.setForeground(new Color(0, 0, 200));
                        return label;
                    }
                }

            }

        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LOGO = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TABELA_CERTIFICADOS = new javax.swing.JTable();
        PESQUISA = new javax.swing.JTextField();
        BT_INSTALAR = new javax.swing.JButton();
        TOTAL = new javax.swing.JLabel();
        BT_PREFERENCIAS = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        FILTRO = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instalador De Certificados .pfx");
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Licenciado para Vandolima Contabilidade  ®", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        LOGO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGENS/LOGO_1.jpeg"))); // NOI18N

        TABELA_CERTIFICADOS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        TABELA_CERTIFICADOS.setModel(new javax.swing.table.DefaultTableModel(
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
        TABELA_CERTIFICADOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(TABELA_CERTIFICADOS);

        PESQUISA.setBackground(new java.awt.Color(255, 252, 243));
        PESQUISA.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                PESQUISAComponentAdded(evt);
            }
        });
        PESQUISA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PESQUISAActionPerformed(evt);
            }
        });
        PESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PESQUISAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PESQUISAKeyReleased(evt);
            }
        });

        BT_INSTALAR.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BT_INSTALAR.setText("INSTALAR");
        BT_INSTALAR.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BT_INSTALAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BT_INSTALAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_INSTALARActionPerformed(evt);
            }
        });

        TOTAL.setBackground(new java.awt.Color(255, 255, 255));
        TOTAL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TOTAL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TOTAL.setText("Total Certificados: 100");
        TOTAL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        BT_PREFERENCIAS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BT_PREFERENCIAS.setText("PREFERÊNCIAS");
        BT_PREFERENCIAS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BT_PREFERENCIAS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BT_PREFERENCIAS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BT_PREFERENCIASActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("DELETAR");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        FILTRO.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FILTRO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Listar Todos Certificados", "Listar  Certificados Vencidos", "Listar  Certificados Ativos", "Listar  Certificados que vencerão em até 30 dias" }));
        FILTRO.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
        FILTRO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        FILTRO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FILTROItemStateChanged(evt);
            }
        });
        FILTRO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FILTROActionPerformed(evt);
            }
        });
        FILTRO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FILTROKeyPressed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Listar Todos");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Pesquisar Certificado");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BT_INSTALAR, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BT_PREFERENCIAS, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(311, 311, 311)
                        .addComponent(TOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(LOGO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PESQUISA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(FILTRO, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(FILTRO, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LOGO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BT_INSTALAR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(BT_PREFERENCIAS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TOTAL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Desenvolvido por: Thiago JR  - thi4go19@gmail.com  (62)98120-4102");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 479, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 480, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 308, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 308, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PESQUISAKeyPressed
        preencherTelaPersonalizada();
    }//GEN-LAST:event_PESQUISAKeyPressed

    private void PESQUISAComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_PESQUISAComponentAdded
    }//GEN-LAST:event_PESQUISAComponentAdded

    private void PESQUISAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PESQUISAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PESQUISAActionPerformed

    private void BT_INSTALARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_INSTALARActionPerformed
        if (TABELA_CERTIFICADOS.getSelectedRow() != -1) {
            int linhaSelecionada = TABELA_CERTIFICADOS.getSelectedRow();
            String EXPIRA = controle.retornarTempoExpirar(linhaSelecionada);
            String DT_VENCIMENTO = controle.retornarDtVencimento(linhaSelecionada);
            String NOME_CERTIFICADO = controle.retornarNOME_CERTIFICADO_SELECIONADO(linhaSelecionada);
            String ALIAS = controle.retornarALIAS_CERTIFICADO_SELECIONADO(linhaSelecionada);
            String DESCRICAO = controle.retornarDescricao(linhaSelecionada);
            ID_CERTIFICADO = controle.retornarID_CERTIFICADO_SELECIONADO(linhaSelecionada);
            //----------------------------------------------------------    
            if (!EXPIRA.contains("-")) {
                if (Integer.parseInt(EXPIRA) < 30) {
                    JOptionPane.showMessageDialog(null, "*** ATENÇÃO:(" + NOME_CERTIFICADO + "-" + ALIAS + ") \r\n  Esse Certificado Vence em " + EXPIRA + ""
                            + " DIAS, Favor informar o Responsável pela renovação");
                }
                if (DT_VENCIMENTO != null & !DESCRICAO.equals("A Senha do Certificado não confere com a senha do Instalador!")) {
                    CERTIFICADO_CLASS cert = new CERTIFICADO_CLASS();
                    cert.INSTALAR_CERTIFICADO(SENHA_CERTIFICADO, NOME_CERTIFICADO, ID_CERTIFICADO, ALIAS, controle);
                } else {
                    JOptionPane.showMessageDialog(null, "*** NÃO FOI POSSÍVEL INSTALAR, A SENHA DO CERTIFICADO NÃO CONFERE "
                            + "COM A SENHA DO INSTALADOR!");
                }

            } else {
                JOptionPane.showMessageDialog(null, "NÃO FOI POSSÍVEL INSTALAR O CERTIFICADO: \r\n (" + NOME_CERTIFICADO + "-" + ALIAS + ")  \r\n "
                        + " CAUSA: ESSE CERTIFICADO EXPIROU EM: " + DT_VENCIMENTO);
            }

        } else {
            JOptionPane.showMessageDialog(null, " SELECIONE UM CERTIFICADO PARA INSTALAR!!!");
        }
    }//GEN-LAST:event_BT_INSTALARActionPerformed

    private void BT_PREFERENCIASActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BT_PREFERENCIASActionPerformed
        TelaLoginPreferencias loga_pref = new TelaLoginPreferencias();
        loga_pref.setControle(controle);
        loga_pref.setUSO_LOGIN("PREFERENCIAS");
        loga_pref.setSENHA_MASTER(SENHA_MASTER);
        loga_pref.setPASTA_CERTIFICADOS(PASTA_CERTIFICADOS);
        loga_pref.setSENHA_CERTIFICADO(SENHA_CERTIFICADO);
    }//GEN-LAST:event_BT_PREFERENCIASActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (TABELA_CERTIFICADOS.getSelectedRow() != -1) {
            JPasswordField pwd = new JPasswordField(20);
            JOptionPane.showConfirmDialog(null, pwd, "Digite a Senha Master: ", JOptionPane.OK_CANCEL_OPTION);
            char[] SENHA_DIGITADA = pwd.getPassword();
            String DIGITADA = "";
            for (char chars : SENHA_DIGITADA) {
                DIGITADA = DIGITADA + chars;
            }
            DIGITADA = DIGITADA.trim();
            if (!DIGITADA.equals("") && DIGITADA.length() > 0) {
                if (DIGITADA.equals(SENHA_MASTER)) {
                    //
                    int linhaSelecionada = TABELA_CERTIFICADOS.getSelectedRow();
                    String NOME_CERTIFICADO = controle.retornarNOME_CERTIFICADO_SELECIONADO(linhaSelecionada);
                    int idCertificado = controle.retornarID_CERTIFICADO_SELECIONADO(linhaSelecionada);
                    int response = JOptionPane.showConfirmDialog(null, "CONFIRMA EXCLUSÃO DO CERTIFICADO: " + NOME_CERTIFICADO, "Confirma?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(null, "EXCLUSÃO CANCELADA!");
                    } else {
                        if (response == JOptionPane.YES_NO_OPTION) {

                            if (controle.deletarCertificado(idCertificado) == true) {
                                JOptionPane.showMessageDialog(null, "SUCESSO AO DELETAR " + NOME_CERTIFICADO);
                                preencherTela();
                            } else {
                                JOptionPane.showMessageDialog(null, "ERRO AO DELETAR " + NOME_CERTIFICADO);
                            }
                        }
                    }
                    //                
                } else {
                    JOptionPane.showMessageDialog(null, "SENHA MASTER INCORRETA!!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "DIGITE A SENHA MASTER! ");
            }
        } else {
            JOptionPane.showMessageDialog(null, " SELECIONE UM CERTIFICADO PARA DELETAR!!!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void FILTROKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FILTROKeyPressed

    }//GEN-LAST:event_FILTROKeyPressed

    private void FILTROItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FILTROItemStateChanged


    }//GEN-LAST:event_FILTROItemStateChanged

    private void FILTROActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FILTROActionPerformed
        String FILTER = "" + FILTRO.getSelectedItem();
        FILTER = FILTER.trim();
        if (FILTER.equals("Listar Todos Certificados")) {
            System.out.println("todos");
            PESQUISA.setText("");
            preencherTelaTodos();
        } else {
            if (FILTER.equals("Listar  Certificados Vencidos")) {
                PESQUISA.setText("");
                preencherTelaVencidos();
            } else {
                if (FILTER.equals("Listar  Certificados que vencerão em até 30 dias")) {
                    preencherTelaVencerEm1Mes();
                } else {
                    PESQUISA.setText("");
                    preencherTelaAtivos();
                }
            }
        }
    }//GEN-LAST:event_FILTROActionPerformed

    private void PESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PESQUISAKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_PESQUISAKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        preencherTela();
    }//GEN-LAST:event_jButton2ActionPerformed

    public void desailitar_botoes() {
        PESQUISA.setEnabled(false);
        BT_INSTALAR.setEnabled(false);
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
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Tela().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BT_INSTALAR;
    private javax.swing.JButton BT_PREFERENCIAS;
    private javax.swing.JComboBox<String> FILTRO;
    private javax.swing.JLabel LOGO;
    private javax.swing.JTextField PESQUISA;
    private javax.swing.JTable TABELA_CERTIFICADOS;
    private javax.swing.JLabel TOTAL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
