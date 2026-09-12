package br.com.meetinginsights.controller;

import br.com.meetinginsights.domain.Cliente;
import br.com.meetinginsights.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:8081")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Retorna apenas os clientes de um vendedor específico
    @GetMapping("/vendedor/{idVendedor}")
    public ResponseEntity<List<Cliente>> listarPorVendedor(@PathVariable Long idVendedor) {
        List<Cliente> lista = clienteRepository.listarPorVendedor(idVendedor);
        return ResponseEntity.ok(lista);
    }

    // Retorna todos os clientes (usado para admins, se necessário)
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> lista = clienteRepository.listarTodos();
        return ResponseEntity.ok(lista);
    }

    // Cadastra o cliente já vinculado ao vendedor enviado no corpo da requisição
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Cliente cliente) {
        if (cliente.getRazaoSocial() == null || cliente.getRazaoSocial().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Razão social é obrigatória.");
        }
        if (cliente.getIdVendedor() == null) {
            return ResponseEntity.badRequest().body("ID do vendedor responsável é obrigatório.");
        }

        boolean sucesso = clienteRepository.cadastrar(cliente);
        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gravar cliente no Oracle.");
        }
    }
}