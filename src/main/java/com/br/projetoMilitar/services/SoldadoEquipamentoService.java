package com.br.projetoMilitar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Equipamentos;
import com.br.projetoMilitar.modeloBD.Soldados;
import com.br.projetoMilitar.modeloBD.SoldadosEquipamentos;
import com.br.projetoMilitar.repositories.EquipamentosRepository;
import com.br.projetoMilitar.repositories.SoldadosEquipamentosRepository;
import com.br.projetoMilitar.repositories.SoldadosRepository;

@Service
public class SoldadoEquipamentoService {

    @Autowired
    private SoldadosEquipamentosRepository repository;

    @Autowired
    private SoldadosRepository soldadosRepository;

    @Autowired
    private EquipamentosRepository equipamentosRepository;

    private Logger logger = Logger.getLogger(SoldadoEquipamentoService.class.getName());

    public List<SoldadosEquipamentos> findAll() {
        logger.info("Method findAll started - SoldadosEquipamentos");
        return repository.findAll();
    }

    public SoldadosEquipamentos findById(Long id) {
        logger.info("Method findById started - SoldadoEquipamento id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Atribuição não encontrada para o id %s", id)));
    }

    public List<SoldadosEquipamentos> findBySoldadoId(Long soldadoId) {
        logger.info("Method findBySoldadoId started - Soldado id: " + soldadoId);
        return repository.findBySoldadoId(soldadoId);
    }

    public List<SoldadosEquipamentos> findByEquipamentoId(Long equipamentoId) {
        logger.info("Method findByEquipamentoId started - Equipamento id: " + equipamentoId);
        return repository.findByEquipamentoId(equipamentoId);
    }

    /**
     * Cria ou atualiza uma atribuição.
     * Se o soldado já tiver o mesmo equipamento atribuído, soma a quantidade.
     * Caso contrário, cria uma nova linha.
     */
    public SoldadosEquipamentos create(SoldadosEquipamentos atribuicao) {
        logger.info("Method create started - SoldadoEquipamento");

        // 1) Valida se o soldado existe
        Soldados soldado = soldadosRepository.findById(atribuicao.getSoldadoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Soldado não encontrado para o id %s", atribuicao.getSoldadoId())));

        // 2) Valida se o equipamento existe
        Equipamentos equipamento = equipamentosRepository.findById(atribuicao.getEquipamentoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Equipamento não encontrado para o id %s", atribuicao.getEquipamentoId())));

        // 3) Define quantidade padrão se não veio
        if (atribuicao.getQuantidade() == null || atribuicao.getQuantidade() <= 0) {
            atribuicao.setQuantidade(1);
        }

        // 4) Upsert: se já existe atribuição desse equipamento pro soldado, soma a quantidade
        Optional<SoldadosEquipamentos> existente =
                repository.findBySoldadoIdAndEquipamentoId(
                        atribuicao.getSoldadoId(), atribuicao.getEquipamentoId());

        if (existente.isPresent()) {
            SoldadosEquipamentos reg = existente.get();
            reg.setQuantidade(reg.getQuantidade() + atribuicao.getQuantidade());

            // Se veio observação nova, atualiza. Se não, mantém a antiga.
            if (atribuicao.getObservacao() != null && !atribuicao.getObservacao().isBlank()) {
                reg.setObservacao(atribuicao.getObservacao());
            }
            // Atualiza o usuário que fez a última movimentação
            if (atribuicao.getUsuarioId() != null) {
                reg.setUsuarioId(atribuicao.getUsuarioId());
            }
            // Atualiza a data pra refletir a última modificação
            reg.setDataAtribuicao(LocalDateTime.now());

            logger.info(String.format(
                    "Atribuição já existia (soldado=%s, equipamento=%s). Quantidade somada para %d.",
                    soldado.getNome(), equipamento.getNome(), reg.getQuantidade()));

            return repository.save(reg);
        }

        // 5) Não existe: cria nova atribuição
        atribuicao.setDataAtribuicao(LocalDateTime.now());
        return repository.save(atribuicao);
    }

    /**
     * Remove uma atribuição específica pelo idSoldadoEquipamento.
     */
    public void delete(Long id) {
        logger.info("Method delete started - SoldadoEquipamento id: " + id);

        SoldadosEquipamentos entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Atribuição não encontrada para o id %s", id)));

        repository.delete(entity);
    }

    /**
     * Remove TODAS as atribuições de um soldado de uma vez.
     */
    @Transactional
    public void deleteAllBySoldadoId(Long soldadoId) {
        logger.info("Method deleteAllBySoldadoId started - Soldado id: " + soldadoId);

        // Valida se o soldado existe
        soldadosRepository.findById(soldadoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Soldado não encontrado para o id %s", soldadoId)));

        repository.deleteBySoldadoId(soldadoId);
    }

    public long countBySoldadoId(Long soldadoId) {
        return repository.countBySoldadoId(soldadoId);
    }

    public long countByEquipamentoId(Long equipamentoId) {
        return repository.countByEquipamentoId(equipamentoId);
    }
}