package br.com.meetinginsights.repository;

import org.springframework.stereotype.Repository;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TranscricaoRepository {

    public Long salvar(String texto, Long idCliente) {
        String sql = "INSERT INTO T_TRANSCRICAO (ID_CLIENTE, DATA_REUNIAO, TEXTO_BRUTO, STATUS_PROCESSAMENTO) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_TRANSCRICAO"})) {

            stmt.setLong(1, idCliente);
            stmt.setDate(2, new Date(System.currentTimeMillis()));
            stmt.setCharacterStream(3, new StringReader(texto), texto.length());
            stmt.setString(4, "CONCLUIDO");

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao persistir transcrição no Oracle: " + e.getMessage());
        }
        return null;
    }
}