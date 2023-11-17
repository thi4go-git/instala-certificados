package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CertificadoDAO implements ICertificado {

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_DESCRICAO_VENCIMENTO = "descricao_vencimento";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_ALIAS = "alias";
    private static final String COLUNA_DATA_VENCIMENTO = "dtVencimento";
    private static final String COLUNA_HORA_VENCIMENTO = "hrVencimento";
    private static final String COLUNA_IMG_CERTIFICADO = "imagemCertificado";

    private static final String MSG_SENHA_INCORRETA = "A Senha do Certificado não confere com a senha do Instalador!";

    @Override
    public Certificado findById(int id) {
        try {
            String sql = "SELECT id,imagemCertificado from certificado where id=?";
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Certificado cert = new Certificado();
                cert.setId(rs.getInt("id"));
                cert.setCertificadoByte(rs.getBytes(COLUNA_IMG_CERTIFICADO));
                return cert;
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter Certificado by Id " + ex.getMessage());
        }
        throw new GeralException("Certificado não localizado com id " + id);
    }

    @Override
    public List<Certificado> findAll() {
        List<Certificado> certificados = new ArrayList<>();

        try {
            String sql = "select * from certificado";
            PreparedStatement pst = Conexao.getPreparedStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int diferencaEmDias = 0;
                if (!rs.getString(COLUNA_DESCRICAO_VENCIMENTO).equals(MSG_SENHA_INCORRETA)) {
                    String dataAtual = DataUtils.formataParaBD(new Date());
                    String dataVencimento = DataUtils.formataParaBD(rs.getDate(COLUNA_DATA_VENCIMENTO));

                    diferencaEmDias = DataUtils.retornarDiferencaEmDias(dataAtual, dataVencimento);
                }
                Certificado certificado = new Certificado();
                certificado.setId(rs.getInt(COLUNA_ID));
                certificado.setNome(rs.getString(COLUNA_NOME));
                certificado.setAlias(rs.getString(COLUNA_ALIAS));
                certificado.setDataVencimento(rs.getDate(COLUNA_DATA_VENCIMENTO));
                certificado.setHoraVencimento(rs.getString(COLUNA_HORA_VENCIMENTO));
                certificado.setDescricaoVencimento(rs.getString(COLUNA_DESCRICAO_VENCIMENTO));
                certificado.setExpira(diferencaEmDias);
                certificado.setCertificadoByte(rs.getBytes(COLUNA_IMG_CERTIFICADO));

                certificados.add(certificado);
            }

            Collections.sort(certificados, Comparator.comparingInt(Certificado::getExpira));

        } catch (SQLException ex) {
            throw new GeralException("Erro ao listar Certificados (CertificadoDAO)");
        }
        return certificados;
    }

}
