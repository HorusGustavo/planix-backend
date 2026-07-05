package com.planix.planix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planix.planix.entity.FaixaEtaria;
import com.planix.planix.entity.Plano;
@Repository
public interface FaixaEtariaRepository extends JpaRepository<FaixaEtaria,Long> {
	
	Optional<FaixaEtaria> findByPlanoIdAndIdadeMinLessThanEqualAndIdadeMaxGreaterThanEqual(
		    Long planoId, int idade, int idade2
		);
	List<FaixaEtaria> findByPlano(Plano plano);

}


