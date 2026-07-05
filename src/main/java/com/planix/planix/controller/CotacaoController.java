package com.planix.planix.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planix.planix.dto.CarenciaDto;
import com.planix.planix.dto.CotacaoRequest;
import com.planix.planix.dto.CotacaoResponse;
import com.planix.planix.dto.DetalheVidaResponse;
import com.planix.planix.dto.ResultadoResponse;
import com.planix.planix.entity.Cotacao;
import com.planix.planix.entity.DetalheVida;
import com.planix.planix.entity.Plano;
import com.planix.planix.entity.User;
import com.planix.planix.repository.DetalheVidaRepository;
import com.planix.planix.service.CarenciaService;
import com.planix.planix.service.CotacaoService;
import com.planix.planix.service.UserService;

@RestController
@RequestMapping("/api/cotacoes")
public class CotacaoController {

    private final CotacaoService cotacaoService;
    private final UserService userService;
    private final CarenciaService carenciaService;
    private final DetalheVidaRepository detalheVidaRepository;

    @Autowired
    public CotacaoController(CotacaoService cotacaoService,
                              UserService userService,
                              CarenciaService carenciaService,DetalheVidaRepository detalheVidaRepository) {
        this.cotacaoService = cotacaoService;
        this.userService = userService;
        this.carenciaService = carenciaService;
        this.detalheVidaRepository = detalheVidaRepository;
    }

    private CotacaoResponse toResponse(Cotacao cotacao) {
        CotacaoResponse response = new CotacaoResponse();
        response.setId(cotacao.getId());
        response.setNomeCliente(cotacao.getNomeCliente());
        response.setIdadeCliente(cotacao.getIdadeCliente());
        response.setCreatedAt(cotacao.getCreatedAt());

        List<ResultadoResponse> resultados = cotacao.getResultadosCotacao()
                .stream().map(r -> {
                    ResultadoResponse res = new ResultadoResponse();
                    res.setFaixaEtariaEncontrada(r.getFaixaEtariaEncontrada());
                    res.setValorFinal(r.getValorFinal());
                    res.setValorOriginal(r.getValorOriginal());
                    res.setTipoAcomodacao(r.getTipoAcomodacao());
                    res.setNomePlano(r.getPlano().getNome());
                    res.setNomeOperadora(r.getPlano().getOperadora().getNome());
                    res.setCoparticipacao(r.getPlano().getCoparticipacao().name());
                    res.setPlanoId(r.getPlano().getId());
                    res.setTipoPlano(r.getPlano().getTipo().name());
                    res.setQuantidadeVidas(cotacao.getQuantidadeVidas());

                    List<CarenciaDto> carencias = carenciaService.listarPorPlano(r.getPlano())
                            .stream()
                            .map(c -> new CarenciaDto(c.getDescricao(), c.getPrazo()))
                            .collect(Collectors.toList());
                    res.setCarencias(carencias);

                    if (r.getPlano().getTipo() == Plano.Tipo.ADESAO) {
                        List<DetalheVida> detalhesDb = detalheVidaRepository.findByResultadoCotacao(r);
                        if (detalhesDb != null && !detalhesDb.isEmpty()) {
                            List<DetalheVidaResponse> detalhes = detalhesDb.stream().map(d -> {
                                DetalheVidaResponse dv = new DetalheVidaResponse();
                                dv.setNomes(Arrays.asList(d.getNomes().split(", ")));
                                dv.setFaixaEtaria(d.getFaixaEtaria());
                                dv.setQuantidadeVidas(d.getQuantidadeVidas());
                                dv.setValorPorGrupo(d.getValorGrupo());
                                return dv;
                            }).collect(Collectors.toList());

                            BigDecimal valorTotal = detalhesDb.stream()
                                    .map(DetalheVida::getValorGrupo)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                            res.setDetalhesVidas(detalhes);
                            res.setValorTotal(valorTotal);
                        }
                    }

                    return res;
                }).collect(Collectors.toList());

        response.setResultadoCotacao(resultados);
        return response;
    }

    @PostMapping("/gerar")
    public ResponseEntity<CotacaoResponse> gerar(@RequestBody CotacaoRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.buscarPorEmail(email);

        Cotacao cotacao = cotacaoService.gerarCotacao(
                request.getNomeCliente(),
                request.getIdadeCliente(),
                request.getTipoAcomodacao(),
                request.getQuantidadeVidas(),
                request.getDependentes(),
                user);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(cotacao));
    }

    @GetMapping
    public ResponseEntity<List<CotacaoResponse>> listarMinhas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.buscarPorEmail(email);
        List<Cotacao> cotacoes = cotacaoService.listarPorUser(user);
        return ResponseEntity.ok(cotacoes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CotacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(cotacaoService.buscarPorId(id)));
    }
}