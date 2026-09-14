package br.com.meetinginsights.test;

import br.com.meetinginsights.domain.Cliente;
import br.com.meetinginsights.service.RegraNegocioService;

public class RegraNegocioTest {

    public static void main(String[] args) {
        RegraNegocioService service = new RegraNegocioService();
        System.out.println("====== INICIANDO BATERIA DE TESTES DE REGRAS DE NEGÓCIO ======\n");

        System.out.println("[TESTE 1] Validação de CNPJ:");
        boolean cnpjValido = service.validarCNPJ("11.222.333/0001-81");
        boolean cnpjInvalido = service.validarCNPJ("11111111111111");
        System.out.println("  - CNPJ Válido: " + (cnpjValido ? "PASSOU (Válido)" : "FALHOU"));
        System.out.println("  - CNPJ Inválido: " + (!cnpjInvalido ? "PASSOU (Rejeitado com sucesso)" : "FALHOU"));

        System.out.println("\n[TESTE 2] Classificação de SLA:");
        String slaCritico = service.classificarCriticidadeAtendimento("ALTO", 95000.0);
        String slaNormal = service.classificarCriticidadeAtendimento("BAIXO", 10000.0);
        System.out.println("  - Risco Alto + Orçamento Alto -> " + slaCritico + " (Esperado: SLA_IMEDIATO_DIRETORIA)");
        System.out.println("  - Risco Baixo + Orçamento Baixo -> " + slaNormal + " (Esperado: SLA_REGULAR)");

        System.out.println("\n[TESTE 3] Cálculo do Score do Lead:");
        int scoreAlto = service.calcularScoreEngajamento("POSITIVO", "UPSELL", 60000.0);
        int scoreBaixo = service.calcularScoreEngajamento("NEGATIVO", "NENHUMA", 0.0);
        System.out.println("  - Oportunidade Quente -> Score: " + scoreAlto + " / 100");
        System.out.println("  - Oportunidade Fria -> Score: " + scoreBaixo + " / 100");

        System.out.println("\n[TESTE 4] Validação de Consistência de Cliente:");
        Cliente cValido = new Cliente(1L, 2L, "TOTVS S/A", "11.222.333/0001-81", "Tecnologia", "contato@totvs.com");
        Cliente cInvalido = new Cliente(2L, 0L, "X", "0000", "Varejo", "invalido");
        System.out.println("  - Cliente Válido: " + (service.validarDadosObrigatoriosCliente(cValido) ? "APROVADO" : "REPROVADO"));
        System.out.println("  - Cliente Inválido: " + (!service.validarDadosObrigatoriosCliente(cInvalido) ? "BARRADO CORRETAMENTE" : "FALHOU"));

        System.out.println("\n====== TESTES CONCLUÍDOS COM SUCESSO ======");
    }
}