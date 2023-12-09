package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.Certificado;
import com.dynns.cloudtecnologia.certificados.utils.DataUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public void save(Certificado certificado) {
        String sql = "INSERT INTO certificado "
                + "(nome,alias,dtVencimento,hrVencimento,descricao_vencimento,expira,"
                + "imagemCertificado)"
                + " values (?,?,?,?,?,?,?)";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, certificado.getNome());
            pst.setString(2, certificado.getAlias());
            pst.setDate(3, certificado.getDataVencimento());
            pst.setString(4, certificado.getHoraVencimento());
            pst.setString(5, certificado.getDescricaoVencimento());
            pst.setInt(6, certificado.getExpira());
            pst.setBytes(7, certificado.getCertificadoByte());

            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new GeralException("Erro ao salvar ertificado: " + ex.getMessage());
        }
    }

    @Override
    public Certificado findById(int id) {
        String sql = "SELECT * from certificado where id=?";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Certificado certificado = new Certificado();
                    certificado.setId(rs.getInt(COLUNA_ID));
                    certificado.setNome(rs.getString(COLUNA_NOME));
                    certificado.setAlias(rs.getString(COLUNA_ALIAS));
                    certificado.setDataVencimento(rs.getDate(COLUNA_DATA_VENCIMENTO));
                    certificado.setHoraVencimento(rs.getString(COLUNA_HORA_VENCIMENTO));
                    certificado.setDescricaoVencimento(rs.getString(COLUNA_DESCRICAO_VENCIMENTO));
                    certificado.setCertificadoByte(rs.getBytes(COLUNA_IMG_CERTIFICADO));

                    return certificado;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter Certificado by Id " + ex.getMessage());
        }
        throw new GeralException("Certificado não localizado com id " + id);
    }

    @Override
    public Certificado findByIdNotBytes(int id) {
        String sql = "select id,nome,alias,dtvencimento,hrvencimento,descricao_vencimento,expira  from certificado where id=?";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Certificado certificado = new Certificado();
                    certificado.setId(rs.getInt(COLUNA_ID));
                    certificado.setNome(rs.getString(COLUNA_NOME));
                    certificado.setAlias(rs.getString(COLUNA_ALIAS));
                    certificado.setDataVencimento(rs.getDate(COLUNA_DATA_VENCIMENTO));
                    certificado.setHoraVencimento(rs.getString(COLUNA_HORA_VENCIMENTO));
                    certificado.setDescricaoVencimento(rs.getString(COLUNA_DESCRICAO_VENCIMENTO));

                    return certificado;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter Certificado by Id " + ex.getMessage());
        }
        throw new GeralException("Certificado não localizado com id " + id);
    }

    @Override
    public List<Certificado> findAllFilter(Certificado filter) {

        String sql = "select * from certificado";
        List<Object> parameters = new ArrayList<>();

        if (Objects.nonNull(filter.getNome()) && !filter.getNome().isEmpty()) {
            sql += " where nome ilike ?";
            parameters.add("%" + filter.getNome() + "%");
        }

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            for (Object parameter : parameters) {
                pst.setObject(parameterIndex, parameter);
                parameterIndex++;
            }

            try (ResultSet rs = pst.executeQuery()) {
                List<Certificado> certificados = new ArrayList<>();
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
                return certificados;
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao listar Certificados (CertificadoDAO) " + ex);
        }
    }

    @Override
    public void deletarCertificado(int idCertificado) {
        String sql = "delete from certificado where id = ? ";
        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, idCertificado);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new GeralException("Erro ao Deletar Certificado id: " + idCertificado + " (CertificadoDAO)");
        }
    }

    @Override
    public void deletarCertificadosVencidos() {
        String sql = "delete from certificado where dtVencimento < CURRENT_TIMESTAMP";
        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.execute();
        } catch (SQLException ex) {
            throw new GeralException("*** ERRO: Não foi possível deletar os certificados vencidos: " + ex.getMessage());
        }
    }

    @Override
    public boolean certificadoExists(Certificado certificado) {
        String sql = "select * from certificado where alias = ? "
                + "AND imagemCertificado = ? AND dtVencimento =?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, certificado.getAlias());
            pst.setBytes(2, certificado.getCertificadoByte());
            pst.setDate(3, certificado.getDataVencimento());

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao consultar certificado certificadoExists " + ex.getMessage());
        }
        return false;
    }

}
