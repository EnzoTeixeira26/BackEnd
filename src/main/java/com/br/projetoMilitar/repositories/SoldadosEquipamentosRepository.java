package com.br.projetoMilitar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.SoldadosEquipamentos;

@Repository
public interface SoldadosEquipamentosRepository extends JpaRepository<SoldadosEquipamentos, Long> {

    List<SoldadosEquipamentos> findBySoldadoId(Long soldadoId);

    List<SoldadosEquipamentos> findByEquipamentoId(Long equipamentoId);

    // Agora precisamos do objeto, não só do boolean, pra somar a quantidade
    Optional<SoldadosEquipamentos> findBySoldadoIdAndEquipamentoId(Long soldadoId, Long equipamentoId);

    boolean existsBySoldadoIdAndEquipamentoId(Long soldadoId, Long equipamentoId);

    long countBySoldadoId(Long soldadoId);

    long countByEquipamentoId(Long equipamentoId);

    // Novo: deletar todos os equipamentos de um soldado de uma vez
    @Modifying
    @Query("DELETE FROM SoldadosEquipamentos se WHERE se.soldadoId = :soldadoId")
    void deleteBySoldadoId(@Param("soldadoId") Long soldadoId);
}