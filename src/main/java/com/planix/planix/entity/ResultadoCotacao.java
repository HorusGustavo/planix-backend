package com.planix.planix.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;



@Entity
public class ResultadoCotacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String faixaEtariaEncontrada;
	@Column(nullable = false)
	private BigDecimal valorFinal;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAcomodacao tipoAcomodacao;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cotacao_id",nullable = false)
	private Cotacao cotacao;
	@ManyToOne
	@JoinColumn(name = "plano_id",nullable = false)
	private Plano plano;
	
	private java.math.BigDecimal valorOriginal;
	
	@OneToMany(mappedBy = "resultadoCotacao", cascade = CascadeType.ALL, fetch = jakarta.persistence.FetchType.EAGER)
	private List<DetalheVida> detalhesVidas = new ArrayList<>();
	
	public ResultadoCotacao() {
		
	}
	
	public enum TipoAcomodacao{
		ENFERMARIA,
		APARTAMENTO
	}

	public ResultadoCotacao(Long id, String faixaEtariaEncontrada, BigDecimal valorFinal,
			TipoAcomodacao tipoAcomodacao,Cotacao cotacao,Plano plano) {
		super();
		this.id = id;
		this.faixaEtariaEncontrada = faixaEtariaEncontrada;
		this.valorFinal = valorFinal;
		this.tipoAcomodacao = tipoAcomodacao;
		this.cotacao = cotacao;
		this.plano = plano;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFaixaEtariaEncontrada() {
		return faixaEtariaEncontrada;
	}

	public void setFaixaEtariaEncontrada(String faixaEtariaEncontrada) {
		this.faixaEtariaEncontrada = faixaEtariaEncontrada;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public TipoAcomodacao getTipoAcomodacao() {
		return tipoAcomodacao;
	}

	public void setTipoAcomodacao(TipoAcomodacao tipoAcomodacao) {
		this.tipoAcomodacao = tipoAcomodacao;
	}

	public Cotacao getCotacao() {
		return cotacao;
	}

	public void setCotacao(Cotacao cotacao) {
		this.cotacao = cotacao;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public java.math.BigDecimal getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(java.math.BigDecimal valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public List<DetalheVida> getDetalhesVidas() {
		return detalhesVidas;
	}

	public void setDetalhesVidas(List<DetalheVida> detalhesVidas) {
		this.detalhesVidas = detalhesVidas;
	}
	
	
	
	
	
	
	

}




