package com.planix.planix.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.planix.planix.entity.Operadora;
import com.planix.planix.entity.Plano;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
   
    List<Plano> findByOperadora(Operadora operadora);
}
