package com.planix.planix.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.dto.OperadoraRequest;
import com.planix.planix.dto.OperadoraResponse;
import com.planix.planix.entity.Operadora;
import com.planix.planix.service.OperadoraService;

@RestController
@RequestMapping("/api/operadoras")
public class OperadoraController {

    private final OperadoraService operadoraService;

    @Autowired
    public OperadoraController(OperadoraService operadoraService) {
        this.operadoraService = operadoraService;
    }

    private OperadoraResponse toResponse(Operadora operadora) {
        OperadoraResponse response = new OperadoraResponse();
        response.setId(operadora.getId());
        response.setNome(operadora.getNome());
        return response;
    }

    @PostMapping
    public ResponseEntity<OperadoraResponse> cadastrar(@RequestBody OperadoraRequest request) {
        Operadora operadora = operadoraService.cadastrar(request.getNome(), new ArrayList<>());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(operadora));
    }

    @GetMapping
    public ResponseEntity<List<OperadoraResponse>> listar() {
        List<OperadoraResponse> lista = operadoraService.listar()
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperadoraResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(operadoraService.buscarPorId(id)));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<OperadoraResponse> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(toResponse(operadoraService.buscarPorNome(nome)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        operadoraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

