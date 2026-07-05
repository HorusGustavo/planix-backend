package com.planix.planix.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planix.planix.dto.DependenteRequest;
import com.planix.planix.entity.Cotacao;
import com.planix.planix.entity.DetalheVida;
import com.planix.planix.entity.FaixaEtaria;
import com.planix.planix.entity.Plano;
import com.planix.planix.entity.ResultadoCotacao;
import com.planix.planix.entity.ResultadoCotacao.TipoAcomodacao;
import com.planix.planix.entity.User;
import com.planix.planix.repository.CotacaoRepository;
import com.planix.planix.repository.DetalheVidaRepository;
import com.planix.planix.repository.ResultadoCotacaoRepository;

@Service
public class CotacaoService {

    private final CotacaoRepository cotacaoRepository;
    private final ResultadoCotacaoRepository resultadoCotacaoRepository;
    private final DetalheVidaRepository detalheVidaRepository;
    private final PlanoService planoService;
    private final FaixaEtariaService faixaEtariaService;

    public CotacaoService(CotacaoRepository cotacaoRepository,
                          ResultadoCotacaoRepository resultadoCotacaoRepository,
                          DetalheVidaRepository detalheVidaRepository,
                          PlanoService planoService,
                          FaixaEtariaService faixaEtariaService) {
        this.cotacaoRepository = cotacaoRepository;
        this.resultadoCotacaoRepository = resultadoCotacaoRepository;
        this.detalheVidaRepository = detalheVidaRepository;
        this.planoService = planoService;
        this.faixaEtariaService = faixaEtariaService;
    }

