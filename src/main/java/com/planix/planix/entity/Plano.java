package com.planix.planix.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Plano {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tipo tipo;
	@Column(nullable = false)
	private String regiao;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Acomodacao acomodacao;
	private Coparticipacao coparticipacao;
	@Column(nullable = false)
	private String imagePath;
	@Column(nullable = true)
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "operadora_id",nullable = false)
	private Operadora operadora;
	@JsonIgnore
	@OneToMany(mappedBy = "plano")
	private List<FaixaEtaria> faixasEtarias;
	@ManyToMany
	@JoinTable(name = "plano_hospital", joinColumns = @JoinColumn(name = "plano_id"), inverseJoinColumns = @JoinColumn(name = "hospital_id"))
	@JsonIgnore
	private List<Hospital> hospitais = new ArrayList<>();
	
	
	public Plano() {
		
	}
	
	public enum Coparticipacao{
		SEM_COPARTICIPACAO,
		PARCIAL,
		TOTAL
	}
	
	public enum Tipo{
		INDIVIDUAL,
		ADESAO
		
	}
	
	public enum Acomodacao{
		ENFERMARIA,
		APARTAMENTO
	}

	

	public Plano(Long id, String nome, Tipo tipo, String regiao, Acomodacao acomodacao, Coparticipacao coparticipacao,
			String imagePath, LocalDateTime createdAt, Operadora operadora,
			List<FaixaEtaria> faixasEtarias) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.regiao = regiao;
		this.acomodacao = acomodacao;
		this.coparticipacao = coparticipacao;
		this.imagePath = imagePath;
		this.createdAt = createdAt;
		this.operadora = operadora;
		this.faixasEtarias = faixasEtarias;
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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public Acomodacao getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}

	public Coparticipacao getCoparticipacao() {
		return coparticipacao;
	}

	public void setCoparticipacao(Coparticipacao coparticipacao) {
		this.coparticipacao = coparticipacao;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Operadora getOperadora() {
		return operadora;
	}

	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	public List<FaixaEtaria> getFaixasEtarias() {
		return faixasEtarias;
	}

	public void setFaixasEtarias(List<FaixaEtaria> faixasEtarias) {
		this.faixasEtarias = faixasEtarias;
	}

	public List<Hospital> getHospitais() {
		return hospitais;
	}

	public void setHospitais(List<Hospital> hospitais) {
		this.hospitais = hospitais;
	}
	
	
	
	
	

}


