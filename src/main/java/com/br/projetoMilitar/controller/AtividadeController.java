package com.br.projetoMilitar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetoMilitar.modeloBD.Atividades;
import com.br.projetoMilitar.services.AtividadeService;

@RestController
@RequestMapping("/api/v1/atividade")
public class AtividadeController {

    @Autowired
    private AtividadeService service;

    @GetMapping
    public ResponseEntity<List<Atividades>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividades> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Atividades>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Atividades>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.findByTipo(tipo));
    }

    @GetMapping("/usuario/{usuarioId}/tipo/{tipo}")
    public ResponseEntity<List<Atividades>> findUltimasAtividadesPorTipo(
            @PathVariable Long usuarioId,
            @PathVariable String tipo) {
        return ResponseEntity.ok(service.findUltimasAtividadesPorTipo(usuarioId, tipo));
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<Atividades>> findTop10Recentes() {
        return ResponseEntity.ok(service.findTop10Recentes());
    }

    @GetMapping("/count/tipo")
    public ResponseEntity<Long> countByTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(service.countByTipo(tipo));
    }

    @GetMapping("/count/usuario")
    public ResponseEntity<Long> countByUsuarioId(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(service.countByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Atividades> create(@RequestBody Atividades atividade) {
        return new ResponseEntity<>(service.create(atividade), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}