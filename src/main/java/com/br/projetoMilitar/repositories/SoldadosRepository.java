package com.br.projetoMilitar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.Soldados;

@Repository
public interface SoldadosRepository extends JpaRepository<Soldados, Long> {
    
    Optional<Soldados> findByIdentificador(String identificador);
    
    List<Soldados> findByPelotao(String pelotao);
    
    List<Soldados> findByStatus(String status);
    
    List<Soldados> findByPelotaoAndStatus(String pelotao, String status);
    
    long countByPelotao(String pelotao);
    
    long countByStatus(String status);
    
    long countByPelotaoAndStatus(String pelotao, String status);
}