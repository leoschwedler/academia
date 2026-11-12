package dev.schwedler.academia.avaliacaoFisica.controller;

import dev.schwedler.academia.avaliacaoFisica.dto.AvaliacoesFisicasProjection;
import dev.schwedler.academia.avaliacaoFisica.request.AvaliacaoFisicaRequest;
import dev.schwedler.academia.avaliacaoFisica.response.AvaliacaoFisicaResponse;
import dev.schwedler.academia.avaliacaoFisica.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ===== CAMADA CONTROLLER =====
// Porta de entrada da API: recebe a requisicao HTTP, delega para o service e devolve a resposta.
// NAO coloque regra de negocio aqui; so traducao HTTP <-> chamada de metodo.
@RestController // = @Controller + @ResponseBody: o retorno dos metodos e convertido para JSON automaticamente
@RequiredArgsConstructor // injeta o service pelo construtor (campo final)
@RequestMapping(value = "/v1/avalicoesFisicas") // prefixo de todas as rotas da classe. "v1" = versionamento da API
public class AvaliacaoFisicaController {

    private final AvaliacaoFisicaService service;

    // POST /v1/avalicoesFisicas
    // @RequestBody: converte o JSON do corpo da requisicao em AvaliacaoFisicaRequest.
    // @Valid: dispara as validacoes do record (@NotNull...). Se falhar, o service nao e chamado
    // (o status devolvido depende do GlobalExceptionHandler; hoje sai 500, o ideal seria 400).
    // ResponseEntity permite escolher o STATUS HTTP: 201 CREATED e o correto para criacao de recurso.
    @PostMapping
    public ResponseEntity<AvaliacaoFisicaResponse> criarAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisicaRequest request) {
        AvaliacaoFisicaResponse avaliacaoFisicaResponse = service.criarAvaliacaoFisica(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoFisicaResponse);
    }

    // GET /v1/avalicoesFisicas  -> lista tudo (sem paginacao). Responde 200 OK.
    @GetMapping
    public ResponseEntity<List<AvaliacoesFisicasProjection>> getAllAvaliacaoesFisicas() {
        return ResponseEntity.ok(service.getAllAvaliacaoesFisicas());
    }

    // GET /v1/avalicoesFisicas/page/0/size/10
    // @PathVariable: pega o valor que esta na propria URL ({page} e {size}).
    // Obs: o padrao mais comum no Spring e receber um Pageable e usar query string (?page=0&size=10).
    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<AvaliacoesFisicasProjection>> getAllAvaliacaoesFisicasPageable(@PathVariable Integer page, @PathVariable Integer size) {
        return ResponseEntity.ok(service.getAllAvaliacaoesFisicasPageable(page,size));
    }
}
