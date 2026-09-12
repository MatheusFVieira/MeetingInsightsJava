package br.com.meetinginsights.controller;

import br.com.meetinginsights.domain.AnaliseResponse;
import br.com.meetinginsights.domain.TranscricaoRequest;
import br.com.meetinginsights.repository.AnaliseIaRepository;
import br.com.meetinginsights.repository.TranscricaoRepository;
import br.com.meetinginsights.service.AgenteDecisaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analise")
@CrossOrigin(origins = "http://localhost:8081")
public class AnaliseComercialController {

    private final AgenteDecisaoService agenteService;
    private final TranscricaoRepository transcricaoRepository;
    private final AnaliseIaRepository analiseIaRepository;

    public AnaliseComercialController(AgenteDecisaoService agenteService,
                                      TranscricaoRepository transcricaoRepository,
                                      AnaliseIaRepository analiseIaRepository) {
        this.agenteService = agenteService;
        this.transcricaoRepository = transcricaoRepository;
        this.analiseIaRepository = analiseIaRepository;
    }

    @PostMapping("/processar")
    public ResponseEntity<AnaliseResponse> processarTranscricao(@RequestBody TranscricaoRequest request) {
        if (request.getIdCliente() == null) {
            return ResponseEntity.badRequest().build();
        }

        // 1. Grava a transcrição e recupera o ID gerado
        Long idTranscricao = transcricaoRepository.salvar(request.getTexto(), request.getIdCliente());

        // 2. Executa a IA (Qwen 2.5)
        AnaliseResponse resultado = agenteService.analisar(request.getTexto());

        // 3. Persiste o resultado da IA e gera a ação do agente no Oracle
        if (resultado != null && idTranscricao != null) {
            analiseIaRepository.salvarAnaliseEAcao(resultado, idTranscricao);
        }

        return ResponseEntity.ok(resultado);
    }
}