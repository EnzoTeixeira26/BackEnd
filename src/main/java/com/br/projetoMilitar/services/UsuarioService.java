package com.br.projetoMilitar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetoMilitar.exceptions.ResourceNotFoundException;
import com.br.projetoMilitar.modeloBD.Usuarios;
import com.br.projetoMilitar.repositories.UsuariosRepository;
import com.br.projetoMilitar.security.GeradorSenha;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuariosRepository repository;

    @Autowired
    private EmailService emailService;

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
        
        String senhaTextoPlano = GeradorSenha.gerarSenhaAleatoria();
        logger.info("Senha gerada para o usuário: " + usuario.getIdentificador());
        
        usuario.setSenha(senhaTextoPlano);
        usuario.setPrimeiroAcesso(true);
        usuario.setDataExpiracaoSenha(LocalDateTime.now().plusDays(30));
        
        Usuarios novoUsuario = repository.save(usuario);
        
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            try {
                emailService.enviarBoasVindas(
                    usuario.getEmail(),
                    usuario.getNome(),
                    usuario.getIdentificador(),
                    senhaTextoPlano
                );
                logger.info("E-mail de boas-vindas enviado para: " + usuario.getEmail());
            } catch (Exception e) {
                logger.warning("Erro ao enviar e-mail de boas-vindas: " + e.getMessage());
            }
        } else {
            logger.warning("Usuário sem e-mail cadastrado. E-mail de boas-vindas não enviado.");
        }
        
        return novoUsuario;
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

    

    public Usuarios login(String identificador, String senha) {
        logger.info("Method login started - Identificador: " + identificador);
        
        Usuarios usuario = repository.findByIdentificador(identificador)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!usuario.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }

        //boolean isPrimeiroAcesso = Boolean.TRUE.equals(usuario.getPrimeiroAcesso());
        boolean isSenhaExpirada = usuario.getDataExpiracaoSenha() != null && 
                                  usuario.getDataExpiracaoSenha().isBefore(LocalDateTime.now());

        if (isSenhaExpirada) {
            throw new IllegalStateException("TROCA_SENHA_OBRIGATORIA");
        }

        return usuario;
    }

    public Usuarios trocarSenha(Long id, String novaSenha) {
        logger.info("Method trocarSenha started - Usuario id: " + id);
        
        Usuarios usuario = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setSenha(novaSenha); 
        usuario.setPrimeiroAcesso(false);
        usuario.setDataExpiracaoSenha(null);

        return repository.save(usuario);
    }
}