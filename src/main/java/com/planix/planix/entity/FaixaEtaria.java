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
public class FaixaEtaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int idadeMin;

    @Column(nullable = false)
    private int idadeMax;

    @Column(nullable = false)
    private BigDecimal valorOriginal;

    @Column(nullable = false)
    private BigDecimal valorComDesconto;

    @Column(nullable = false)
    private int desconto;

    
    private BigDecimal valorAdesao1Vida;

    
    private BigDecimal valorAdesao2Vidas;

    
    private BigDecimal valorAdesao3Vidas;

    
    private BigDecimal valorAdesao4Vidas;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    @JsonIgnore
    private Plano plano;

    public FaixaEtaria() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getIdadeMin() { return idadeMin; }
    public void setIdadeMin(int idadeMin) { this.idadeMin = idadeMin; }

    public int getIdadeMax() { return idadeMax; }
    public void setIdadeMax(int idadeMax) { this.idadeMax = idadeMax; }

    public BigDecimal getValorOriginal() { return valorOriginal; }
    public void setValorOriginal(BigDecimal valorOriginal) { this.valorOriginal = valorOriginal; }

    public BigDecimal getValorComDesconto() { return valorComDesconto; }
    public void setValorComDesconto(BigDecimal valorComDesconto) { this.valorComDesconto = valorComDesconto; }

    public int getDesconto() { return desconto; }
    public void setDesconto(int desconto) { this.desconto = desconto; }

    public BigDecimal getValorAdesao1Vida() { return valorAdesao1Vida; }
    public void setValorAdesao1Vida(BigDecimal valorAdesao1Vida) { this.valorAdesao1Vida = valorAdesao1Vida; }

    public BigDecimal getValorAdesao2Vidas() { return valorAdesao2Vidas; }
    public void setValorAdesao2Vidas(BigDecimal valorAdesao2Vidas) { this.valorAdesao2Vidas = valorAdesao2Vidas; }

    public BigDecimal getValorAdesao3Vidas() { return valorAdesao3Vidas; }
    public void setValorAdesao3Vidas(BigDecimal valorAdesao3Vidas) { this.valorAdesao3Vidas = valorAdesao3Vidas; }

    public BigDecimal getValorAdesao4Vidas() { return valorAdesao4Vidas; }
    public void setValorAdesao4Vidas(BigDecimal valorAdesao4Vidas) { this.valorAdesao4Vidas = valorAdesao4Vidas; }

    public Plano getPlano() { return plano; }
    public void setPlano(Plano plano) { this.plano = plano; }
}