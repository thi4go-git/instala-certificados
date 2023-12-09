package com.dynns.cloudtecnologia.certificados.view.table;

import com.dynns.cloudtecnologia.certificados.controller.CertificadoController;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.view.telas.TelaDetalhesCertificado;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class BotaoDetalhesImpl extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener {

    JTable table;
    JButton renderButton;
    JButton editButton;
    String text;

    private static final int INDEX_ID = 0;
    private static final int INDEX_DESCRICAO_VENCIMENTO = 5;

    private final CertificadoController certificadoControler;

    public BotaoDetalhesImpl(JTable table, int column) {
        super();
        this.table = table;
        renderButton = new JButton();
        renderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);

        this.certificadoControler = new CertificadoController();
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        }

        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? "" : value.toString();
        editButton.setText(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();

        abrirTelaDetalhes();
    }

    private void abrirTelaDetalhes() {
        int indexLinhaSelecionada = table.getSelectedRow();
        if (indexLinhaSelecionada != -1) {
            TableModel model = table.getModel();
            int numColunas = model.getColumnCount();
            Object[] valoresLinhaSelecionada = new Object[numColunas];
            for (int i = 0; i < numColunas; i++) {
                valoresLinhaSelecionada[i] = model.getValueAt(indexLinhaSelecionada, i);
            }
            int idCertificado = Integer.parseInt(valoresLinhaSelecionada[INDEX_ID].toString());
            String descricaoVencimento = valoresLinhaSelecionada[INDEX_DESCRICAO_VENCIMENTO].toString();
            Certificado certificado = certificadoControler.findByIdNotBytes(idCertificado);
            certificado.setDescricaoVencimento(descricaoVencimento);

            new TelaDetalhesCertificado(certificado);

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum detalhe a mostrar.");
        }
    }
}
