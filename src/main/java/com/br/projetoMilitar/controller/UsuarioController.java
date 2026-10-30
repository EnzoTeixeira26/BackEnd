package com.br.projetoMilitar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetoMilitar.modeloBD.Usuarios;
import com.br.projetoMilitar.security.LoginRequest;
import com.br.projetoMilitar.services.UsuarioService;
import com.br.projetoMilitar.services.EmailService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/identificador/{identificador}")
    public ResponseEntity<Usuarios> findByIdentificador(@PathVariable String identificador) {
        return ResponseEntity.ok(service.findByIdentificador(identificador));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuarios> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @GetMapping("/count/perfil")
    public ResponseEntity<Long> countByPerfil(@RequestParam String perfil) {
        return ResponseEntity.ok(service.countByPerfil(perfil));
    }

    @PostMapping
    public ResponseEntity<Usuarios> create(@RequestBody Usuarios usuario) {
        return new ResponseEntity<>(service.create(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> update(@PathVariable Long id, @RequestBody Usuarios usuario) {
        return ResponseEntity.ok(service.update(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Usuarios usuario = service.login(request.getIdentificador(), request.getSenha());
            return ResponseEntity.ok(usuario);
        } catch (IllegalStateException e) {
            if ("TROCA_SENHA_OBRIGATORIA".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Troca de senha obrigatória");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/trocar-senha")
    public ResponseEntity<?> trocarSenha(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String novaSenha = body.get("novaSenha");
        if (novaSenha == null || novaSenha.length() < 6) {
            return ResponseEntity.badRequest().body("A nova senha deve ter pelo menos 6 caracteres.");
        }
        
        try {
            Usuarios usuario = service.trocarSenha(id, novaSenha);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teste-email")
    public ResponseEntity<String> testarEmail() {
        try {
            emailService.enviarBoasVindas(
                "enzo.santoste@gmail.com", 
                "Coronel Teste",
                "202600000000099",
                "wnprfxxpmoprtxqp",
                "Esta e uma observacao de teste."
            );
            return ResponseEntity.ok("E-mail enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
}