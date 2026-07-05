package com.planix.planix.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.planix.planix.entity.FaixaEtaria;
import com.planix.planix.entity.Hospital;
import com.planix.planix.entity.Operadora;
import com.planix.planix.entity.Plano;
import com.planix.planix.entity.Plano.Acomodacao;
import com.planix.planix.entity.Plano.Tipo;
import com.planix.planix.repository.PlanoRepository;

@Service
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Autowired
    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public Plano cadastrar(String nome, Tipo tipo, String regiao, Acomodacao acomodacao,
            Plano.Coparticipacao coparticipacao, String imagePath,
            List<FaixaEtaria> faixasEtarias, Operadora operadora) {
        Plano cadastro = new Plano();
        cadastro.setNome(nome);
        cadastro.setTipo(tipo);
        cadastro.setRegiao(regiao);
        cadastro.setAcomodacao(acomodacao);
        cadastro.setCoparticipacao(coparticipacao);
        cadastro.setImagePath(imagePath);
        cadastro.setFaixasEtarias(faixasEtarias);
        cadastro.setOperadora(operadora);
        return planoRepository.save(cadastro);
    }

    public List<Plano> listar() {
        return planoRepository.findAll();
    }

    public List<Plano> buscarPorOperadora(Operadora operadora) {
        return planoRepository.findByOperadora(operadora);
    }

    public Plano buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));
    }

    public void deletarPlano(Long id) {
        buscarPorId(id);
        planoRepository.deleteById(id);
    }
    
    public Plano salvar(Plano plano) {
        return planoRepository.save(plano);
    }
}

