package br.com.meetinginsights.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.meetinginsights")
public class Main {
    public static void main(String[] args) {
        // Inicializa o servidor web na porta 8080
        SpringApplication.run(Main.class, args);

        System.out.println("\n=================================================");
        System.out.println(" SERVIDOR MEETING INSIGHTS INICIADO COM SUCESSO ");
        System.out.println(" O Spring Boot está rodando na porta 8080.");
        System.out.println(" Aguardando envio de transcrições do React...");
        System.out.println("=================================================");
    }
}