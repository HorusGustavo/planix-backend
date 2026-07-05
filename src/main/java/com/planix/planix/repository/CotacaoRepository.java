package com.planix.planix.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planix.planix.entity.Cotacao;
import com.planix.planix.entity.User;
@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao,Long> {
	List<Cotacao> findByUser(User user);

}


