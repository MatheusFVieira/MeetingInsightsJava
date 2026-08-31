package br.com.meetinginsights.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AgenteDecisaoService {

    public void executarFluxoCompleto(String caminhoScriptPython) {
        System.out.println("Aguardando processamento do LLM local (Qwen)...");
        String jsonResult = extrairDecisaoDaIA(caminhoScriptPython);

        if (jsonResult.isEmpty()) {
            System.out.println("Erro: Nenhum dado retornado pela IA.");
            return;
        }

        System.out.println("\n>> JSON Recebido do Python:");
        System.out.println(jsonResult);

        // Parse nativo (sem bibliotecas externas) para capturar as chaves
        String risco = extrairValorJson(jsonResult, "riscoChurn");
        String oportunidade = extrairValorJson(jsonResult, "oportunidadeUpsell");
        String sentimento = extrairValorJson(jsonResult, "sentimentoGeral");
        double orcamento = 0.0;
        try {
            orcamento = Double.parseDouble(extrairValorJson(jsonResult, "orcamentoEstimado"));
        } catch (NumberFormatException e) {
            System.out.println("Aviso: Orçamento não pôde ser convertido.");
        }

        System.out.println("--- Aplicando Regras de Negócio TOTVS ---");

        // Regra 1: Roteamento
        if (oportunidade.equals("CROSS_SELL") || oportunidade.equals("UPSELL")) {
            System.out.println("[ROTEAMENTO] Oportunidade mapeada. Encaminhando alerta para a fila de Vendas.");
        } else {
            System.out.println("[ROTEAMENTO] Reunião de rotina. Encaminhando para a fila de Customer Success (CS).");
        }

        // Regra 2: Status de Urgência
        if (risco.equals("ALTO")) {
            System.out.println("[ALERTA WEBHOOK] Risco de Churn CRÍTICO detectado. Disparando notificação para gerência!");
        } else {
            System.out.println("[MONITORAMENTO] Risco estabilizado (" + risco + "). Fluxo padrão mantido.");
        }

        // Regra 3: Comissão de Vendas
        if (orcamento > 0) {
            double comissao = orcamento * 0.10;
            System.out.printf("[FINANCEIRO] Orçamento de R$ %.2f detectado. Projeção de comissão (10%%): R$ %.2f\n", orcamento, comissao);
        } else {
            System.out.println("[FINANCEIRO] Nenhum orçamento qualificado na transcrição.");
        }
    }

    private String extrairDecisaoDaIA(String caminhoScriptPython) {
        StringBuilder jsonOutput = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder("python", caminhoScriptPython);
            pb.redirectErrorStream(true);
            Process processo = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream(), "UTF-8"));
            String linha;
            boolean lendoJson = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.contains("{")) lendoJson = true;
                if (lendoJson) jsonOutput.append(linha).append("\n");
                if (linha.contains("}")) break;
            }
            processo.waitFor();
        } catch (Exception e) {
            System.out.println("Falha ao abrir processo nativo: " + e.getMessage());
        }
        return jsonOutput.toString();
    }

    private String extrairValorJson(String json, String chave) {
        String[] partes = json.split("\"" + chave + "\"\\s*:\\s*");
        if (partes.length > 1) {
            String valorBruto = partes[1].split(",|\n|\\}")[0].trim();
            return valorBruto.replace("\"", "");
        }
        return "";
    }
}