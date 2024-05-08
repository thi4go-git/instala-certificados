package com.dynns.cloudtecnologia.certificados.view.table;

import com.dynns.cloudtecnologia.certificados.controller.LogCertificadoController;
import com.dynns.cloudtecnologia.certificados.model.entity.LogCertificado;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class LogModelTable extends AbstractTableModel {

    private final LogCertificadoController logCertificadoController;

    public LogModelTable() {
        this.logCertificadoController = new LogCertificadoController();
    }

    private final String[] colunas = {"ID", "TIPO LOG", "DATA", "USUÁRIO", "IP USUÁRIO", "DETALHES"};
    private List<LogCertificado> logCertificadosList = new ArrayList<>();

    @Override// PREENCHE OS NOMES DAS COLUNAS DA TABLE
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {//PASSA  QTDE DE LINHAS PARA TABELA
        return logCertificadosList.size();
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
    public Object getValueAt(int linha, int coluna) {//PEGAR INFORMAÇÕES DA LINHA SELECIONADA!!!
        switch (coluna) {
            case 0:
                return logCertificadosList.get(linha).getId();
            case 1:
                return logCertificadosList.get(linha).getTipoLog();
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                return logCertificadosList.get(linha).getDataLog().format(formatter);
            case 3:
                return logCertificadosList.get(linha).getUsuario();
            case 4:
                return logCertificadosList.get(linha).getIpUsuario();
            case 5:
                return logCertificadosList.get(linha).getDetalhes();
            default:
                return null;
        }
    }

    public void atualizaTabela() {
        this.fireTableDataChanged();
    }

    public void preencherTabelaLogsCertificado() {
        this.logCertificadosList = this.logCertificadoController.findAll();
        atualizaTabela();
    }

}
