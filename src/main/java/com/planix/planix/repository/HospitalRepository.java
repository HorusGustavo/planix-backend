package com.planix.planix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planix.planix.entity.Hospital;
@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {
	
	Optional<Hospital>findByNome(String nome);

}


