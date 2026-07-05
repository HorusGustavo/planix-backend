package com.planix.planix.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.planix.planix.entity.User;
import com.planix.planix.entity.User.Role;
import com.planix.planix.repository.UserRepository;
import com.planix.planix.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registrar(String name, String email, String password, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        User novoUser = new User();
        novoUser.setName(name);
        novoUser.setEmail(email);
        novoUser.setPassword(passwordEncoder.encode(password));
        novoUser.setRole(role);
        novoUser.setAtivo(false);
        return userRepository.save(novoUser);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getAtivo()) {
            throw new RuntimeException("Conta aguardando ativação");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return jwtUtil.gerarToken(user.getEmail());
    }
}
