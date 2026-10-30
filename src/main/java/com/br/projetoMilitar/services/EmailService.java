package com.br.projetoMilitar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public void enviarBoasVindas(String destino, String nome, String identificador, String senha, String observacao) throws Exception {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

        helper.setFrom(remetente);
        helper.setTo(destino);
        helper.setSubject("Bem-vindo ao SGM-017 - Suas credenciais de acesso");

        String blocoObservacao = "";
        if (observacao != null && !observacao.trim().isEmpty()) {
            blocoObservacao = String.format("""
                <div style="background-color: #fff3cd; padding: 15px; border-left: 4px solid #ffc107; margin: 20px 0;">
                    <p style="margin: 0;"><strong>Observacao do Administrador:</strong></p>
                    <p style="margin: 5px 0 0 0;">%s</p>
                </div>
                """, observacao);
        }

        String html = """
            <html>
            <body style="font-family: Arial, sans-serif; color: #333; margin: 40px;">
                <div style="text-align: center; border-bottom: 2px solid #FFD700; padding-bottom: 20px; margin-bottom: 30px;">
                    <h1 style="color: #1a3a1a; margin: 0;">SGM-017</h1>
                    <p style="color: #666; margin-top: 5px;">Sistema de Gestao Militar Integrada</p>
                </div>
                
                <h2>Ola, %s!</h2>
                
                <p>Seu cadastro no SGM-017 foi realizado com sucesso.</p>
                
                <div style="background-color: #f5f5f5; padding: 20px; border-radius: 8px; margin: 20px 0;">
                    <p style="margin: 0 0 10px 0;"><strong>Identificador:</strong> %s</p>
                    <p style="margin: 0;"><strong>Senha temporaria:</strong> %s</p>
                </div>

                %s
                
                <p style="color: #d32f2f;"><strong>IMPORTANTE:</strong></p>
                <ul>
                    <li>Esta senha e temporaria e expira em <strong>30 dias</strong>.</li>
                    <li>Voce devera troca-la no primeiro acesso.</li>
                    <li>Guarde suas credenciais em local seguro.</li>
                </ul>
                
                <div style="text-align: center; margin-top: 40px; padding-top: 20px; border-top: 1px solid #ddd; font-size: 11px; color: #999;">
                    SGM-017 2026 - Exercito Brasileiro<br>
                    Este e um e-mail automatico, nao responda.
                </div>
            </body>
            </html>
            """.formatted(nome, identificador, senha, blocoObservacao);

        helper.setText(html, true);
        mailSender.send(mensagem);
    }
}