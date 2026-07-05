package com.planix.planix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planix.planix.entity.User;
import com.planix.planix.entity.User.Role;
import com.planix.planix.repository.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public List<User>buscarTodos(){
		return userRepository.findAll();
	}
	
	public User buscarPorId(Long id){
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
	}
	
	public User buscarPorEmail(String email) {
	    return userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
	
	public User atualizar(Long id, String name, String email, String password, Role role) {
	    User atualizacao = buscarPorId(id);
	    atualizacao.setName(name);
	    atualizacao.setEmail(email);
	    atualizacao.setPassword(password);
	    atualizacao.setRole(role);
	    return userRepository.save(atualizacao);
	}
	
	public User ativarConta(Long id) {
	    User user = buscarPorId(id);
	    user.setAtivo(true);
	    return userRepository.save(user);
	}
	
	public User desativarConta(Long id) {
	    User user = buscarPorId(id);
	    user.setAtivo(false);
	    return userRepository.save(user);
	}
	
	public void deletar(Long id) {
		buscarPorId(id);
		userRepository.deleteById(id);
	}
	

}



