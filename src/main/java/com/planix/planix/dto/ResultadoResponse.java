package com.planix.planix.dto;

import com.planix.planix.entity.ResultadoCotacao.TipoAcomodacao;
import java.math.BigDecimal;
import java.util.List;

public class ResultadoResponse {

    private String faixaEtariaEncontrada;
    private BigDecimal valorFinal;
    private BigDecimal valorOriginal;
    private TipoAcomodacao tipoAcomodacao;
    private String nomePlano;
    private String nomeOperadora;
    private String coparticipacao;
    private List<CarenciaDto> carencias;
    private Long planoId;
    private String tipoPlano;
    private int quantidadeVidas;

    // Novo — detalhes por faixa etária para plano ADESAO
    private List<DetalheVidaResponse> detalhesVidas;
    private BigDecimal valorTotal;

    public String getFaixaEtariaEncontrada() { return faixaEtariaEncontrada; }
    public void setFaixaEtariaEncontrada(String faixaEtariaEncontrada) { this.faixaEtariaEncontrada = faixaEtariaEncontrada; }

    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }

    public BigDecimal getValorOriginal() { return valorOriginal; }
    public void setValorOriginal(BigDecimal valorOriginal) { this.valorOriginal = valorOriginal; }

    public TipoAcomodacao getTipoAcomodacao() { return tipoAcomodacao; }
    public void setTipoAcomodacao(TipoAcomodacao tipoAcomodacao) { this.tipoAcomodacao = tipoAcomodacao; }

    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }

    public String getNomeOperadora() { return nomeOperadora; }
    public void setNomeOperadora(String nomeOperadora) { this.nomeOperadora = nomeOperadora; }

    public String getCoparticipacao() { return coparticipacao; }
    public void setCoparticipacao(String coparticipacao) { this.coparticipacao = coparticipacao; }

    public List<CarenciaDto> getCarencias() { return carencias; }
    public void setCarencias(List<CarenciaDto> carencias) { this.carencias = carencias; }

    public Long getPlanoId() { return planoId; }
    public void setPlanoId(Long planoId) { this.planoId = planoId; }

    public String getTipoPlano() { return tipoPlano; }
    public void setTipoPlano(String tipoPlano) { this.tipoPlano = tipoPlano; }

    public int getQuantidadeVidas() { return quantidadeVidas; }
    public void setQuantidadeVidas(int quantidadeVidas) { this.quantidadeVidas = quantidadeVidas; }

    public List<DetalheVidaResponse> getDetalhesVidas() { return detalhesVidas; }
    public void setDetalhesVidas(List<DetalheVidaResponse> detalhesVidas) { this.detalhesVidas = detalhesVidas; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
}



