package br.com.instalador.model.repository;

import br.com.instalador.service.ICertificadoDAO;
import br.com.instalador.utils.Data;
import br.com.instalador.model.entity.Certificado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CertificadoRepository implements ICertificadoDAO {

    private static final int QTDE_DIAS_A_VENCER = 30;

    SimpleDateFormat formataDataBD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formataBR = new SimpleDateFormat("dd/MM/yyyy");
    Data dt = new Data();

    @Override
    public String retornarCaminhoDaPasta() {
        String CAMINHO_PASTA = null;
        String SQL = "SELECT local_pasta FROM configuracao_certificado";
        PreparedStatement pst = Conexao.getPreparedStatement(SQL);
        try {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CAMINHO_PASTA = rs.getString("local_pasta");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarCaminhoDaPasta() " + ex);
        }
        return CAMINHO_PASTA;
    }

    @Override
    public List<Certificado> retornarListaDeCertificados() {
        List<Certificado> listaCertificados = new ArrayList<>();
        String SQL = "select * from certificado";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int DIFERENCA_EM_DIAS = 0;
                if (!rs.getString("descricao_vencimento").equals("A Senha do Certificado não confere com a senha do Instalador!")) {
                    Date dtHoje = new Date();
                    String DATA_MENOR = formataDataBD.format(dtHoje);
                    String DT_MAIOR = formataDataBD.format(rs.getDate("dtVencimento"));
                    try {
                        DT_MAIOR = formataDataBD.format(formataBR.parse(DT_MAIOR));
                    } catch (ParseException ex) {

                    }
                    DIFERENCA_EM_DIAS = dt.retornarDiferencaEmDias(DATA_MENOR, DT_MAIOR);
                }
                Certificado certificado = new Certificado(rs.getInt("id"), rs.getString("nome"), rs.getString("alias"), rs.getDate("dtVencimento"),
                        rs.getString("hrVencimento"), rs.getString("descricao_vencimento"), DIFERENCA_EM_DIAS, rs.getBytes("imagemCertificado"));
                //
                listaCertificados.add(certificado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarListaDeCertificados() " + ex);
        }

        listaCertificados.sort(new ExpiraComparador());
        return listaCertificados;
    }

    public List<Certificado> retornarListaDeCertificadosVencerNDias() {
        List<Certificado> listaCertificados = new ArrayList<>();

        String SQL = "select * from certificado where dtVencimento between "
                + "current_date and current_date+" + QTDE_DIAS_A_VENCER + " "
                + "order by dtVencimento asc ";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int DIFERENCA_EM_DIAS = 0;
                if (!rs.getString("descricao_vencimento").equals("A Senha do Certificado não confere com a senha do Instalador!")) {
                    Date dtHoje = new Date();
                    String DATA_MENOR = formataDataBD.format(dtHoje);
                    String DT_MAIOR = formataDataBD.format(rs.getDate("dtVencimento"));
                    try {
                        DT_MAIOR = formataDataBD.format(formataBR.parse(DT_MAIOR));
                    } catch (ParseException ex) {

                    }
                    DIFERENCA_EM_DIAS = dt.retornarDiferencaEmDias(DATA_MENOR, DT_MAIOR);
                }
                Certificado certificado = new Certificado(rs.getInt("id"), rs.getString("nome"), rs.getString("alias"), rs.getDate("dtVencimento"),
                        rs.getString("hrVencimento"), rs.getString("descricao_vencimento"), DIFERENCA_EM_DIAS, rs.getBytes("imagemCertificado"));
                //
                listaCertificados.add(certificado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarListaDeCertificados() " + ex);
        }

        listaCertificados.sort(new ExpiraComparador());
        return listaCertificados;
    }

    @Override
    public List<Certificado> retornarListaDeCertificadosNome(String nome) {
        List<Certificado> listaCertificados = new ArrayList<>();
        String SQL = "select * from certificado where nome ilike '%" + nome + "%' ";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int DIFERENCA_EM_DIAS = 0;
                if (!rs.getString("descricao_vencimento").equals("A Senha do Certificado não confere com a senha do Instalador!")) {
                    Date dtHoje = new Date();
                    String DATA_MENOR = formataDataBD.format(dtHoje);
                    String DT_MAIOR = formataDataBD.format(rs.getDate("dtVencimento"));
                    try {
                        DT_MAIOR = formataDataBD.format(formataBR.parse(DT_MAIOR));
                    } catch (ParseException ex) {

                    }
                    DIFERENCA_EM_DIAS = dt.retornarDiferencaEmDias(DATA_MENOR, DT_MAIOR);
                }
                Certificado certificado = new Certificado(rs.getInt("id"), rs.getString("nome"), rs.getString("alias"), rs.getDate("dtVencimento"),
                        rs.getString("hrVencimento"), rs.getString("descricao_vencimento"), DIFERENCA_EM_DIAS, rs.getBytes("imagemCertificado"));
                //
                listaCertificados.add(certificado);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarListaDeCertificadosNome(String nome) " + ex);
        }
        listaCertificados.sort(new ExpiraComparador());
        return listaCertificados;
    }

    @Override
    public boolean consultarExistenciaCertifiado(Certificado certificado) {
        String SQL = "select * from certificado where alias = ? "
                + "AND imagemCertificado = ? AND dtVencimento =? AND hrVencimento =? ";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setString(1, certificado.getALIAS());
            pst.setBytes(2, certificado.getBYTES_CERTIFICADO());
            pst.setDate(3, certificado.getDT_VENCIMENTO());
            pst.setString(4, certificado.getHORA_VENCIMENTO());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return true;//////
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: consultarExistenciaCertifiado() " + ex);
        }
        return false;
    }

    @Override
    public boolean inserirCertificado(Certificado certificado) {
        String SQL = "INSERT INTO certificado "
                + "(nome,alias,dtVencimento,hrVencimento,descricao_vencimento,expira,"
                + "imagemCertificado)"
                + " values (?,?,?,?,?,?,?)";
        try {

            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setString(1, certificado.getNOME());
            pst.setString(2, certificado.getALIAS());
            pst.setDate(3, certificado.getDT_VENCIMENTO());
            pst.setString(4, certificado.getHORA_VENCIMENTO());
            pst.setString(5, certificado.getDESC_VENCIMENTO());
            pst.setInt(6, certificado.getEXPIRA());
            pst.setBytes(7, certificado.getBYTES_CERTIFICADO());
            //
            pst.executeUpdate();
            //          
            return true;
            //
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: inserirCertificado() " + ex);
            System.out.println(ex.getCause());
        }
        return false;
    }

    @Override
    public Certificado baixarCertificadoPorID(int id) {
        String SQL = "SELECT id,imagemCertificado from certificado where id=?";
        PreparedStatement pst = Conexao.getPreparedStatement(SQL);
        try {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Certificado cert = new Certificado(rs.getInt("id"),
                        rs.getBytes("imagemCertificado"));
                return cert;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: baixarCertificadoPorID() +" + ex);
        }
        return null;
    }

    @Override
    public boolean atualizarCertificado(Certificado certificado) {
        String SQL = "UPDATE certificado SET nome =?,alias=?,dtVencimento=?,hrVencimento=?,"
                + "descricao_vencimento=?,expira=?, imagemCertificado=?  WHERE id = ?";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setString(1, certificado.getNOME());
            pst.setString(2, certificado.getALIAS());
            pst.setDate(3, certificado.getDT_VENCIMENTO());
            pst.setString(4, certificado.getHORA_VENCIMENTO());
            pst.setString(5, certificado.getDESC_VENCIMENTO());
            pst.setInt(6, certificado.getEXPIRA());
            pst.setBytes(7, certificado.getBYTES_CERTIFICADO());
            //
            pst.setInt(8, certificado.getID());
            //
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: atualizarCertificado() +" + ex);
        }
        return false;

    }

    @Override
    public boolean deletarCertificado(int id) {
        String SQL = "delete from certificado where id = ? ";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setInt(1, id);
            //
            pst.executeUpdate();
            Thread.sleep(100);
            return true;
        } catch (SQLException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: deletarCERTIFICADO_BD() +" + ex);
        }
        return false;
    }

    public class ExpiraComparador implements Comparator<Certificado> {

        @Override
        public int compare(Certificado cert1, Certificado cert2) {
            return Integer.compare(cert1.getEXPIRA(), cert2.getEXPIRA());
        }
    }

    @Override
    public String retornarSenhaMaster() {
        String senhaMaster = null;
        String SQL = "SELECT senha_master FROM configuracao_certificado";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString("senha_master");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarSenhaMaster() " + ex);
        }
        return senhaMaster;
    }

    @Override
    public String retornarSenhaCertificado() {
        String senhaCertificado = null;
        String SQL = "SELECT senha_certificado FROM configuracao_certificado";
        PreparedStatement pst = Conexao.getPreparedStatement(SQL);
        try {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getString("senha_certificado");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: retornarSenhaCertificado() " + ex);
        }
        return senhaCertificado;
    }

    @Override
    public void atualizarPreferencias(String local_pasta, String senha_certificado) {
        try {
            String SQL = "update configuracao_certificado SET local_pasta = ?,senha_certificado = ?";
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setString(1, local_pasta);
            pst.setString(2, senha_certificado);
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: atualizarPreferencias() " + ex);
        }

    }

    @Override
    public boolean deletarCertificadosVencidos() {
        String SQL = "delete from certificado where dtVencimento < CURRENT_TIMESTAMP";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR CERTIFICADOS VENCIDOS DA BASE! "
                    + "deletarCertificadosVencidos " + ex);
        }
        return false;
    }

    @Override
    public boolean deletarCertificadosDuplicados() {
        List<byte[]> listaImagesDuplicadas = new ArrayList<>();
        String SQL = "select \n"
                + "imagemcertificado, count (*) as total\n"
                + "from certificado\n"
                + "group by imagemcertificado\n"
                + "having count (*) > 1";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                listaImagesDuplicadas.add(rs.getBytes("imagemcertificado"));
            }
            //
            return processarListaDeImagesDuplicadas(listaImagesDuplicadas);
            //
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: deletarCertificadosDuplicados() " + ex);
        }
        return false;
    }

    public boolean processarListaDeImagesDuplicadas(List<byte[]> listaImagesDuplicadas) {

        for (byte[] imagemDeletar : listaImagesDuplicadas) {
            int idNaoDeletar = retornarIdNaoDeletar(imagemDeletar);
            if (idNaoDeletar != 0) {
                deletarDuplicado(idNaoDeletar, imagemDeletar);
            }
        }

        return true;
    }

    public int retornarIdNaoDeletar(byte[] imagem) {

        String SQL = "select id,imagemcertificado  "
                + "from certificado c where imagemCertificado = ? limit 1";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setBytes(1, imagem);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro método: deletarCertificadosDuplicados() " + ex);
        }

        return 0;
    }

    public void deletarDuplicado(int id, byte[] imagem) {
        String SQL = "delete from certificado where id != ? and imagemcertificado  = ?";
        try {
            PreparedStatement pst = Conexao.getPreparedStatement(SQL);
            pst.setInt(1, id);
            pst.setBytes(2, imagem);
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERRO AO DELETAR CERTIFICADO DUPLICADO DA BASE! "
                    + "deletarDuplicado " + ex);
        }
    }

}
