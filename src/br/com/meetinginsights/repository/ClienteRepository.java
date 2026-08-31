package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClienteRepository {

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO T_CLIENTE (id_vendedor, razao_social, cnpj, segmento) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getIdVendedor());
            stmt.setString(2, cliente.getEmpresa());
            stmt.setString(3, cliente.getCnpj());
            stmt.setString(4, cliente.getSegmento());

            stmt.executeUpdate();
            System.out.println("Cliente salvo no Oracle com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao salvar cliente no banco: " + e.getMessage());
        }
    }
}