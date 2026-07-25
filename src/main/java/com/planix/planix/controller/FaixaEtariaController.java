package com.planix.planix.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.entity.FaixaEtaria;
import com.planix.planix.entity.Plano;
import com.planix.planix.repository.FaixaEtariaRepository;
import com.planix.planix.service.PlanoService;

@RestController
@RequestMapping("/api/faixas")
public class FaixaEtariaController {

    private final FaixaEtariaRepository faixaEtariaRepository;
    private final PlanoService planoService;

    public FaixaEtariaController(FaixaEtariaRepository faixaEtariaRepository,
                                  PlanoService planoService) {
        this.faixaEtariaRepository = faixaEtariaRepository;
        this.planoService = planoService;
    }

    @PostMapping
    public ResponseEntity<FaixaEtaria> cadastrar(
            @RequestParam int idadeMin,
            @RequestParam int idadeMax,
            @RequestParam(required = false) BigDecimal valorOriginal,
            @RequestParam(required = false, defaultValue = "0") int desconto,
            @RequestParam(required = false) BigDecimal valorComDesconto,
            @RequestParam(required = false) BigDecimal valorAdesao1Vida,
            @RequestParam(required = false) BigDecimal valorAdesao2Vidas,
            @RequestParam(required = false) BigDecimal valorAdesao3Vidas,
            @RequestParam(required = false) BigDecimal valorAdesao4Vidas,
            @RequestParam Long planoId) {

        Plano plano = planoService.buscarPorId(planoId);

        FaixaEtaria faixa = new FaixaEtaria();
        faixa.setIdadeMin(idadeMin);
        faixa.setIdadeMax(idadeMax);
        faixa.setDesconto(desconto);
        faixa.setPlano(plano);

        if (plano.getTipo() == Plano.Tipo.INDIVIDUAL) {
            faixa.setValorOriginal(valorOriginal != null ? valorOriginal : BigDecimal.ZERO);
            faixa.setValorComDesconto(valorComDesconto != null ? valorComDesconto : BigDecimal.ZERO);
        } else {
            faixa.setValorOriginal(BigDecimal.ZERO);
            faixa.setValorComDesconto(BigDecimal.ZERO);
            faixa.setValorAdesao1Vida(valorAdesao1Vida != null ? valorAdesao1Vida : BigDecimal.ZERO);
            faixa.setValorAdesao2Vidas(valorAdesao2Vidas != null ? valorAdesao2Vidas : BigDecimal.ZERO);
            faixa.setValorAdesao3Vidas(valorAdesao3Vidas != null ? valorAdesao3Vidas : BigDecimal.ZERO);
            faixa.setValorAdesao4Vidas(valorAdesao4Vidas != null ? valorAdesao4Vidas : BigDecimal.ZERO);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(faixaEtariaRepository.save(faixa));
    }

    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<FaixaEtaria>> listarPorPlano(@PathVariable Long planoId) {
        Plano plano = planoService.buscarPorId(planoId);
        return ResponseEntity.ok(faixaEtariaRepository.findByPlano(plano));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        faixaEtariaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FaixaEtaria> atualizar(
            @PathVariable Long id,
            @RequestParam(required = false) BigDecimal valorOriginal,
            @RequestParam(required = false) BigDecimal valorComDesconto,
            @RequestParam(required = false, defaultValue = "0") int desconto,
            @RequestParam(required = false) BigDecimal valorAdesao1Vida,
            @RequestParam(required = false) BigDecimal valorAdesao2Vidas,
            @RequestParam(required = false) BigDecimal valorAdesao3Vidas,
            @RequestParam(required = false) BigDecimal valorAdesao4Vidas) {

        FaixaEtaria faixa = faixaEtariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faixa não encontrada"));

        if (valorOriginal != null) faixa.setValorOriginal(valorOriginal);
        if (valorComDesconto != null) faixa.setValorComDesconto(valorComDesconto);
        faixa.setDesconto(desconto);
        if (valorAdesao1Vida != null) faixa.setValorAdesao1Vida(valorAdesao1Vida);
        if (valorAdesao2Vidas != null) faixa.setValorAdesao2Vidas(valorAdesao2Vidas);
        if (valorAdesao3Vidas != null) faixa.setValorAdesao3Vidas(valorAdesao3Vidas);
        if (valorAdesao4Vidas != null) faixa.setValorAdesao4Vidas(valorAdesao4Vidas);

        return ResponseEntity.ok(faixaEtariaRepository.save(faixa));
    }
}