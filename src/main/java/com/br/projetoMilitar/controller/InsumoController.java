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

import com.br.projetoMilitar.modeloBD.Insumos;
import com.br.projetoMilitar.services.InsumoService;

@RestController
@RequestMapping("/api/v1/insumo")
public class InsumoController {

    @Autowired
    private InsumoService service;

    @GetMapping
    public ResponseEntity<List<Insumos>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumos> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Insumos> findByNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.findByNome(nome));
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Insumos>> findByCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.findByCategoria(categoria));
    }

    @GetMapping("/alerta")
    public ResponseEntity<List<Insumos>> findInsumosEmAlerta() {
        return ResponseEntity.ok(service.findInsumosEmAlerta());
    }

    @GetMapping("/esgotados")
    public ResponseEntity<List<Insumos>> findInsumosEsgotados() {
        return ResponseEntity.ok(service.findInsumosEsgotados());
    }

    @GetMapping("/count/categoria")
    public ResponseEntity<Long> countByCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(service.countByCategoria(categoria));
    }

    @PostMapping
    public ResponseEntity<Insumos> create(@RequestBody Insumos insumo) {
        return new ResponseEntity<>(service.create(insumo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumos> update(@PathVariable Long id, @RequestBody Insumos insumo) {
        return ResponseEntity.ok(service.update(id, insumo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}