package dev.schwedler.academia.aluno.controller.controller;

import dev.schwedler.academia.aluno.request.AlunoRequest;
import dev.schwedler.academia.aluno.response.AlunoResponse;
import dev.schwedler.academia.aluno.service.AlunoService;
import dev.schwedler.academia.avaliacaoFisica.response.AvaliacaoFisicaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller do recurso "alunos". Delega tudo ao service.
// (O pacote "controller.controller" esta duplicado; um unico "controller" bastaria.)
@RestController
@RequestMapping(value = "/v1/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService service;

    // POST /v1/alunos -> cadastra um aluno. 201 CREATED.
    @PostMapping
    public ResponseEntity<AlunoResponse> save(@Valid @RequestBody AlunoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }


    // GET /v1/alunos/{alunoId} -> devolve a AVALIACAO FISICA do aluno (nao o aluno).
    // Boa pratica: expor como /v1/alunos/{id}/avaliacao-fisica, pois o recurso retornado nao e o aluno.
    // Obs: o metodo e "private". O Spring ate consegue registrar, mas o padrao e deixar handlers "public".
    @GetMapping("/{alunoId}")
    private ResponseEntity<AvaliacaoFisicaResponse> getAvaliacaoFisicaByUser(@PathVariable Long alunoId){
       return ResponseEntity.ok(service.getAvaliacaoFisicaByUser(alunoId));
    }

    // DELETE /v1/alunos/{alunoId} -> apaga a avaliacao fisica do aluno (o aluno continua existindo).
    // Retorno void = HTTP 200 sem corpo. O mais correto para delete seria 204 No Content:
    //   @ResponseStatus(HttpStatus.NO_CONTENT)  ou  ResponseEntity.noContent().build()
    @DeleteMapping(value = "/{alunoId}")
    public void deletarAvaliacao(@PathVariable Long alunoId) {
       service.deletarAvaliacao(alunoId);
    }
}
