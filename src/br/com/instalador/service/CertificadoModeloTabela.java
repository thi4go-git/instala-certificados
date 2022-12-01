
package br.com.instalador.service;

import br.com.instalador.model.entity.Certificado;
import br.com.instalador.controle.TelaController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class CertificadoModeloTabela extends AbstractTableModel {

    //informações da tabela
    SimpleDateFormat formataBR = new SimpleDateFormat("dd/MM/yyyy");
    private List<Certificado> dados = new ArrayList<>();
    private final String[] colunas = {"CERTIFICADO", "VENCIMENTO", "HORA", "EXPIRA EM (DIAS)", "DETALHES"};
    private List<Certificado> dadosFinal = new ArrayList<>();
    //

    //---------------------------------------------------------------------------------
    @Override// PREENCHE OS NOMES DAS COLUNAS DA TABLE.As
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {//PASSA  QTDE DE LINHAS PARA TABELA
        return dados.size();
    }

    @Override
    public int getColumnCount() {//PASSA QTDE DE COLUNAS PARA A TABELA
        return colunas.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {//PEGAR INFORMAÇÕES DA LINHA SELECIONADA!!!sss
        switch (coluna) {
            case 0:
                return dados.get(linha).getNOME();
            case 1:
                if (dados.get(linha).getDT_VENCIMENTO() == null) {
                    return null;
                } else {
                    return formataBR.format((Date) dados.get(linha).getDT_VENCIMENTO());
                }
            case 2:
                return dados.get(linha).getHORA_VENCIMENTO();
            case 3:
                return dados.get(linha).getEXPIRA();
            case 4:
                String expira = "" + dados.get(linha).getEXPIRA();
                String OBS = dados.get(linha).getDESC_VENCIMENTO();
                if (!OBS.contains("A Senha do Certificado não confere com a senha do Instalador!")) {
                    if (expira.contains("-")) {
                        return "O Certificado EXPIROU há (" + expira.replace("-", "") + ") DIAS!";
                    } else {
                        return "O certificado VENCERÁ em (" + expira + ") DIAS!";
                    }
                } else {
                    return dados.get(linha).getDESC_VENCIMENTO();
                }
        }
        return null;
    }

    //----------------
    public void atualizaTabela() {
        this.fireTableDataChanged();
    }

    public void preencherTabelaCERTIFICADOS_BD(TelaController controle) {
        dados = controle.retornarListaDeCertificados();
        dadosFinal = dados;
        atualizaTabela();
    }

    public void preencherTabelaCERTIFICADOS_VENCIDOS() {
        dados = new ArrayList<>();
        for (Certificado cert : dadosFinal) {
            if (cert.getEXPIRA() <= 0) {
                dados.add(cert);
            }
        }
        atualizaTabela();
    }

    public void preencherTabelaCERTIFICADOS_ATIVOS() {
        dados = new ArrayList<>();
        for (Certificado cert : dadosFinal) {
            if (cert.getEXPIRA() > 0) {
                dados.add(cert);
            }
        }
        atualizaTabela();
    }

    public void preencherTabelaCERTIFICADOS_TODOS() {
        dados = dadosFinal;
        atualizaTabela();
    }

    public void preencherTabelaCERTIFICADOS_BD_PERSONALIZADA(String NOME_PESQUISA, TelaController controle) {
        dados = controle.retornarListaDeCertificadosNome(NOME_PESQUISA);
        atualizaTabela();
    }

    public void preencherTabelaCERTIFICADOS_BD_VENCER_1_MES(TelaController controle) {
        dados = controle.retornarListaDeCertificadosVencer1Mes();
        atualizaTabela();
    }

    //----
    public int retornarID_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return dados.get(linhaSelecionada).getID();
    }

    public String retornarNOME_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return dados.get(linhaSelecionada).getNOME();
    }

    public String retornarALIAS_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return dados.get(linhaSelecionada).getALIAS();
    }

    public String retornarTempoExpirar(int linhaSelecionada) {
        return "" + dados.get(linhaSelecionada).getEXPIRA();
    }

    public String retornarDtVencimento(int linhaSelecionada) {
        if (dados.get(linhaSelecionada).getDT_VENCIMENTO() != null) {
            return formataBR.format(dados.get(linhaSelecionada).getDT_VENCIMENTO());
        } else {
            return null;
        }

    }

    public String retornarCAMINHO_CERTIFICADO_SELECIONADO(int linhaSelecionada) {
        return dados.get(linhaSelecionada).getCAMINHO();
    }

    public int retornarTOTAL_CERTIFICADOS() {
        return dados.size();
    }

    public String retornarDescricao(int linhaSelecionada) {
        return dados.get(linhaSelecionada).getDESC_VENCIMENTO();
    }

}
