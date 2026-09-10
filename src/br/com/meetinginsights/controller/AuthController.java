package br.com.meetinginsights.controller;

import br.com.meetinginsights.domain.Vendedor;
import br.com.meetinginsights.repository.VendedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    private final VendedorRepository vendedorRepository;

    public AuthController(VendedorRepository vendedorRepository) {
        this.vendedorRepository = vendedorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        Vendedor vendedor = vendedorRepository.autenticar(email, senha);
        if (vendedor != null) {
            return ResponseEntity.ok(vendedor);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("mensagem", "E-mail ou palavra-passe incorretos."));
    }

    @GetMapping("/vendedores")
    public ResponseEntity<List<Vendedor>> listarVendedores() {
        return ResponseEntity.ok(vendedorRepository.listarTodos());
    }

    @PostMapping("/vendedores")
    public ResponseEntity<?> criarVendedor(@RequestBody Vendedor novo) {
        boolean sucesso = vendedorRepository.cadastrar(novo);
        if (sucesso) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Vendedor cadastrado com sucesso."));
        }
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Não foi possível cadastrar o vendedor."));
    }

    @PutMapping("/alterar-senha/{idVendedor}")
    public ResponseEntity<?> alterarSenha(@PathVariable Long idVendedor, @RequestBody Map<String, String> payload) {
        String novaSenha = payload.get("novaSenha");
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "A nova palavra-passe é obrigatória."));
        }

        boolean atualizou = vendedorRepository.atualizarSenha(idVendedor, novaSenha);
        if (atualizou) {
            return ResponseEntity.ok(Map.of("mensagem", "Palavra-passe atualizada com sucesso!"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensagem", "Erro ao atualizar."));
    }
}