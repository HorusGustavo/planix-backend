package com.planix.planix.service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String resendApiKey;

    private String remetente = "onboarding@resend.dev";

    public void enviarEmailBoasVindas(String destinatario, String nome) {
        try {
            Resend resend = new Resend(resendApiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(remetente)
                .to(destinatario)
                .subject("Bem-vindo ao Planix!")
                .html("<div style='font-family:Arial,sans-serif;max-width:500px;margin:0 auto;'>" +
                    "<h2 style='color:#1a56db;'>Planix</h2>" +
                    "<p>Olá <strong>" + nome + "</strong>,</p>" +
                    "<p>Seu cadastro no Planix foi recebido com sucesso!</p>" +
                    "<p>Em breve um administrador irá ativar sua conta e você poderá começar a gerar cotações.</p>" +
                    "<hr style='border:none;border-top:1px solid #e5e7eb;margin:20px 0;'/>" +
                    "<p style='color:#9ca3af;font-size:0.8rem;'>Equipe Planix</p>" +
                    "</div>")
                .build();
            resend.emails().send(params);
        } catch (ResendException e) {
            System.out.println("Erro ao enviar e-mail de boas-vindas: " + e.getMessage());
        }
    }

    public void enviarEmailRedefinicaoSenha(String destinatario, String nome, String token) {
        try {
            String link = "file:///Users/tuliomartins/Documents/front-planix/redefinir-senha.html?token=" + token;

            Resend resend = new Resend(resendApiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(remetente)
                .to(destinatario)
                .subject("Redefinição de senha — Planix")
                .html("<div style='font-family:Arial,sans-serif;max-width:500px;margin:0 auto;'>" +
                    "<h2 style='color:#1a56db;'>Planix</h2>" +
                    "<p>Olá <strong>" + nome + "</strong>,</p>" +
                    "<p>Você solicitou a redefinição da sua senha no Planix.</p>" +
                    "<p>Copie o link abaixo e cole na barra do seu navegador:</p>" +
                    "<div style='background:#f9fafb;border:1px solid #e5e7eb;border-radius:8px;padding:12px 16px;margin:16px 0;word-break:break-all;font-size:0.85rem;color:#374151;'>" +
                    link +
                    "</div>" +
                    "<p style='color:#6b7280;font-size:0.85rem;'>Se você não solicitou essa alteração, ignore este e-mail.</p>" +
                    "<p style='color:#6b7280;font-size:0.85rem;'>O link expira em 1 hora.</p>" +
                    "<hr style='border:none;border-top:1px solid #e5e7eb;margin:20px 0;'/>" +
                    "<p style='color:#9ca3af;font-size:0.8rem;'>Equipe Planix</p>" +
                    "</div>")
                .build();
            resend.emails().send(params);
        } catch (ResendException e) {
            System.out.println("Erro ao enviar e-mail de redefinição: " + e.getMessage());
        }
    }
}