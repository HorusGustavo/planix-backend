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

import com.planix.planix.dto.HospitalRequest;
import com.planix.planix.dto.HospitalResponse;
import com.planix.planix.entity.Hospital;
import com.planix.planix.service.HospitalService;

@RestController
@RequestMapping("/api/hospitais")
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    private HospitalResponse toResponse(Hospital hospital) {
        HospitalResponse response = new HospitalResponse();
        response.setId(hospital.getId());
        response.setNome(hospital.getNome());
        response.setCidade(hospital.getCidade());
        response.setEstado(hospital.getEstado());
        return response;
    }

    
    @PostMapping
    public ResponseEntity<HospitalResponse> cadastrar(@RequestBody HospitalRequest request) {
        Hospital hospital = hospitalService.cadastrar(
                request.getNome(),
                request.getCidade(),
                request.getEstado());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(hospital));
    }

    @GetMapping
    public ResponseEntity<List<HospitalResponse>> listarHospital() {
        List<HospitalResponse> lista = hospitalService.listarHospital()
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(hospitalService.buscarPorId(id)));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<HospitalResponse> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(toResponse(hospitalService.buscarPorNome(nome)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospitalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


