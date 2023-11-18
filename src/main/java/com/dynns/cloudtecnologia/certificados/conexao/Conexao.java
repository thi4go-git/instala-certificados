package com.dynns.cloudtecnologia.certificados.conexao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    private Conexao() {
    }

    private static Connection connection = null;

    public static Connection getConexao() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Dotenv dotenv = Dotenv.configure().load();

            String url = dotenv.get("URL_BANCO");
            String usuario = dotenv.get("USUARIO_BANCO");
            String senha = dotenv.get("SENHA_BANCO");

            connection = DriverManager.getConnection(url, usuario, senha);
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
