package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.Vendedor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VendedorRepository {

    public Vendedor autenticar(String email, String senha) {
        String sql = "SELECT ID_VENDEDOR, NOME, EMAIL_CORPORATIVO, FL_ADMIN " +
                "FROM T_VENDEDOR " +
                "WHERE LOWER(TRIM(EMAIL_CORPORATIVO)) = LOWER(TRIM(?)) AND TRIM(DS_SENHA) = TRIM(?)";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Vendedor v = new Vendedor();
                    v.setIdVendedor(rs.getLong("ID_VENDEDOR"));
                    v.setNomeVendedor(rs.getString("NOME"));
                    v.setEmail(rs.getString("EMAIL_CORPORATIVO"));
                    v.setAdmin(rs.getString("FL_ADMIN"));
                    return v;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao autenticar vendedor no Oracle: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Vendedor> listarTodos() {
        List<Vendedor> lista = new ArrayList<>();
        String sql = "SELECT ID_VENDEDOR, NOME, EMAIL_CORPORATIVO, FL_ADMIN FROM T_VENDEDOR ORDER BY ID_VENDEDOR";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vendedor v = new Vendedor();
                v.setIdVendedor(rs.getLong("ID_VENDEDOR"));
                v.setNomeVendedor(rs.getString("NOME"));
                v.setEmail(rs.getString("EMAIL_CORPORATIVO"));
                v.setAdmin(rs.getString("FL_ADMIN"));
                lista.add(v);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar vendedores: " + e.getMessage());
        }
        return lista;
    }

    public boolean cadastrar(Vendedor v) {
        // O ID_VENDEDOR é gerado automaticamente pelo Oracle IDENTITY
        String sql = "INSERT INTO T_VENDEDOR (NOME, EMAIL_CORPORATIVO, REGIAO_ATUACAO, DS_SENHA, FL_ADMIN) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getNomeVendedor());
            stmt.setString(2, v.getEmail());
            stmt.setString(3, "SP");
            stmt.setString(4, v.getSenha());
            stmt.setString(5, v.getAdmin() != null ? v.getAdmin() : "N");

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar vendedor: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarSenha(Long idVendedor, String novaSenha) {
        String sql = "UPDATE T_VENDEDOR SET DS_SENHA = ? WHERE ID_VENDEDOR = ?";

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setLong(2, idVendedor);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar senha no Oracle: " + e.getMessage());
            return false;
        }
    }
}