package com.br.projetoMilitar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.Insumos;

@Repository
public interface InsumosRepository extends JpaRepository<Insumos, Long> {
    
    Optional<Insumos> findByNome(String nome);
    
    List<Insumos> findByCategoria(String categoria);
    
    List<Insumos> findByLocalizacao(String localizacao);
    
    List<Insumos> findByQuantidadeLessThanEqual(int quantidadeMinima);
    
    @Query("SELECT i FROM Insumos i WHERE i.quantidade <= i.quantidadeMinima")
    List<Insumos> findInsumosEmAlerta();
    
    @Query("SELECT i FROM Insumos i WHERE i.quantidade = 0")
    List<Insumos> findInsumosEsgotados();
    
    long countByCategoria(String categoria);
    
    long countByQuantidadeLessThanEqual(int quantidadeMinima);
}