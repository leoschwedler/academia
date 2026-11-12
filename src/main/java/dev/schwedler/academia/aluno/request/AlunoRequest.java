package dev.schwedler.academia.aluno.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

// DTO de entrada para criar um aluno.
// @NotBlank para textos (rejeita null, vazio e so espacos); @NotNull para os demais tipos.
// "message" personaliza o texto do erro de validacao.
// Dica: para email, existe tambem @Email (valida o formato).
// LocalDate e lido do JSON no formato ISO: "2000-01-31".
public record AlunoRequest(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotNull(message = "Data de nascimento nao pode ser nulo")
        LocalDate dataNascimento
) {
}
