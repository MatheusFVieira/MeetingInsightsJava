package br.com.meetinginsights.repository;

import br.com.meetinginsights.domain.AnaliseResponse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class AnaliseIaRepository {

    public void salvarAnaliseEAcao(AnaliseResponse analise, Long idTranscricao) {
        if (idTranscricao == null || analise == null) return;

        String sqlAnalise = "INSERT INTO T_ANALISE_IA (ID_TRANSCRICAO, AMEACA_CHURN, OPORTUNIDADE_UPSELL, SENTIMENTO_GERAL, ORCAMENTO_ESTIMADO) " +
                "VALUES (?, ?, ?, ?, ?)";

        String sqlBuscaProduto = "SELECT ID_PRODUTO FROM T_PRODUTO WHERE UPPER(NOME_PRODUTO) LIKE UPPER(?)";

        String sqlAcao = "INSERT INTO T_ACAO_AGENTE (ID_ANALISE, ID_PRODUTO, TIPO_ACAO, DATA_ACAO, STATUS_ALERTA) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoOracle.conectar()) {
            conn.setAutoCommit(false);

            Long idAnaliseGerado = null;

            // 1. Gravar em T_ANALISE_IA
            try (PreparedStatement stmtAnalise = conn.prepareStatement(sqlAnalise, new String[]{"ID_ANALISE"})) {
                stmtAnalise.setLong(1, idTranscricao);
                stmtAnalise.setString(2, analise.getRiscoChurn() != null ? analise.getRiscoChurn().name() : "BAIXO");
                stmtAnalise.setString(3, analise.getOportunidadeUpsell() != null ? analise.getOportunidadeUpsell().name() : "NENHUMA");
                stmtAnalise.setString(4, analise.getSentimentoGeral() != null ? analise.getSentimentoGeral().name() : "NEUTRO");
                stmtAnalise.setDouble(5, analise.getOrcamentoEstimado() != null ? analise.getOrcamentoEstimado() : 0.0);

                stmtAnalise.executeUpdate();

                try (ResultSet rs = stmtAnalise.getGeneratedKeys()) {
                    if (rs.next()) {
                        idAnaliseGerado = rs.getLong(1);
                    }
                }
            }

            // 2. Buscar ID do Produto correspondente no catálogo
            Long idProduto = null;
            if (analise.getProdutoRecomendado() != null) {
                try (PreparedStatement stmtProd = conn.prepareStatement(sqlBuscaProduto)) {
                    stmtProd.setString(1, "%" + analise.getProdutoRecomendado().trim() + "%");
                    try (ResultSet rsProd = stmtProd.executeQuery()) {
                        if (rsProd.next()) {
                            idProduto = rsProd.getLong("ID_PRODUTO");
                        }
                    }
                }
            }

            // Se não encontrar o produto específico, assume ID 1 como fallback
            if (idProduto == null) {
                idProduto = 1L;
            }

            // 3. Gravar em T_ACAO_AGENTE
            if (idAnaliseGerado != null) {
                try (PreparedStatement stmtAcao = conn.prepareStatement(sqlAcao)) {
                    stmtAcao.setLong(1, idAnaliseGerado);
                    stmtAcao.setLong(2, idProduto);
                    stmtAcao.setString(3, "SUGESTAO_COMERCIAL");
                    stmtAcao.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    stmtAcao.setString(5, "ALTA".equalsIgnoreCase(analise.getRiscoChurn() != null ? analise.getRiscoChurn().name() : "") ? "URGENTE" : "NORMAL");

                    stmtAcao.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Diagnóstico salvo com sucesso em T_ANALISE_IA (#" + idAnaliseGerado + ") e T_ACAO_AGENTE!");

        } catch (SQLException e) {
            System.err.println("Erro ao persistir análise e ação no Oracle: " + e.getMessage());
            e.printStackTrace();
        }
    }
}