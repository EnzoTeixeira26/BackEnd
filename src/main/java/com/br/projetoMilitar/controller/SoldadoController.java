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

import com.br.projetoMilitar.modeloBD.Soldados;
import com.br.projetoMilitar.services.SoldadoService;

@RestController
@RequestMapping("/api/v1/soldado")
public class SoldadoController {

    @Autowired
    private SoldadoService service;

    @GetMapping
    public ResponseEntity<List<Soldados>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soldados> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/identificador/{identificador}")
    public ResponseEntity<Soldados> findByIdentificador(@PathVariable String identificador) {
        return ResponseEntity.ok(service.findByIdentificador(identificador));
    }

    @GetMapping("/pelotao/{pelotao}")
    public ResponseEntity<List<Soldados>> findByPelotao(@PathVariable String pelotao) {
        return ResponseEntity.ok(service.findByPelotao(pelotao));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Soldados>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @GetMapping("/pelotao/{pelotao}/status/{status}")
    public ResponseEntity<List<Soldados>> findByPelotaoAndStatus(
            @PathVariable String pelotao, 
            @PathVariable String status) {
        return ResponseEntity.ok(service.findByPelotaoAndStatus(pelotao, status));
    }

    @GetMapping("/count/pelotao")
    public ResponseEntity<Long> countByPelotao(@RequestParam String pelotao) {
        return ResponseEntity.ok(service.countByPelotao(pelotao));
    }

    @GetMapping("/count/status")
    public ResponseEntity<Long> countByStatus(@RequestParam String status) {
        return ResponseEntity.ok(service.countByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Soldados> create(@RequestBody Soldados soldado) {
        return new ResponseEntity<>(service.create(soldado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Soldados> update(@PathVariable Long id, @RequestBody Soldados soldado) {
        return ResponseEntity.ok(service.update(id, soldado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}