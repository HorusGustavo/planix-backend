package com.planix.planix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planix.planix.entity.Hospital;
import com.planix.planix.entity.Plano;
import com.planix.planix.repository.HospitalRepository;
@Service
public class HospitalService {
	@Autowired
	private HospitalRepository hospitalRepository;
	
	
	public HospitalService(HospitalRepository hospitalRepository) {
		this.hospitalRepository = hospitalRepository;
	}
	
	
	public Hospital cadastrar(String nome, String cidade, String estado) {
		Hospital novoCadastro = new Hospital();
		novoCadastro.setNome(nome);
		novoCadastro.setCidade(cidade);
		novoCadastro.setEstado(estado);
		return hospitalRepository.save(novoCadastro);
	}
	
	public List<Hospital> listarHospital(){
		return hospitalRepository.findAll();
	}
	
	public Hospital buscarPorId(Long id) {
		return hospitalRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospital não encontrado"));
	}
	
	public Hospital buscarPorNome(String nome) {
		return hospitalRepository.findByNome(nome).orElseThrow(() -> new RuntimeException("Hospital não encontrado"));
	}
	
	public void deletar(Long id) {
		buscarPorId(id);
		hospitalRepository.deleteById(id);
	}

}


