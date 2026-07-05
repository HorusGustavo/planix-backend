package com.planix.planix.dto;

import java.math.BigDecimal;
import java.util.List;

public class DetalheVidaResponse {

    private List<String> nomes;
    private String faixaEtaria;
    private int quantidadeVidas;
    private BigDecimal valorPorGrupo;

    public DetalheVidaResponse() {}

    public List<String> getNomes() { return nomes; }
    public void setNomes(List<String> nomes) { this.nomes = nomes; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public int getQuantidadeVidas() { return quantidadeVidas; }
    public void setQuantidadeVidas(int quantidadeVidas) { this.quantidadeVidas = quantidadeVidas; }

    public BigDecimal getValorPorGrupo() { return valorPorGrupo; }
    public void setValorPorGrupo(BigDecimal valorPorGrupo) { this.valorPorGrupo = valorPorGrupo; }
}