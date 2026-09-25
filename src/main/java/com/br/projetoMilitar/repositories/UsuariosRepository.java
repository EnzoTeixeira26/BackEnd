package com.br.projetoMilitar.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.projetoMilitar.modeloBD.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    
    Optional<Usuarios> findByIdentificador(String identificador);
    
    Optional<Usuarios> findByEmail(String email);
    
    Optional<Usuarios> findByCpf(String cpf);
    
    boolean existsByIdentificador(String identificador);
    
    boolean existsByEmail(String email);
    
    boolean existsByCpf(String cpf);
    
    long countByPerfil(String perfil);
}