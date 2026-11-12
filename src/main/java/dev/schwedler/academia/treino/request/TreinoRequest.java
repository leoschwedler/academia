package dev.schwedler.academia.treino.request;

import dev.schwedler.academia.exercicio.domain.Exercicio;

import java.util.Set;

// DTO de entrada para criar um treino.
// Em vez de receber objetos inteiros (Aluno, Exercicio), a API recebe so os IDs:
// e mais simples para o cliente e o service busca as entidades reais no banco.
// Nao ha anotacoes de validacao aqui; o @Valid do controller nao checa nada. Considere adicionar
// @NotNull em alunoId, @NotBlank em nome/objetivo e @NotEmpty em exerciciosIds
// (sem isso, exerciciosIds null causa NullPointerException no service).
public record TreinoRequest(
        Long alunoId,
        String nome,
        String objetivo,
        Set<Long> exerciciosIds
) {
}