    public Cotacao gerarCotacao(String nomeCliente, int idadeCliente,
                                TipoAcomodacao tipoAcomodacao,
                                int quantidadeVidas,
                                List<DependenteRequest> dependentes,
                                User user) {

        List<Plano> planos = planoService.listar();

        Cotacao cotacao = new Cotacao();
        cotacao.setNomeCliente(nomeCliente);
        cotacao.setIdadeCliente(idadeCliente);
        cotacao.setCreatedAt(LocalDateTime.now());
        cotacao.setUser(user);
        cotacao.setQuantidadeVidas(quantidadeVidas);

        // Salva dependentes em JSON
        try {
            if (dependentes != null && !dependentes.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                cotacao.setDependentesJson(mapper.writeValueAsString(dependentes));
            }
        } catch (Exception e) {
            // ignora erro de serialização
        }

        cotacaoRepository.save(cotacao);

        List<ResultadoCotacao> resultados = new ArrayList<>();

        for (Plano plano : planos) {
            if (plano.getAcomodacao() != Plano.Acomodacao.valueOf(tipoAcomodacao.name())) {
                continue;
            }
            try {
                if (plano.getTipo() == Plano.Tipo.INDIVIDUAL) {
                    FaixaEtaria faixa = faixaEtariaService.buscarPorIdadePlano(plano.getId(), idadeCliente);

                    ResultadoCotacao resultado = new ResultadoCotacao();
                    resultado.setCotacao(cotacao);
                    resultado.setPlano(plano);
                    resultado.setTipoAcomodacao(tipoAcomodacao);
                    resultado.setValorFinal(faixa.getValorComDesconto());
                    resultado.setValorOriginal(faixa.getValorOriginal());
                    resultado.setFaixaEtariaEncontrada(faixa.getIdadeMin() + " a " + faixa.getIdadeMax());
                    resultadoCotacaoRepository.save(resultado);
                    resultados.add(resultado);

                } else {
                    // ADESAO — monta lista de todas as pessoas
                    List<String> nomesTodas = new ArrayList<>();
                    List<Integer> idadesTodas = new ArrayList<>();

                    nomesTodas.add(nomeCliente);
                    idadesTodas.add(idadeCliente);

                    if (dependentes != null) {
                        for (DependenteRequest dep : dependentes) {
                            nomesTodas.add(dep.getNome() == null || dep.getNome().isEmpty()
                                    ? "Dependente" : dep.getNome());
                            idadesTodas.add(dep.getIdade());
                        }
                    }

                    // Agrupa por faixa etária
                    Map<String, List<String>> grupos = new LinkedHashMap<>();
                    Map<String, FaixaEtaria> faixasPorGrupo = new LinkedHashMap<>();

                    boolean todasEncontradas = true;
                    for (int i = 0; i < nomesTodas.size(); i++) {
                        try {
                            FaixaEtaria faixa = faixaEtariaService.buscarPorIdadePlano(
                                    plano.getId(), idadesTodas.get(i));
                            String chave = faixa.getIdadeMin() + "-" + faixa.getIdadeMax();
                            grupos.computeIfAbsent(chave, k -> new ArrayList<>())
                                    .add(nomesTodas.get(i));
                            faixasPorGrupo.put(chave, faixa);
                        } catch (RuntimeException e) {
                            todasEncontradas = false;
                            break;
                        }
                    }

                    if (!todasEncontradas) continue;

                    // Calcula valor total
                    BigDecimal valorTotal = BigDecimal.ZERO;
                    for (Map.Entry<String, List<String>> entry : grupos.entrySet()) {
                        BigDecimal valorGrupo = getValorAdesaoPorVidas(
                                faixasPorGrupo.get(entry.getKey()), entry.getValue().size());
                        valorTotal = valorTotal.add(valorGrupo);
                    }

                    FaixaEtaria faixaTitular = faixaEtariaService.buscarPorIdadePlano(
                            plano.getId(), idadeCliente);

                    ResultadoCotacao resultado = new ResultadoCotacao();
                    resultado.setCotacao(cotacao);
                    resultado.setPlano(plano);
                    resultado.setTipoAcomodacao(tipoAcomodacao);
                    resultado.setValorFinal(valorTotal);
                    resultado.setValorOriginal(valorTotal);
                    resultado.setFaixaEtariaEncontrada(
                            faixaTitular.getIdadeMin() + " a " + faixaTitular.getIdadeMax());
                    resultadoCotacaoRepository.save(resultado);

                    // Salva detalhes por faixa etária
                    for (Map.Entry<String, List<String>> entry : grupos.entrySet()) {
                        FaixaEtaria faixa = faixasPorGrupo.get(entry.getKey());
                        List<String> nomesGrupo = entry.getValue();
                        int qtdGrupo = nomesGrupo.size();
                        BigDecimal valorGrupo = getValorAdesaoPorVidas(faixa, qtdGrupo);

                        DetalheVida detalhe = new DetalheVida();
                        detalhe.setNomes(String.join(", ", nomesGrupo));
                        detalhe.setFaixaEtaria(faixa.getIdadeMin() + " a " +
                                faixa.getIdadeMax() + " anos");
                        detalhe.setQuantidadeVidas(qtdGrupo);
                        detalhe.setValorGrupo(valorGrupo);
                        detalhe.setResultadoCotacao(resultado);
                        detalheVidaRepository.save(detalhe);
                    }

                    resultados.add(resultado);
                }

            } catch (RuntimeException e) {
                continue;
            }
        }

        cotacao.setResultadosCotacao(resultados);
        return cotacaoRepository.save(cotacao);
    }

    public BigDecimal getValorAdesaoPublico(FaixaEtaria faixa, int quantidadeVidas) {
        return getValorAdesaoPorVidas(faixa, quantidadeVidas);
    }

    private BigDecimal getValorAdesaoPorVidas(FaixaEtaria faixa, int quantidadeVidas) {
        if (quantidadeVidas >= 4) {
            return faixa.getValorAdesao4Vidas() != null
                    ? faixa.getValorAdesao4Vidas() : BigDecimal.ZERO;
        }
        switch (quantidadeVidas) {
            case 2: return faixa.getValorAdesao2Vidas() != null
                    ? faixa.getValorAdesao2Vidas() : BigDecimal.ZERO;
            case 3: return faixa.getValorAdesao3Vidas() != null
                    ? faixa.getValorAdesao3Vidas() : BigDecimal.ZERO;
            default: return faixa.getValorAdesao1Vida() != null
                    ? faixa.getValorAdesao1Vida() : BigDecimal.ZERO;
        }
    }

    public List<Cotacao> listarPorUser(User user) {
        return cotacaoRepository.findByUser(user);
    }

    public Cotacao buscarPorId(Long id) {
        return cotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));
    }
}