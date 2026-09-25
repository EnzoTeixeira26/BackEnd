package com.br.projetoMilitar.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Soldados;
import com.br.projetoMilitar.repositories.SoldadosRepository;

@Service
public class SoldadoService {
    
    @Autowired
    private SoldadosRepository repository;

    private Logger logger = Logger.getLogger(SoldadoService.class.getName());

    public List<Soldados> findAll() {
        logger.info("Method findAll started - Soldados");
        return repository.findAll();
    }

    public Soldados findById(Long id) {
        logger.info("Method findById started - Soldado id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Soldado não encontrado para o id %s", id)));
    }

    public Soldados findByIdentificador(String identificador) {
        logger.info("Method findByIdentificador started - Identificador: " + identificador);
        return repository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Soldado não encontrado para o identificador %s", identificador)));
    }

    public List<Soldados> findByPelotao(String pelotao) {
        logger.info("Method findByPelotao started - Pelotão: " + pelotao);
        return repository.findByPelotao(pelotao);
    }

    public List<Soldados> findByStatus(String status) {
        logger.info("Method findByStatus started - Status: " + status);
        return repository.findByStatus(status);
    }

    public List<Soldados> findByPelotaoAndStatus(String pelotao, String status) {
        logger.info("Method findByPelotaoAndStatus started - Pelotão: " + pelotao + ", Status: " + status);
        return repository.findByPelotaoAndStatus(pelotao, status);
    }

    public Soldados create(Soldados soldado) {
        logger.info("Method create started - Soldado: " + soldado.getNome());
        return repository.save(soldado);
    }

    public Soldados update(Long id, Soldados soldado) {
        logger.info("Method update started - Soldado id: " + id);
        
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Soldado não encontrado para o id %s", id)));
        
        soldado.setId(id);
        return repository.save(soldado);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Soldado id: " + id);
        
        Soldados entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Soldado não encontrado para o id %s", id)));
        
        repository.delete(entity);
    }

    public long countByPelotao(String pelotao) {
        logger.info("Method countByPelotao started - Pelotão: " + pelotao);
        return repository.countByPelotao(pelotao);
    }

    public long countByStatus(String status) {
        logger.info("Method countByStatus started - Status: " + status);
        return repository.countByStatus(status);
    }
}