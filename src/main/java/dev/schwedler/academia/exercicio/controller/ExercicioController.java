package dev.schwedler.academia.exercicio.controller;

import dev.schwedler.academia.exercicio.domain.Exercicio;
import dev.schwedler.academia.exercicio.request.ExercicioRequest;
import dev.schwedler.academia.exercicio.response.ExercicioResponse;
import dev.schwedler.academia.exercicio.service.ExercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller REST: so traduz HTTP <-> service. @RestController converte o retorno em JSON.
@RestController
@RequestMapping(value = "/v1/exercicios") // prefixo das rotas; "v1" versiona a API
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService exercicioService;

    // GET /v1/exercicios -> lista todos (200 OK)
    @GetMapping
    public ResponseEntity<List<ExercicioResponse>> findAll(){
        return ResponseEntity.ok(exercicioService.findAll());
    }

    // GET /v1/exercicios/peito -> filtra por grupo muscular.
    // @PathVariable le o trecho {grupoMuscular} da URL. O nome da variavel Java precisa
    // bater com o do template (ou use @PathVariable("grupoMuscular")).
    // Obs: em REST, filtro costuma ser query param (?grupoMuscular=peito), porque o path deve identificar um recurso.
    @GetMapping("/{grupoMuscular}")
    public ResponseEntity<List<ExercicioResponse>> findByGrupoMuscular(@PathVariable String grupoMuscular){
        return ResponseEntity.ok(exercicioService.findByGrupoMuscular(grupoMuscular));
    }

    // POST /v1/exercicios -> cria. @Valid roda as validacoes do record; 201 CREATED = recurso criado.
    @PostMapping
    public ResponseEntity<ExercicioResponse> save(@Valid @RequestBody ExercicioRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(exercicioService.save(request));
    }
}
