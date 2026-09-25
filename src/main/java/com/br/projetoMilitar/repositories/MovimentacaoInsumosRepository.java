package com.br.projetoMilitar.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.MovimentacoesInsumos;

@Repository
public interface MovimentacaoInsumosRepository extends JpaRepository<MovimentacoesInsumos, Long> {
    
    List<MovimentacoesInsumos> findByInsumoId(Long insumoId);
    
    List<MovimentacoesInsumos> findByTipo(String tipo);
    
    List<MovimentacoesInsumos> findByUsuarioId(Long usuarioId);
    
    List<MovimentacoesInsumos> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<MovimentacoesInsumos> findByInsumoIdOrderByDataDesc(Long insumoId);
    
    @Query("SELECT m FROM MovimentacoesInsumos m WHERE m.insumoId = :insumoId AND m.data >= :dataInicio")
    List<MovimentacoesInsumos> findMovimentacoesRecentesPorInsumo(
        @Param("insumoId") Long insumoId, 
        @Param("dataInicio") LocalDateTime dataInicio
    );
    
    @Query("SELECT SUM(m.quantidade) FROM MovimentacoesInsumos m WHERE m.insumoId = :insumoId AND m.tipo = 'ENTRADA'")
    Integer sumEntradasByInsumoId(@Param("insumoId") Long insumoId);
    
    @Query("SELECT SUM(m.quantidade) FROM MovimentacoesInsumos m WHERE m.insumoId = :insumoId AND m.tipo = 'SAIDA'")
    Integer sumSaidasByInsumoId(@Param("insumoId") Long insumoId);
}