package com.planix.planix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planix.planix.entity.Operadora;
import com.planix.planix.entity.Plano;
import com.planix.planix.repository.OperadoraRepository;
@Service
public class OperadoraService {
	@Autowired
	private OperadoraRepository operadoraRepository;
	
	
	public OperadoraService(OperadoraRepository operadoraRepository) {
		this.operadoraRepository = operadoraRepository;
	}
	
	public Operadora cadastrar(String nome,List<Plano> plano) {
		Operadora novoCadastro = new Operadora();
		novoCadastro.setNome(nome);
		novoCadastro.setPlanos(plano);
		return operadoraRepository.save(novoCadastro);
	}
	
	public List<Operadora> listar(){
		return operadoraRepository.findAll();
	}
	
	public Operadora buscarPorId(Long id) {
		return operadoraRepository.findById(id).orElseThrow(() -> new RuntimeException("Operadora não encontrada"));
	}
	
	public Operadora buscarPorNome(String nome) {
		return operadoraRepository.findByNome(nome).orElseThrow(() -> new RuntimeException("Operadora não encontrada"));
	}
	
	public void deletar(Long id) {
		buscarPorId(id);
		operadoraRepository.deleteById(id);
		
	}
}



