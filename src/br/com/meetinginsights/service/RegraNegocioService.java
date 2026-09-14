package br.com.meetinginsights.service;

import br.com.meetinginsights.domain.Cliente;

public class RegraNegocioService {

    public boolean validarCNPJ(String cnpj) {
        if (cnpj == null) return false;
        String limpo = cnpj.replaceAll("\\D", "");
        if (limpo.length() != 14 || limpo.matches("(\\d)\\1{13}")) return false;

        try {
            int soma = 0, peso = 5;
            for (int i = 0; i < 12; i++) {
                soma += (limpo.charAt(i) - '0') * peso;
                peso = (peso == 2) ? 9 : peso - 1;
            }
            int r = soma % 11;
            char dig13 = (r < 2) ? '0' : (char) ((11 - r) + '0');

            soma = 0;
            peso = 6;
            for (int i = 0; i < 13; i++) {
                soma += (limpo.charAt(i) - '0') * peso;
                peso = (peso == 2) ? 9 : peso - 1;
            }
            r = soma % 11;
            char dig14 = (r < 2) ? '0' : (char) ((11 - r) + '0');

            return (dig13 == limpo.charAt(12)) && (dig14 == limpo.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    public String classificarCriticidadeAtendimento(String riscoChurn, double orcamentoEstimado) {
        if ("ALTO".equalsIgnoreCase(riscoChurn) && orcamentoEstimado >= 50000.0) {
            return "SLA_IMEDIATO_DIRETORIA";
        } else if ("ALTO".equalsIgnoreCase(riscoChurn) || orcamentoEstimado >= 80000.0) {
            return "SLA_PRIORITARIO_24H";
        } else if ("MEDIO".equalsIgnoreCase(riscoChurn)) {
            return "SLA_MODERADO_48H";
        }
        return "SLA_REGULAR";
    }

    public int calcularScoreEngajamento(String sentimento, String oportunidade, double orcamento) {
        int score = 20;

        if ("POSITIVO".equalsIgnoreCase(sentimento)) score += 30;
        else if ("NEUTRO".equalsIgnoreCase(sentimento)) score += 15;

        if ("CROSS_SELL".equalsIgnoreCase(oportunidade) || "UPSELL".equalsIgnoreCase(oportunidade)) {
            score += 30;
        }

        if (orcamento >= 50000.0) score += 20;
        else if (orcamento > 0) score += 10;

        return Math.min(score, 100);
    }

    public boolean validarDadosObrigatoriosCliente(Cliente cliente) {
        if (cliente == null) return false;
        if (cliente.getRazaoSocial() == null || cliente.getRazaoSocial().trim().length() < 3) return false;
        if (cliente.getIdVendedor() == null || cliente.getIdVendedor() <= 0) return false;
        return validarCNPJ(cliente.getCnpj());
    }
}