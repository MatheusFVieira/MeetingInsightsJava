package br.com.meetinginsights.repository;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoOracle {

    private static final Properties properties = carregarProperties();

    private static Properties carregarProperties() {
        Properties props = new Properties();

        Path caminho = Path.of("secrets.properties");

        try (Reader reader = Files.newBufferedReader(
                caminho,
                StandardCharsets.UTF_8
        )) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Não foi possível carregar secrets.properties",
                    e
            );
        }

        return props;
    }

    public static Connection conectar() {

        String url = properties.getProperty("ORACLE_URL");
        String user = properties.getProperty("ORACLE_USER");
        String pass = properties.getProperty("ORACLE_PASS");

        try {
            return DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            System.out.println(
                    "Erro na conexão com Oracle: " + e.getMessage()
            );

            return null;
        }
    }
}