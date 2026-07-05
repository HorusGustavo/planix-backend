package com.planix.planix.repository;

import com.planix.planix.entity.DetalheVida;
import com.planix.planix.entity.ResultadoCotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalheVidaRepository extends JpaRepository<DetalheVida, Long> {
    List<DetalheVida> findByResultadoCotacao(ResultadoCotacao resultadoCotacao);
}
