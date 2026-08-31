package br.com.meetinginsights.app;

import br.com.meetinginsights.repository.ClienteRepository;
import br.com.meetinginsights.service.AgenteDecisaoService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteRepository clienteRepo = new ClienteRepository();
        AgenteDecisaoService agenteService = new AgenteDecisaoService();

        while (true) {
            System.out.println("\n===== MEETING INSIGHTS: AGENTE MCP =====");
            System.out.println("1 - Inserir novo Cliente no Oracle");
            System.out.println("2 - Simular Fluxo de Decisão da IA (Python + Ollama)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.println("\n--- Cadastro de Cliente ---");
                // Sua lógica existente do JDBC aqui
                System.out.println("Cliente salvo no Oracle com sucesso!");

            } else if (opcao == 2) {
                System.out.println("\n--- Iniciando Análise da IA ---");
                // Aponta para o arquivo Python que você criou no Windows
                String caminhoScript = "C:\\Users\\Vieira\\Downloads\\Challenge\\AgenteAI.py";
                agenteService.executarFluxoCompleto(caminhoScript);

            } else if (opcao == 0) {
                System.out.println("Encerrando sistema...");
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}