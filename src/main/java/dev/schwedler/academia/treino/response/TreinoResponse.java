package dev.schwedler.academia.treino.response;

import dev.schwedler.academia.exercicio.domain.Exercicio;

import java.time.LocalDateTime;
import java.util.Set;

// DTO de saida. Em vez de devolver o Aluno e os Exercicios completos (entidades),
// devolvemos so os ids. Isso evita loops de serializacao (Treino -> Aluno -> Treinos -> ...)
// e carregar dados desnecessarios.
public record TreinoResponse(
        Long id,
        Long alunoId,
        String nome,
        String objetivo,
        LocalDateTime criadoEm,
        Set<Long> exerciciosIds
) {
}
