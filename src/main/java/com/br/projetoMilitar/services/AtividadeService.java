package com.br.projetoMilitar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Atividades;
import com.br.projetoMilitar.repositories.AtividadesRepository;

@Service
public class AtividadeService {
    
    @Autowired
    private AtividadesRepository repository;

    private Logger logger = Logger.getLogger(AtividadeService.class.getName());

    public List<Atividades> findAll() {
        logger.info("Method findAll started - Atividades");
        return repository.findAll();
    }

    public Atividades findById(Long id) {
        logger.info("Method findById started - Atividade id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Atividade não encontrada para o id %s", id)));
    }

    public List<Atividades> findByUsuarioId(Long usuarioId) {
        logger.info("Method findByUsuarioId started - Usuario id: " + usuarioId);
        return repository.findByUsuarioId(usuarioId);
    }

    public List<Atividades> findByTipo(String tipo) {
        logger.info("Method findByTipo started - Tipo: " + tipo);
        return repository.findByTipo(tipo);
    }

    public List<Atividades> findUltimasAtividadesPorTipo(Long usuarioId, String tipo) {
        logger.info("Method findUltimasAtividadesPorTipo started");
        return repository.findUltimasAtividadesPorTipo(usuarioId, tipo);
    }

    public List<Atividades> findTop10Recentes() {
        logger.info("Method findTop10Recentes started");
        return repository.findTop10ByOrderByDataDesc();
    }

    public Atividades create(Atividades atividade) {
        logger.info("Method create started - Atividade");
        atividade.setData(LocalDateTime.now());
        return repository.save(atividade);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Atividade id: " + id);
        
        Atividades entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Atividade não encontrada para o id %s", id)));
        
        repository.delete(entity);
    }

    public long countByTipo(String tipo) {
        logger.info("Method countByTipo started - Tipo: " + tipo);
        return repository.countByTipo(tipo);
    }

    public long countByUsuarioId(Long usuarioId) {
        logger.info("Method countByUsuarioId started - Usuario id: " + usuarioId);
        return repository.countByUsuarioId(usuarioId);
    }
}