package com.planix.planix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.planix.planix.entity.Carencia;
import com.planix.planix.entity.Plano;
import com.planix.planix.service.CarenciaService;
import com.planix.planix.service.PlanoService;

@RestController
@RequestMapping("/api/carencias")
public class CarenciaController {

    private final CarenciaService carenciaService;
    private final PlanoService planoService;

    @Autowired
    public CarenciaController(CarenciaService carenciaService, PlanoService planoService) {
        this.carenciaService = carenciaService;
        this.planoService = planoService;
    }

    @PostMapping
    public ResponseEntity<Carencia> cadastrar(
            @RequestParam String descricao,
            @RequestParam String prazo,
            @RequestParam Long planoId) {
        Plano plano = planoService.buscarPorId(planoId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carenciaService.cadastrar(descricao, prazo, plano));
    }

    @GetMapping("/plano/{planoId}")
    public ResponseEntity<List<Carencia>> listarPorPlano(@PathVariable Long planoId) {
        Plano plano = planoService.buscarPorId(planoId);
        return ResponseEntity.ok(carenciaService.listarPorPlano(plano));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}