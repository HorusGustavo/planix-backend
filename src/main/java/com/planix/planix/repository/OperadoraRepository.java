package com.planix.planix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planix.planix.entity.Operadora;
@Repository
public interface OperadoraRepository extends JpaRepository<Operadora,Long> {
	
	Optional<Operadora>findByNome(String nome);

}


