package com.planix.planix.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Cotacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nomeCliente;
	@Column(nullable = false)
	private int idadeCliente;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private User user;

	@OneToMany(mappedBy = "cotacao")
	private List<ResultadoCotacao> resultadosCotacao;
	
	private int quantidadeVidas = 1;
	
	@Column(columnDefinition = "TEXT")
	private String dependentesJson;
	
	
	public Cotacao() {
		
	}


	public Cotacao(Long id, String nomeCliente, int idadeCliente, LocalDateTime createdAt, User user,
			List<ResultadoCotacao> resultadosCotacao) {
		super();
		this.id = id;
		this.nomeCliente = nomeCliente;
		this.idadeCliente = idadeCliente;
		this.createdAt = createdAt;
		this.user = user;
		this.resultadosCotacao = resultadosCotacao;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public int getIdadeCliente() {
		return idadeCliente;
	}


	public void setIdadeCliente(int idadeCliente) {
		this.idadeCliente = idadeCliente;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<ResultadoCotacao> getResultadosCotacao() {
		return resultadosCotacao;
	}


	public void setResultadosCotacao(List<ResultadoCotacao> resultadosCotacao) {
		this.resultadosCotacao = resultadosCotacao;
	}


	public int getQuantidadeVidas() {
		return quantidadeVidas;
	}


	public void setQuantidadeVidas(int quantidadeVidas) {
		this.quantidadeVidas = quantidadeVidas;
	}


	public String getDependentesJson() {
		return dependentesJson;
	}


	public void setDependentesJson(String dependentesJson) {
		this.dependentesJson = dependentesJson;
	}


	
	
	
	
	
	
}



