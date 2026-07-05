package com.planix.planix.dto;

import com.planix.planix.entity.Plano;

public class PlanoResponse {
	
	private Long id;
	
	private String nome;
	
	private Plano.Tipo tipo;
	
	private String regiao;
	
	private Plano.Acomodacao acomodacao;
	
	private Plano.Coparticipacao coparticipacao;
	
	private OperadoraResponse operadora;
	
	
	
	public PlanoResponse() {
		
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


	public Plano.Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Plano.Tipo tipo) {
		this.tipo = tipo;
	}


	public String getRegiao() {
		return regiao;
	}


	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}


	public Plano.Acomodacao getAcomodacao() {
		return acomodacao;
	}


	public void setAcomodacao(Plano.Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}



	public Plano.Coparticipacao getCoparticipacao() {
		return coparticipacao;
	}


	public void setCoparticipacao(Plano.Coparticipacao coparticipacao) {
		this.coparticipacao = coparticipacao;
	}


	public OperadoraResponse getOperadora() {
		return operadora;
	}


	public void setOperadora(OperadoraResponse operadora) {
		this.operadora = operadora;
	}

	
	

}



