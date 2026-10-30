package com.br.projetoMilitar.security;

public class LoginRequest {
    private String identificador;
    private String senha;

    // Getters e Setters
    public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}