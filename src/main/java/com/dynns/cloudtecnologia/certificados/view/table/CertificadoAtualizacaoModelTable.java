package com.dynns.cloudtecnologia.certificados.view.table;

import com.dynns.cloudtecnologia.certificados.model.entity.DetalhesAtualizacaoDTO;
import com.dynns.cloudtecnologia.certificados.model.enums.StatusAtualizacaoEnum;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

public class CertificadoAtualizacaoModelTable extends AbstractTableModel {

    private final String[] colunas = {"Nome", "Data vencimento", "Status"};
    private List<DetalhesAtualizacaoDTO> certificadosAtualizacaoList = new ArrayList<>();
    private List<DetalhesAtualizacaoDTO> certificadosAtualizacaoListBackup = new ArrayList<>();

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
        this.certificadosAtualizacaoListBackup = this.certificadosAtualizacaoList;
        atualizaTabela();
    }

    public void preencherTabelaPersonalizada(String atualizacaoEnumStr) {
        resetarTabela();
        if (!atualizacaoEnumStr.equals("TODOS")) {
            StatusAtualizacaoEnum status = StatusAtualizacaoEnum.fromString(atualizacaoEnumStr);
            List<DetalhesAtualizacaoDTO> filtro = certificadosAtualizacaoList.stream()
                    .filter(detalhe -> detalhe.getStatusAtualizacaoEnum() == status)
                    .collect(Collectors.toList());
            this.certificadosAtualizacaoList = filtro;
        }
        atualizaTabela();
    }

    private void resetarTabela() {
        this.certificadosAtualizacaoList = this.certificadosAtualizacaoListBackup;
    }

}
