package com.br.projetoMilitar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.Equipamentos;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {
    
    Optional<Equipamentos> findByCodigo(String codigo);
    
    List<Equipamentos> findByTipoPelotao(String tipoPelotao);
    
    List<Equipamentos> findByStatus(String status);
    
    List<Equipamentos> findByTipoPelotaoAndStatus(String tipoPelotao, String status);
    
    long countByTipoPelotao(String tipoPelotao);
    
    long countByStatus(String status);
    
    boolean existsByCodigo(String codigo);
}