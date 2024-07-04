package com.dynns.cloudtecnologia.certificados.view.table;

import com.dynns.cloudtecnologia.certificados.model.entity.DetalhesAtualizacaoDTO;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CertificadoAtualizacaoModelTable extends AbstractTableModel {

    private final String[] colunas = {"Nome", "Data vencimento", "Status"};
    private List<DetalhesAtualizacaoDTO> certificadosAtualizacaoList = new ArrayList<>();

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return certificadosAtualizacaoList.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {    
            case 0:
                return certificadosAtualizacaoList.get(linha).getNomeCertificado();

            case 1:
                if (certificadosAtualizacaoList.get(linha).getDataVencimentoCertificado() != null) {
                    return DataUtils.formataParaBR((Date) certificadosAtualizacaoList.get(linha).getDataVencimentoCertificado());
                }
                return null;
            case 2:
                return certificadosAtualizacaoList.get(linha).getStatusAtualizacaoEnum();

            default:
                return null;
        }
    }

    public void atualizaTabela() {
        this.fireTableDataChanged();
    }

    public void preencherTabela(List<DetalhesAtualizacaoDTO> certificadosAtualizacaoList) {
        this.certificadosAtualizacaoList = certificadosAtualizacaoList;
        atualizaTabela();
    }

}
