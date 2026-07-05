package com.planix.planix.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.planix.planix.dto.HospitalResponse;
import com.planix.planix.dto.OperadoraResponse;
import com.planix.planix.dto.PlanoResponse;
import com.planix.planix.entity.Hospital;
import com.planix.planix.entity.Operadora;
import com.planix.planix.entity.Plano;
import com.planix.planix.service.HospitalService;
import com.planix.planix.service.OperadoraService;
import com.planix.planix.service.PlanoService;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    private final PlanoService planoService;
    private final HospitalService hospitalService;
    private final OperadoraService operadoraService;

    @Autowired
    public PlanoController(PlanoService planoService,
                           HospitalService hospitalService,
                           OperadoraService operadoraService) {
        this.planoService = planoService;
        this.hospitalService = hospitalService;
        this.operadoraService = operadoraService;
    }

    private PlanoResponse toResponse(Plano plano) {
        PlanoResponse response = new PlanoResponse();
        response.setId(plano.getId());
        response.setNome(plano.getNome());
        response.setTipo(plano.getTipo());
        response.setRegiao(plano.getRegiao());
        response.setAcomodacao(plano.getAcomodacao());
        response.setCoparticipacao(plano.getCoparticipacao());

        OperadoraResponse operadoraResponse = new OperadoraResponse();
        operadoraResponse.setId(plano.getOperadora().getId());
        operadoraResponse.setNome(plano.getOperadora().getNome());
        response.setOperadora(operadoraResponse);

        return response;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlanoResponse> cadastrar(
            @RequestParam String nome,
            @RequestParam Plano.Tipo tipo,
            @RequestParam String regiao,
            @RequestParam Plano.Acomodacao acomodacao,
            @RequestParam Plano.Coparticipacao coparticipacao,
            @RequestParam Long operadoraId,
            @RequestPart(required = false) MultipartFile imagem) throws IOException {

        String imagePath = "";
        if (imagem != null && !imagem.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
            File pasta = new File(System.getProperty("user.home") + "/planix-uploads");
            if (!pasta.exists()) pasta.mkdirs();
            File arquivo = new File(pasta, nomeArquivo);
            imagem.transferTo(arquivo.toPath());
            imagePath = arquivo.getAbsolutePath();
        }

        Operadora operadora = operadoraService.buscarPorId(operadoraId);

        Plano plano = planoService.cadastrar(
                nome, tipo, regiao, acomodacao, coparticipacao,
                imagePath, new ArrayList<>(), operadora);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(plano));
    }
    
    @GetMapping
    public ResponseEntity<List<PlanoResponse>> listar() {
        List<PlanoResponse> lista = planoService.listar()
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(planoService.buscarPorId(id)));
    }

    @GetMapping("/operadora/{operadoraId}")
    public ResponseEntity<List<PlanoResponse>> buscarPorOperadora(@PathVariable Long operadoraId) {
        Operadora operadora = operadoraService.buscarPorId(operadoraId);
        List<PlanoResponse> lista = planoService.buscarPorOperadora(operadora)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping("/{planoId}/hospitais/{hospitalId}")
    public ResponseEntity<Void> adicionarHospital(
            @PathVariable Long planoId,
            @PathVariable Long hospitalId) {
        Plano plano = planoService.buscarPorId(planoId);
        Hospital hospital = hospitalService.buscarPorId(hospitalId);
        plano.getHospitais().add(hospital);
        planoService.salvar(plano);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{planoId}/hospitais/{hospitalId}")
    public ResponseEntity<Void> removerHospital(
            @PathVariable Long planoId,
            @PathVariable Long hospitalId) {
        Plano plano = planoService.buscarPorId(planoId);
        plano.getHospitais().removeIf(h -> h.getId().equals(hospitalId));
        planoService.salvar(plano);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{planoId}/hospitais")
    public ResponseEntity<List<HospitalResponse>> listarHospitais(@PathVariable Long planoId) {
        Plano plano = planoService.buscarPorId(planoId);
        List<HospitalResponse> lista = plano.getHospitais().stream().map(h -> {
            HospitalResponse r = new HospitalResponse();
            r.setId(h.getId());
            r.setNome(h.getNome());
            r.setCidade(h.getCidade());
            r.setEstado(h.getEstado());
            return r;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}