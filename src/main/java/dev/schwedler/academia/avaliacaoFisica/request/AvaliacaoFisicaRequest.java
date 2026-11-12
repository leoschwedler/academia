package dev.schwedler.academia.avaliacaoFisica.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// ===== REQUEST (DTO de entrada) =====
// Representa o JSON que o cliente ENVIA no POST. Nao e a entidade: assim o cliente so
// consegue mandar os campos que NOS permitimos (nao manda id nem criadoEm, por exemplo).
//
// "record" (Java 16+) = classe imutavel com construtor, getters (peso(), nome()...),
// equals/hashCode/toString gerados automaticamente. Ideal para DTOs.
//
// Bean Validation: @NotNull so e checado se o controller usar @Valid no parametro.
// Se algum campo vier null, o Spring lanca MethodArgumentNotValidException ANTES de entrar no service.
// O padrao do Spring seria responder 400, mas neste projeto o GlobalExceptionHandler tem um
// handler generico de Exception que a captura e responde 500 (veja o comentario la).
public record AvaliacaoFisicaRequest(
        @NotNull
        String nome,              // nome do aluno: usado no service para achar o aluno (nao vai para a entidade)
        @NotNull
        BigDecimal peso,
        @NotNull
        BigDecimal altura,
        @NotNull
        BigDecimal percentualGordura
) {
}
