package com.dynns.cloudtecnologia.certificados.view.table;

import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CertificadoModelTable extends AbstractTableModel {

    private final String[] colunas = {"ID", "CERTIFICADO", "VENCIMENTO", "HORA", "EXPIRA EM (DIAS)", "DETALHES", ""};
    private List<Certificado> certificadosList = new ArrayList<>();

    private static final String MSG_SENHA_INCORRETA = "A Senha do Certificado não confere com a senha do Instalador!";

    @Override// PREENCHE OS NOMES DAS COLUNAS DA TABLE
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {//PASSA  QTDE DE LINHAS PARA TABELA
        return certificadosList.size();
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
                return certificadosList.get(linha).getId();
            case 1:
                return certificadosList.get(linha).getNome();

            case 2:
                if (certificadosList.get(linha).getDataVencimento() != null) {
                    return DataUtils.formataParaBR((Date) certificadosList.get(linha).getDataVencimento());
                }
                return null;

            case 3:
                return certificadosList.get(linha).getHoraVencimento();

            case 4:
                return certificadosList.get(linha).getExpira();

            case 5:
                String expira = "" + certificadosList.get(linha).getExpira();
                String observacao = certificadosList.get(linha).getDescricaoVencimento();
                if (!observacao.contains(MSG_SENHA_INCORRETA)) {
                    if (expira.equals("0")) {
                        return "O Certificado VENCE HOJE as " + certificadosList.get(linha).getHoraVencimento();
                    } else {
                        if (expira.contains("-")) {
                            return "O Certificado EXPIROU há (" + expira.replace("-", "") + ") DIAS!";
                        } else {
                            return "O certificado VENCERÁ em (" + expira + ") DIAS!";
                        }
                    }

                } else {
                    return certificadosList.get(linha).getDescricaoVencimento();
                }
            case 6:
                return ". . .";

            default:
                return null;
        }
    }

    public void atualizaTabela() {
        this.fireTableDataChanged();
    }

    public void preencherTabelaCertificados(List<Certificado> certificadosList) {
        this.certificadosList = certificadosList;
        atualizaTabela();
    }

    public void preencherTabelaCertificadosVencidos(List<Certificado> certificadosList) {
        this.certificadosList = new ArrayList<>();
        for (Certificado certificado : certificadosList) {
            if (certificado.getExpira() <= 0) {
                this.certificadosList.add(certificado);
            }
        }
        atualizaTabela();
    }

    public void preencherTabelaCertificadosAtivos(List<Certificado> certificadosList) {
        this.certificadosList = new ArrayList<>();
        for (Certificado certificado : certificadosList) {
            if (certificado.getExpira() > 0) {
                this.certificadosList.add(certificado);
            }
        }
        atualizaTabela();
    }

    public void preencherTabelaCertificadosVencemAte30Dias(List<Certificado> certificadosList) {
        this.certificadosList = new ArrayList<>();
        for (Certificado certificado : certificadosList) {
            if (certificado.getExpira() >= 0 && certificado.getExpira() <= 30) {
                this.certificadosList.add(certificado);
            }
        }
        atualizaTabela();
    }

    public int retornarQtdeRegistros() {
        return this.certificadosList.size();
    }

    public int retornarExpira(int linhaSelecionada) {
        return certificadosList.get(linhaSelecionada).getExpira();
    }

    public String retornarNome(int linhaSelecionada) {
        return certificadosList.get(linhaSelecionada).getNome();
    }

    public String retornarDataVencimentoSTR(int linhaSelecionada) {
        Date dataVencimento = certificadosList.get(linhaSelecionada).getDataVencimento();
        if (dataVencimento != null) {
            return DataUtils.formataParaBR(dataVencimento);
        }
        return null;
    }

    public String retornarAlias(int linhaSelecionada) {
        return certificadosList.get(linhaSelecionada).getAlias();
    }

    public String retornarDescricao(int linhaSelecionada) {
        return certificadosList.get(linhaSelecionada).getDescricaoVencimento();
    }

    public int retornarId(int linhaSelecionada) {
        return certificadosList.get(linhaSelecionada).getId();
    }
}
