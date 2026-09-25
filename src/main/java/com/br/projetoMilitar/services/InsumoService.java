package com.br.projetoMilitar.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Insumos;
import com.br.projetoMilitar.repositories.InsumosRepository;

@Service
public class InsumoService {
    
    @Autowired
    private InsumosRepository repository;

    private Logger logger = Logger.getLogger(InsumoService.class.getName());

    public List<Insumos> findAll() {
        logger.info("Method findAll started - Insumos");
        return repository.findAll();
    }

    public Insumos findById(Long id) {
        logger.info("Method findById started - Insumo id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Insumo não encontrado para o id %s", id)));
    }

    public Insumos findByNome(String nome) {
        logger.info("Method findByNome started - Nome: " + nome);
        return repository.findByNome(nome)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Insumo não encontrado para o nome %s", nome)));
    }

    public List<Insumos> findByCategoria(String categoria) {
        logger.info("Method findByCategoria started - Categoria: " + categoria);
        return repository.findByCategoria(categoria);
    }

    public List<Insumos> findInsumosEmAlerta() {
        logger.info("Method findInsumosEmAlerta started");
        return repository.findInsumosEmAlerta();
    }

    public List<Insumos> findInsumosEsgotados() {
        logger.info("Method findInsumosEsgotados started");
        return repository.findInsumosEsgotados();
    }

    public Insumos create(Insumos insumo) {
        logger.info("Method create started - Insumo: " + insumo.getNome());
        return repository.save(insumo);
    }

    public Insumos update(Long id, Insumos insumo) {
        logger.info("Method update started - Insumo id: " + id);
        
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Insumo não encontrado para o id %s", id)));
        
        insumo.setId(id);
        return repository.save(insumo);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Insumo id: " + id);
        
        Insumos entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Insumo não encontrado para o id %s", id)));
        
        repository.delete(entity);
    }

    public long countByCategoria(String categoria) {
        logger.info("Method countByCategoria started - Categoria: " + categoria);
        return repository.countByCategoria(categoria);
    }
}