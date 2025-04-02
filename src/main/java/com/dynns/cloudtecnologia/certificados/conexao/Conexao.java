package com.dynns.cloudtecnologia.certificados.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    private Conexao() {
    }

    private static Connection connection = null;

    private static final String URL_BANCO = "jdbc:postgresql://ip_aqui:porta/banco";
    private static final String USUARIO = "postgres";
    private static final String PASS_BD = "senha cert aqui";

    public static Connection getConexao() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL_BANCO,
                    USUARIO, PASS_BD);
        }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = getConexao();
        }
        return connection.prepareStatement(sql);
    }

}
