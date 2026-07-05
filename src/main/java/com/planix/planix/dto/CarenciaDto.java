package com.planix.planix.dto;

public class CarenciaDto {
    private String descricao;
    private String prazo;

    public CarenciaDto(String descricao, String prazo) {
        this.descricao = descricao;
        this.prazo = prazo;
    }

    public String getDescricao() { return descricao; }
    public String getPrazo() { return prazo; }
}