package com.planix.planix.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";
    private final String remetente = "planixsistema@gmail.com";
    private final String remetenteNome = "Planix";

    private void enviar(String destinatario, String nomeDestinatario, String assunto, String html) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", brevoApiKey);

            Map<String, Object> body = new HashMap<>();
            body.put("sender", Map.of("name", remetenteNome, "email", remetente));
            body.put("to", List.of(Map.of("email", destinatario, "name", nomeDestinatario)));
            body.put("subject", assunto);
            body.put("htmlContent", html);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(BREVO_URL, entity, String.class);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    public void enviarEmailBoasVindas(String destinatario, String nome) {
        String html = "<div style='font-family:Arial,sans-serif;max-width:500px;margin:0 auto;'>" +
            "<h2 style='color:#1a56db;'>Planix</h2>" +
            "<p>Olá <strong>" + nome + "</strong>,</p>" +
            "<p>Seu cadastro no Planix foi recebido com sucesso!</p>" +
            "<p>Em breve um administrador irá ativar sua conta e você poderá começar a gerar cotações.</p>" +
            "<hr style='border:none;border-top:1px solid #e5e7eb;margin:20px 0;'/>" +
            "<p style='color:#9ca3af;font-size:0.8rem;'>Equipe Planix</p>" +
            "</div>";
        enviar(destinatario, nome, "Bem-vindo ao Planix!", html);
    }

    public void enviarEmailRedefinicaoSenha(String destinatario, String nome, String token) {
    	String link = "https://planix-frontend-beryl.vercel.app/redefinir-senha.html?token=" + token;
        String html = "<div style='font-family:Arial,sans-serif;max-width:500px;margin:0 auto;'>" +
            "<h2 style='color:#1a56db;'>Planix</h2>" +
            "<p>Olá <strong>" + nome + "</strong>,</p>" +
            "<p>Você solicitou a redefinição da sua senha no Planix.</p>" +
            "<p>Copie o link abaixo e cole na barra do seu navegador:</p>" +
            "<div style='background:#f9fafb;border:1px solid #e5e7eb;border-radius:8px;padding:12px 16px;margin:16px 0;word-break:break-all;font-size:0.85rem;color:#374151;'>" +
            link +
            "</div>" +
            "<p style='color:#6b7280;font-size:0.85rem;'>O link expira em 1 hora.</p>" +
            "<hr style='border:none;border-top:1px solid #e5e7eb;margin:20px 0;'/>" +
            "<p style='color:#9ca3af;font-size:0.8rem;'>Equipe Planix</p>" +
            "</div>";
        enviar(destinatario, nome, "Redefinição de senha — Planix", html);
    }
}