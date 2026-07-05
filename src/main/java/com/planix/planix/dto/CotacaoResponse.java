package com.planix.planix.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CotacaoResponse {
	
	private Long id;
	
	private String nomeCliente;
	
	private int idadeCliente;
	
	private LocalDateTime createdAt;
	
	private List<ResultadoResponse> resultadoCotacao;
	
	
	public CotacaoResponse() {
		
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


	public List<ResultadoResponse> getResultadoCotacao() {
		return resultadoCotacao;
	}


	public void setResultadoCotacao(List<ResultadoResponse> resultadoCotacao) {
		this.resultadoCotacao = resultadoCotacao;
	}
	
	

}



