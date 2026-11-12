package dev.schwedler.academia.treino.controller;

import dev.schwedler.academia.treino.request.TreinoRequest;
import dev.schwedler.academia.treino.response.TreinoResponse;
import dev.schwedler.academia.treino.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/treinos")
public class TreinoController {

    private final TreinoService service;

    // POST /v1/treinos -> cria um treino para um aluno com uma lista de exercicios (201 CREATED).
    // Obs: o metodo esta "private"; o padrao e "public" para handlers de controller.
    @PostMapping()
    private ResponseEntity<TreinoResponse> saveTreino(@Valid @RequestBody TreinoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTreino(request));
    }
}
