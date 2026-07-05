package com.planix.planix.dto;

import com.planix.planix.entity.ResultadoCotacao.TipoAcomodacao;
import java.util.ArrayList;
import java.util.List;

public class CotacaoRequest {

    private String nomeCliente;
    private int idadeCliente;
    private TipoAcomodacao tipoAcomodacao;
    private int quantidadeVidas = 1;
    private List<DependenteRequest> dependentes = new ArrayList<>();

    public CotacaoRequest() {}

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public int getIdadeCliente() { return idadeCliente; }
    public void setIdadeCliente(int idadeCliente) { this.idadeCliente = idadeCliente; }

    public TipoAcomodacao getTipoAcomodacao() { return tipoAcomodacao; }
    public void setTipoAcomodacao(TipoAcomodacao tipoAcomodacao) { this.tipoAcomodacao = tipoAcomodacao; }

    public int getQuantidadeVidas() { return quantidadeVidas; }
    public void setQuantidadeVidas(int quantidadeVidas) { this.quantidadeVidas = quantidadeVidas; }

    public List<DependenteRequest> getDependentes() { return dependentes; }
    public void setDependentes(List<DependenteRequest> dependentes) { this.dependentes = dependentes; }
}