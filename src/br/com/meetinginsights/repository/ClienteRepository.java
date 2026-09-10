package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.Cliente;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    public List<Cliente> listarPorVendedor(Long idVendedor) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, ID_VENDEDOR, RAZAO_SOCIAL, CNPJ, SEGMENTO, DS_EMAIL " +
                "FROM T_CLIENTE " +
                "WHERE ID_VENDEDOR = ? " +
                "ORDER BY RAZAO_SOCIAL ASC";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idVendedor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = new Cliente(
                            rs.getLong("ID_CLIENTE"),
                            rs.getLong("ID_VENDEDOR"),
                            rs.getString("RAZAO_SOCIAL"),
                            rs.getString("CNPJ"),
                            rs.getString("SEGMENTO"),
                            rs.getString("DS_EMAIL")
                    );
                    lista.add(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes do vendedor " + idVendedor + ": " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT ID_CLIENTE, ID_VENDEDOR, RAZAO_SOCIAL, CNPJ, SEGMENTO, DS_EMAIL " +
                "FROM T_CLIENTE ORDER BY RAZAO_SOCIAL ASC";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getLong("ID_CLIENTE"),
                        rs.getLong("ID_VENDEDOR"),
                        rs.getString("RAZAO_SOCIAL"),
                        rs.getString("CNPJ"),
                        rs.getString("SEGMENTO"),
                        rs.getString("DS_EMAIL")
                );
                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean cadastrar(Cliente c) {
        // ID_CLIENTE é gerado automaticamente pelo Oracle IDENTITY
        String sql = "INSERT INTO T_CLIENTE (ID_VENDEDOR, RAZAO_SOCIAL, CNPJ, SEGMENTO, DS_EMAIL) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, c.getIdVendedor());
            stmt.setString(2, c.getRazaoSocial());
            stmt.setString(3, c.getCnpj());
            stmt.setString(4, c.getSegmento());
            stmt.setString(5, c.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente no Oracle: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}