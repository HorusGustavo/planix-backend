package com.planix.planix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.entity.User;
import com.planix.planix.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> buscarTodos(){
		return ResponseEntity.ok(userService.buscarTodos());
	}
	@GetMapping("/{id}")
	public ResponseEntity<User> buscarPorId(@PathVariable Long id){
		return ResponseEntity.ok(userService.buscarPorId(id));
	}
	@PutMapping("/{id}")
	public ResponseEntity<User>atualizar(@PathVariable Long id,@RequestParam  String name,@RequestParam  String email,@RequestParam  String password,@RequestParam  User.Role role ){
		return ResponseEntity.ok(userService.atualizar(id,name , password, email, role));
	}
	@PatchMapping("/{id}/ativar")
	public ResponseEntity<User> ativarConta(@PathVariable Long id) {
	    return ResponseEntity.ok(userService.ativarConta(id));
	}
	
	@PatchMapping("/{id}/desativar")
	public ResponseEntity<User> desativarConta(@PathVariable Long id) {
	    return ResponseEntity.ok(userService.desativarConta(id));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		userService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/me")
	public ResponseEntity<User> buscarUsuarioLogado() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String email = auth.getName();
	    return ResponseEntity.ok(userService.buscarPorEmail(email));
	}

}


