package com.planix.planix.dto;

public class DependenteRequest {

    private String nome;
    private int idade;
    private String vinculo;

    public DependenteRequest() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getVinculo() { return vinculo; }
    public void setVinculo(String vinculo) { this.vinculo = vinculo; }
}
