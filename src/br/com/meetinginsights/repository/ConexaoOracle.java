package br.com.meetinginsights.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoOracle {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "rm566198"; //Colocar usuário
    private static final String PASS = "300906"; //Colocar senha

    public static Connection conectar() {
        try {
            // Força a leitura do driver do Oracle na memória
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException e) {
            System.out.println("Erro crítico: Biblioteca do Oracle não encontrada no projeto.");
            return null;
        } catch (SQLException e) {
            System.out.println("Erro na conexão com o Oracle remoto: " + e.getMessage());
            return null;
        }
    }
}