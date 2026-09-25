package com.br.projetoMilitar.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Equipamentos;
import com.br.projetoMilitar.repositories.EquipamentosRepository;

@Service
public class EquipamentoService {
    
    @Autowired
    private EquipamentosRepository repository;

    private Logger logger = Logger.getLogger(EquipamentoService.class.getName());

    public List<Equipamentos> findAll() {
        logger.info("Method findAll started - Equipamentos");
        return repository.findAll();
    }

    public Equipamentos findById(Long id) {
        logger.info("Method findById started - Equipamento id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Equipamento não encontrado para o id %s", id)));
    }

    public Equipamentos findByCodigo(String codigo) {
        logger.info("Method findByCodigo started - Código: " + codigo);
        return repository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Equipamento não encontrado para o código %s", codigo)));
    }

    public List<Equipamentos> findByTipoPelotao(String tipoPelotao) {
        logger.info("Method findByTipoPelotao started - Pelotão: " + tipoPelotao);
        return repository.findByTipoPelotao(tipoPelotao);
    }

    public List<Equipamentos> findByStatus(String status) {
        logger.info("Method findByStatus started - Status: " + status);
        return repository.findByStatus(status);
    }

    public List<Equipamentos> findByTipoPelotaoAndStatus(String tipoPelotao, String status) {
        logger.info("Method findByTipoPelotaoAndStatus started - Pelotão: " + tipoPelotao + ", Status: " + status);
        return repository.findByTipoPelotaoAndStatus(tipoPelotao, status);
    }

    public Equipamentos create(Equipamentos equipamento) {
        logger.info("Method create started - Equipamento: " + equipamento.getNome());
        
        if (repository.existsByCodigo(equipamento.getCodigo())) {
            throw new RuntimeException("Já existe um equipamento com o código: " + equipamento.getCodigo());
        }
        
        return repository.save(equipamento);
    }

    public Equipamentos update(Long id, Equipamentos equipamento) {
        logger.info("Method update started - Equipamento id: " + id);
        
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Equipamento não encontrado para o id %s", id)));
        
        equipamento.setId(id);
        return repository.save(equipamento);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Equipamento id: " + id);
        
        Equipamentos entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Equipamento não encontrado para o id %s", id)));
        
        repository.delete(entity);
    }

    public long countByTipoPelotao(String tipoPelotao) {
        logger.info("Method countByTipoPelotao started - Pelotão: " + tipoPelotao);
        return repository.countByTipoPelotao(tipoPelotao);
    }
}