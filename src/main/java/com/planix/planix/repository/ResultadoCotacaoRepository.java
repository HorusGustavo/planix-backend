package com.planix.planix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planix.planix.entity.ResultadoCotacao;
@Repository
public interface ResultadoCotacaoRepository extends JpaRepository<ResultadoCotacao,Long> {

}


