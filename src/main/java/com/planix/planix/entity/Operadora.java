package com.planix.planix.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Operadora {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = true)
	private LocalDateTime createdAt;
	@JsonIgnore
	@OneToMany(mappedBy = "operadora")
	private List<Plano> planos;
	
	
	public Operadora() {
		
	}


	


	public Operadora(Long id, String nome, LocalDateTime createdAt, List<Plano> planos) {
		super();
		this.id = id;
		this.nome = nome;
		this.createdAt = createdAt;
		this.planos = planos;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}





	public List<Plano> getPlanos() {
		return planos;
	}





	public void setPlanos(List<Plano> planos) {
		this.planos = planos;
	}


	
	
	
	
	

}



