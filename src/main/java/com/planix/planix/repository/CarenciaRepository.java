package com.planix.planix.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.planix.planix.entity.Carencia;
import com.planix.planix.entity.Plano;

public interface CarenciaRepository extends JpaRepository<Carencia, Long> {
    List<Carencia> findByPlano(Plano plano);
}