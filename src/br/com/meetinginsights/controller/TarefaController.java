package br.com.meetinginsights.controller;

import br.com.meetinginsights.domain.TarefaDTO;
import br.com.meetinginsights.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "http://localhost:8081")
public class TarefaController {

    private final TarefaRepository tarefaRepository;

    public TarefaController(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<TarefaDTO>> listarTarefasDoVendedor(@PathVariable Long idVendedor) {
        List<TarefaDTO> lista = tarefaRepository.listarPorVendedor(idVendedor);
        return ResponseEntity.ok(lista);
    }
}