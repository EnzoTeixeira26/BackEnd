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
import org.springframework.web.bind.annotation.RestController;

import com.br.projetoMilitar.modeloBD.SoldadosEquipamentos;
import com.br.projetoMilitar.services.SoldadoEquipamentoService;

@RestController
@RequestMapping("/api/v1/soldado-equipamento")
public class SoldadoEquipamentoController {

    @Autowired
    private SoldadoEquipamentoService service;

    @GetMapping
    public ResponseEntity<List<SoldadosEquipamentos>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoldadosEquipamentos> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/soldado/{soldadoId}")
    public ResponseEntity<List<SoldadosEquipamentos>> findBySoldadoId(@PathVariable Long soldadoId) {
        return ResponseEntity.ok(service.findBySoldadoId(soldadoId));
    }

    @GetMapping("/equipamento/{equipamentoId}")
    public ResponseEntity<List<SoldadosEquipamentos>> findByEquipamentoId(@PathVariable Long equipamentoId) {
        return ResponseEntity.ok(service.findByEquipamentoId(equipamentoId));
    }

    @PostMapping
    public ResponseEntity<SoldadosEquipamentos> create(@RequestBody SoldadosEquipamentos atribuicao) {
        return new ResponseEntity<>(service.create(atribuicao), HttpStatus.CREATED);
    }

    /**
     * Remove UMA atribuição específica pelo idSoldadoEquipamento.
     * Ex: DELETE /api/v1/soldado-equipamento/5
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove TODAS as atribuições de um soldado de uma vez.
     * Ex: DELETE /api/v1/soldado-equipamento/soldado/3/todos
     *
     * IMPORTANTE: essa rota precisa vir ANTES da rota /{id} no mapeamento,
     * ou o Spring vai tentar interpretar "soldado" como um {id}.
     * Como o Spring resolve por especificidade (mais segmentos = mais específico),
     * a rota com /soldado/{soldadoId}/todos tem precedência sobre /{id}.
     * Ainda assim, deixamos declarada logo abaixo de /{id} por organização.
     */
    @DeleteMapping("/soldado/{soldadoId}/todos")
    public ResponseEntity<Void> deleteAllBySoldadoId(@PathVariable Long soldadoId) {
        service.deleteAllBySoldadoId(soldadoId);
        return ResponseEntity.noContent().build();
    }
}