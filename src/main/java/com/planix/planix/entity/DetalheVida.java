package com.planix.planix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

@Entity
public class DetalheVida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomes;

    @Column(nullable = false)
    private String faixaEtaria;

    @Column(nullable = false)
    private int quantidadeVidas;

    @Column(nullable = false)
    private BigDecimal valorGrupo;

    @ManyToOne
    @JoinColumn(name = "resultado_cotacao_id", nullable = false)
    @JsonIgnore
    private ResultadoCotacao resultadoCotacao;

    public DetalheVida() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomes() { return nomes; }
    public void setNomes(String nomes) { this.nomes = nomes; }

    public String getFaixaEtaria() { return faixaEtaria; }
    public void setFaixaEtaria(String faixaEtaria) { this.faixaEtaria = faixaEtaria; }

    public int getQuantidadeVidas() { return quantidadeVidas; }
    public void setQuantidadeVidas(int quantidadeVidas) { this.quantidadeVidas = quantidadeVidas; }

    public BigDecimal getValorGrupo() { return valorGrupo; }
    public void setValorGrupo(BigDecimal valorGrupo) { this.valorGrupo = valorGrupo; }

    public ResultadoCotacao getResultadoCotacao() { return resultadoCotacao; }
    public void setResultadoCotacao(ResultadoCotacao resultadoCotacao) { this.resultadoCotacao = resultadoCotacao; }
}
