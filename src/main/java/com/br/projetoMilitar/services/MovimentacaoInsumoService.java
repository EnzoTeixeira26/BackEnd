package com.br.projetoMilitar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Insumos;
import com.br.projetoMilitar.modeloBD.MovimentacoesInsumos;
import com.br.projetoMilitar.repositories.InsumosRepository;
import com.br.projetoMilitar.repositories.MovimentacaoInsumosRepository;

@Service
public class MovimentacaoInsumoService {
    
    @Autowired
    private MovimentacaoInsumosRepository repository;
    
    @Autowired
    private InsumosRepository insumoRepository;

    private Logger logger = Logger.getLogger(MovimentacaoInsumoService.class.getName());

    public List<MovimentacoesInsumos> findAll() {
        logger.info("Method findAll started - MovimentacoesInsumos");
        return repository.findAll();
    }

    public MovimentacoesInsumos findById(Long id) {
        logger.info("Method findById started - Movimentacao id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Movimentação não encontrada para o id %s", id)));
    }

    public List<MovimentacoesInsumos> findByInsumoId(Long insumoId) {
        logger.info("Method findByInsumoId started - Insumo id: " + insumoId);
        return repository.findByInsumoId(insumoId);
    }

    public List<MovimentacoesInsumos> findMovimentacoesRecentesPorInsumo(Long insumoId, LocalDateTime dataInicio) {
        logger.info("Method findMovimentacoesRecentesPorInsumo started");
        return repository.findMovimentacoesRecentesPorInsumo(insumoId, dataInicio);
    }

    public MovimentacoesInsumos create(MovimentacoesInsumos movimentacao) {
        logger.info("Method create started - Movimentacao");
        
        Insumos insumo = insumoRepository.findById(movimentacao.getInsumoId())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo não encontrado"));
        
        // Atualizar quantidade do insumo
        if (movimentacao.getTipo().equals("ENTRADA")) {
            insumo.setQuantidade(insumo.getQuantidade() + movimentacao.getQuantidade());
        } else if (movimentacao.getTipo().equals("SAIDA")) {
            if (insumo.getQuantidade() < movimentacao.getQuantidade()) {
                throw new RuntimeException("Quantidade insuficiente em estoque");
            }
            insumo.setQuantidade(insumo.getQuantidade() - movimentacao.getQuantidade());
        }
        
        insumoRepository.save(insumo);
        movimentacao.setData(LocalDateTime.now());
        
        return repository.save(movimentacao);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Movimentacao id: " + id);
        
        MovimentacoesInsumos entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Movimentação não encontrada para o id %s", id)));
        
        repository.delete(entity);
    }

    public Integer sumEntradasByInsumoId(Long insumoId) {
        logger.info("Method sumEntradasByInsumoId started");
        Integer sum = repository.sumEntradasByInsumoId(insumoId);
        return sum != null ? sum : 0;
    }

    public Integer sumSaidasByInsumoId(Long insumoId) {
        logger.info("Method sumSaidasByInsumoId started");
        Integer sum = repository.sumSaidasByInsumoId(insumoId);
        return sum != null ? sum : 0;
    }
}