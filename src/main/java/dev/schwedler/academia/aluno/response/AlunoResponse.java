package dev.schwedler.academia.aluno.response;

import java.time.LocalDate;

// DTO de saida. Repare que NAO expomos avaliacaoFisica nem treinos da entidade:
// o Response mostra so o que a API quer publicar (e evita carregar relacoes LAZY sem necessidade).
public record AlunoResponse(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento
) {
}
