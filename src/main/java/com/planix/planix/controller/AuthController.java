package com.planix.planix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.dto.LoginRequest;
import com.planix.planix.dto.LoginResponse;
import com.planix.planix.dto.RegisterRequest;
import com.planix.planix.entity.User;
import com.planix.planix.service.AuthService;
import com.planix.planix.service.EmailService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @Autowired
    public AuthController(AuthService authService, EmailService emailService) {
        this.authService = authService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User user = authService.registrar(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole());

        try {
            emailService.enviarEmailBoasVindas(user.getEmail(), user.getName());
        } catch (Exception e) {
            // Não impede o cadastro caso o e-mail falhe
            System.out.println("Erro ao enviar e-mail de boas-vindas: " + e.getMessage());
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}