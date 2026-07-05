package com.planix.planix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.planix.planix.entity.Carencia;
import com.planix.planix.entity.Plano;
import com.planix.planix.repository.CarenciaRepository;

@Service
public class CarenciaService {

	@Autowired
    private CarenciaRepository carenciaRepository;

    public CarenciaService(CarenciaRepository carenciaRepository) {
        this.carenciaRepository = carenciaRepository;
    }

    public Carencia cadastrar(String descricao, String prazo, Plano plano) {
        Carencia carencia = new Carencia();
        carencia.setDescricao(descricao);
        carencia.setPrazo(prazo);
        carencia.setPlano(plano);
        return carenciaRepository.save(carencia);
    }

    public List<Carencia> listarPorPlano(Plano plano) {
        return carenciaRepository.findByPlano(plano);
    }

    public void deletar(Long id) {
        carenciaRepository.deleteById(id);
    }
}