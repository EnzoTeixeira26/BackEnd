package com.br.projetoMilitar.security;

import java.security.SecureRandom;   

public class GeradorSenha {

    private static final String CARACTERES = 
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String gerarSenhaAleatoria() {
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(10);
        
        for (int i = 0; i < 10; i++) {
            senha.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        
        return senha.toString();
    }
}