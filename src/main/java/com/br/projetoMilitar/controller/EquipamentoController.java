package com.br.projetoMilitar.controller;

import java.util.List;

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

import com.br.projetoMilitar.modeloBD.Equipamentos;
import com.br.projetoMilitar.services.EquipamentoService;

@RestController
@RequestMapping("/api/v1/equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @GetMapping
    public ResponseEntity<List<Equipamentos>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamentos> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Equipamentos> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(service.findByCodigo(codigo));
    }

    @GetMapping("/pelotao/{tipoPelotao}")
    public ResponseEntity<List<Equipamentos>> findByTipoPelotao(@PathVariable String tipoPelotao) {
        return ResponseEntity.ok(service.findByTipoPelotao(tipoPelotao));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Equipamentos>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/pelotao/{tipoPelotao}/status/{status}")
    public ResponseEntity<List<Equipamentos>> findByTipoPelotaoAndStatus(
            @PathVariable String tipoPelotao, 
            @PathVariable String status) {
        return ResponseEntity.ok(service.findByTipoPelotaoAndStatus(tipoPelotao, status));
    }

    @GetMapping("/count/pelotao")
    public ResponseEntity<Long> countByTipoPelotao(@RequestParam String tipoPelotao) {
        return ResponseEntity.ok(service.countByTipoPelotao(tipoPelotao));
    }

    @PostMapping
    public ResponseEntity<Equipamentos> create(@RequestBody Equipamentos equipamento) {
        return new ResponseEntity<>(service.create(equipamento), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> update(@PathVariable Long id, @RequestBody Equipamentos equipamento) {
        return ResponseEntity.ok(service.update(id, equipamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}