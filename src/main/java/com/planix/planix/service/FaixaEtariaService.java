package com.planix.planix.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planix.planix.entity.FaixaEtaria;
import com.planix.planix.entity.Plano;
import com.planix.planix.repository.FaixaEtariaRepository;
@Service
public class FaixaEtariaService {
	@Autowired
	private FaixaEtariaRepository faixaEtariaRepository;
	
	
	public FaixaEtariaService(FaixaEtariaRepository faixaEtariaRepository) {
		this.faixaEtariaRepository = faixaEtariaRepository;
	}
	
	public FaixaEtaria cadastrar(int idadeMin, int idadeMax,BigDecimal valorOriginal, BigDecimal valorComDesconto, int desconto,Plano plano) {
	FaixaEtaria cadastro = new FaixaEtaria();
	cadastro.setIdadeMin(idadeMin);
	cadastro.setIdadeMax(idadeMax);
	cadastro.setValorOriginal(valorOriginal);
	cadastro.setValorComDesconto(valorComDesconto);
	cadastro.setDesconto(desconto);
	cadastro.setPlano(plano);
	return faixaEtariaRepository.save(cadastro);
	}
	
	
	public FaixaEtaria buscarPorIdadePlano(Long id, int idade) {
		return faixaEtariaRepository.findByPlanoIdAndIdadeMinLessThanEqualAndIdadeMaxGreaterThanEqual(id, idade, idade).orElseThrow(() -> new RuntimeException("Faixa etária não encontrada, para a idade"));
	}
	
	

}


