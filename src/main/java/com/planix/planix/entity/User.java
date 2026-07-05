package com.planix.planix.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, unique = true)
	private String email;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(nullable = true)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private boolean ativo;
	
	private String resetToken;
	private LocalDateTime resetTokenExpiracao;

	
	
	public User() {

}

	public enum Role{
		ADMIN,
		CORRETOR
	}

	public User(Long id, String name, String password,String email, Role role, LocalDateTime createdAt, boolean ativo) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
		this.createdAt = createdAt;
		this.ativo = ativo;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getResetTokenExpiracao() {
		return resetTokenExpiracao;
	}

	public void setResetTokenExpiracao(LocalDateTime resetTokenExpiracao) {
		this.resetTokenExpiracao = resetTokenExpiracao;
	}
	
	
	


	
	
	
	

}




