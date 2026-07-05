package com.planix.planix.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.entity.User;
import com.planix.planix.repository.UserRepository;
import com.planix.planix.service.EmailService;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetController(UserRepository userRepository,
                                    EmailService emailService,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<String> solicitarRedefinicao(@RequestBody java.util.Map<String, String> body) {
        String email = body.get("email");
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            // Não revela se o e-mail existe ou não, por segurança
            return ResponseEntity.ok("Se o e-mail existir, um link foi enviado.");
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiracao(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        emailService.enviarEmailRedefinicaoSenha(user.getEmail(), user.getName(), token);

        return ResponseEntity.ok("Se o e-mail existir, um link foi enviado.");
    }

    @PostMapping("/redefinir")
    public ResponseEntity<String> redefinirSenha(@RequestBody java.util.Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");

        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido ou expirado."));

        if (user.getResetTokenExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado. Solicite uma nova redefinição.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        user.setResetToken(null);
        user.setResetTokenExpiracao(null);
        userRepository.save(user);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
