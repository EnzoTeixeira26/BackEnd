package com.br.projetoMilitar.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.Atividades;

@Repository
public interface AtividadesRepository extends JpaRepository<Atividades, Long> {
    
    List<Atividades> findByUsuarioId(Long usuarioId);
    
    List<Atividades> findByTipo(String tipo);
    
    List<Atividades> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<Atividades> findByUsuarioIdOrderByDataDesc(Long usuarioId);
    
    List<Atividades> findTop10ByOrderByDataDesc();
    
    @Query("SELECT a FROM Atividades a WHERE a.usuarioId = :usuarioId AND a.tipo = :tipo ORDER BY a.data DESC")
    List<Atividades> findUltimasAtividadesPorTipo(
        @Param("usuarioId") Long usuarioId, 
        @Param("tipo") String tipo
    );
    
    @Query("SELECT a FROM Atividades a WHERE a.data >= :dataInicio ORDER BY a.data DESC")
    List<Atividades> findAtividadesRecentes(@Param("dataInicio") LocalDateTime dataInicio);
    
    long countByTipo(String tipo);
    
    long countByUsuarioId(Long usuarioId);
}