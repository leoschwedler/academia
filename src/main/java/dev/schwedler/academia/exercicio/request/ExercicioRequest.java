package dev.schwedler.academia.exercicio.request;

import jakarta.validation.constraints.NotBlank;

// DTO de entrada (JSON enviado no POST). Nao tem id: quem gera e o banco.
// @NotBlank e mais forte que @NotNull para texto: rejeita null, "" (vazio) e "   " (so espacos).
// Use @NotNull para tipos que nao sao String (numeros, datas...), como no AlunoRequest.
// A validacao so acontece se o controller usar @Valid.
public record ExercicioRequest (
        @NotBlank
        String nome,
        @NotBlank
        String grupoMuscular,
        @NotBlank
        String equipamento
){
}
