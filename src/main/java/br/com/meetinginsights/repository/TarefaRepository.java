package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.TarefaDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TarefaRepository {

    public List<TarefaDTO> listarPorVendedor(Long idVendedor) {
        List<TarefaDTO> tarefas = new ArrayList<>();

        String sql = "SELECT " +
                "    a.ID_ACAO, " +
                "    a.TIPO_ACAO, " +
                "    a.DATA_ACAO, " +
                "    a.STATUS_ALERTA, " +
                "    p.NOME_PRODUTO, " +
                "    c.RAZAO_SOCIAL, " +
                "    ia.AMEACA_CHURN, " +
                "    ia.OPORTUNIDADE_UPSELL, " +
                "    ia.SENTIMENTO_GERAL, " +
                "    ia.ORCAMENTO_ESTIMADO " +
                "FROM T_ACAO_AGENTE a " +
                "JOIN T_PRODUTO p ON a.ID_PRODUTO = p.ID_PRODUTO " +
                "JOIN T_ANALISE_IA ia ON a.ID_ANALISE = ia.ID_ANALISE " +
                "JOIN T_TRANSCRICAO t ON ia.ID_TRANSCRICAO = t.ID_TRANSCRICAO " +
                "JOIN T_CLIENTE c ON t.ID_CLIENTE = c.ID_CLIENTE " +
                "WHERE c.ID_VENDEDOR = ? " +
                "ORDER BY a.DATA_ACAO DESC";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try (Connection conn = ConexaoOracle.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idVendedor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String produto = rs.getString("NOME_PRODUTO");
                    String razaoSocial = rs.getString("RAZAO_SOCIAL");
                    String statusAlerta = rs.getString("STATUS_ALERTA");
                    String tipoAcao = rs.getString("TIPO_ACAO");
                    String dataFormatada = rs.getTimestamp("DATA_ACAO") != null ? sdf.format(rs.getTimestamp("DATA_ACAO")) : "Recente";

                    String ameacaChurn = rs.getString("AMEACA_CHURN");
                    String oportunidadeUpsell = rs.getString("OPORTUNIDADE_UPSELL");
                    String sentimentoGeral = rs.getString("SENTIMENTO_GERAL");
                    Double orcamentoEstimado = rs.getDouble("ORCAMENTO_ESTIMADO");

                    String titulo = "Apresentar proposta de implantação: " + produto;
                    if ("URGENTE".equalsIgnoreCase(statusAlerta) || "ALTO".equalsIgnoreCase(ameacaChurn)) {
                        titulo = "Contenção de Churn & Diagnóstico: " + produto;
                    }

                    TarefaDTO dto = new TarefaDTO(
                            rs.getLong("ID_ACAO"),
                            titulo,
                            razaoSocial,
                            produto,
                            tipoAcao,
                            statusAlerta,
                            dataFormatada,
                            ameacaChurn,
                            oportunidadeUpsell,
                            sentimentoGeral,
                            orcamentoEstimado
                    );

                    tarefas.add(dto);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar tarefas do vendedor no Oracle: " + e.getMessage());
            e.printStackTrace();
        }

        return tarefas;
    }
}