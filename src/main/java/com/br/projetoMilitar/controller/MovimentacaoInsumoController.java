package com.br.projetoMilitar.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.br.projetoMilitar.modeloBD.MovimentacoesInsumos;
import com.br.projetoMilitar.services.MovimentacaoInsumoService;

@RestController
@RequestMapping("/api/v1/movimentacao")
public class MovimentacaoInsumoController {

    @Autowired
    private MovimentacaoInsumoService service;

    @GetMapping
    public ResponseEntity<List<MovimentacoesInsumos>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacoesInsumos> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<List<MovimentacoesInsumos>> findByInsumoId(@PathVariable Long insumoId) {
        return ResponseEntity.ok(service.findByInsumoId(insumoId));
    }

    @GetMapping("/insumo/{insumoId}/recentes")
    public ResponseEntity<List<MovimentacoesInsumos>> findMovimentacoesRecentesPorInsumo(
            @PathVariable Long insumoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio) {
        return ResponseEntity.ok(service.findMovimentacoesRecentesPorInsumo(insumoId, dataInicio));
    }

    @GetMapping("/insumo/{insumoId}/total/entradas")
    public ResponseEntity<Integer> sumEntradasByInsumoId(@PathVariable Long insumoId) {
        return ResponseEntity.ok(service.sumEntradasByInsumoId(insumoId));
    }

    @GetMapping("/insumo/{insumoId}/total/saidas")
    public ResponseEntity<Integer> sumSaidasByInsumoId(@PathVariable Long insumoId) {
        return ResponseEntity.ok(service.sumSaidasByInsumoId(insumoId));
    }

    @PostMapping
    public ResponseEntity<MovimentacoesInsumos> create(@RequestBody MovimentacoesInsumos movimentacao) {
        return new ResponseEntity<>(service.create(movimentacao), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}