package br.com.instalador.model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {

    private static final String BANCO = "jdbc:postgresql://192.168.1.253:5432/certificado";
    static final String DRIVER = "org.postgresql.Driver";
    static final String USUARIO = "postgres";
    static String SENHA = "1234";
    private static Connection con = null;

    public static Connection getConexao() {
        if (con == null) {
            try {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(BANCO, USUARIO, SENHA);
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "ERRO AO CONECTAR AO BANCO! "
                        + "Causa: " + ex.getMessage());
            }
        }
        return con;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        if (con == null) {
            con = getConexao();
        }
        try {
            return con.prepareStatement(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de sql: "
                    + e.getMessage());
        }
        return null;
    }
}
