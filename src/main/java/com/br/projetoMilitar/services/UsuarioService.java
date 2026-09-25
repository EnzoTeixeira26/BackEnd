package com.br.projetoMilitar.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Usuarios;
import com.br.projetoMilitar.repositories.UsuariosRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuariosRepository repository;

    private Logger logger = Logger.getLogger(UsuarioService.class.getName());

    public List<Usuarios> findAll() {
        logger.info("Method findAll started - Usuarios");
        return repository.findAll();
    }

    public Usuarios findById(Long id) {
        logger.info("Method findById started - Usuario id: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário não encontrado para o id %s", id)));
    }

    public Usuarios findByIdentificador(String identificador) {
        logger.info("Method findByIdentificador started - Identificador: " + identificador);
        return repository.findByIdentificador(identificador)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário não encontrado para o identificador %s", identificador)));
    }

    public Usuarios findByEmail(String email) {
        logger.info("Method findByEmail started - Email: " + email);
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário não encontrado para o email %s", email)));
    }

    public Usuarios create(Usuarios usuario) {
        logger.info("Method create started - Usuario: " + usuario.getNome());
        return repository.save(usuario);
    }

    public Usuarios update(Long id, Usuarios usuario) {
        logger.info("Method update started - Usuario id: " + id);
        
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário não encontrado para o id %s", id)));
        
        usuario.setId(id);
        return repository.save(usuario);
    }

    public void delete(Long id) {
        logger.info("Method delete started - Usuario id: " + id);
        
        Usuarios entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário não encontrado para o id %s", id)));
        
        repository.delete(entity);
    }

    public long countByPerfil(String perfil) {
        logger.info("Method countByPerfil started - Perfil: " + perfil);
        return repository.countByPerfil(perfil);
    }
}