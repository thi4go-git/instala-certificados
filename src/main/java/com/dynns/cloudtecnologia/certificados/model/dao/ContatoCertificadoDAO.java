package com.dynns.cloudtecnologia.certificados.model.dao;

import com.dynns.cloudtecnologia.certificados.conexao.Conexao;
import com.dynns.cloudtecnologia.certificados.exception.GeralException;
import com.dynns.cloudtecnologia.certificados.model.entity.ContatoCertificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContatoCertificadoDAO implements IContatoCertificado {

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_ID_CERTIFICADO = "id_certificado";
    private static final String COLUNA_NOME_CONTATO = "nome_contato";
    private static final String COLUNA_TELEFONE_CONTATO = "telefone_contato";
    private static final String COLUNA_CELULAR_CONTATO = "celular_contato";
    private static final String COLUNA_EMAIL_CONTATO = "email_contato";

    @Override
    public ContatoCertificado retornarContatoCertificado(int idCertificado) {
        String sql = "select * from contato_certificado where id_certificado=?";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, idCertificado);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    ContatoCertificado contato = new ContatoCertificado();
                    contato.setId(rs.getInt(COLUNA_ID));
                    contato.setIdCertificado(rs.getInt(COLUNA_ID_CERTIFICADO));
                    contato.setNomeContato(rs.getString(COLUNA_NOME_CONTATO));
                    contato.setTelefoneContato(rs.getString(COLUNA_TELEFONE_CONTATO));
                    contato.setCelularContato(rs.getString(COLUNA_CELULAR_CONTATO));
                    contato.setEmailContato(rs.getString(COLUNA_EMAIL_CONTATO));

                    return contato;
                }
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao obter ContatoCertificado by idCertificado " + ex.getMessage());
        }
        return new ContatoCertificado();
    }

    @Override
    public void salvarContatoCertificado(ContatoCertificado contato) {
        String sql = "INSERT INTO contato_certificado "
                + "(id_certificado,nome_contato,telefone_contato,celular_contato,email_contato)"
                + " values (?,?,?,?,?)";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, contato.getIdCertificado());
            pst.setString(2, contato.getNomeContato());
            pst.setString(3, contato.getTelefoneContato());
            pst.setString(4, contato.getCelularContato());
            pst.setString(5, contato.getEmailContato());

            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new GeralException("Erro ao salvar Contato: " + ex.getMessage());
        }
    }

    @Override
    public void atualizarContatoCertificado(ContatoCertificado contatoBD) {
        String sql = "UPDATE contato_certificado SET "
                + "nome_contato=?, telefone_contato=?, celular_contato=?, email_contato=? "
                + "WHERE id_certificado=?";

        try (Connection connection = Conexao.getConexao(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, contatoBD.getNomeContato());
            pst.setString(2, contatoBD.getTelefoneContato());
            pst.setString(3, contatoBD.getCelularContato());
            pst.setString(4, contatoBD.getEmailContato());
            pst.setInt(5, contatoBD.getIdCertificado());

            int linhasAfetadas = pst.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new GeralException("Contato não encontrado para atualização. ID: " + contatoBD.getIdCertificado());
            }
        } catch (SQLException ex) {
            throw new GeralException("Erro ao atualizar Contato: " + ex.getMessage());
        }
    }

}
