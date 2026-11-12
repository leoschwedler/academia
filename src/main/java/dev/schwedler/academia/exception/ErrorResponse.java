package dev.schwedler.academia.exception;

import lombok.Builder;

// Formato padrao de erro devolvido pela API, ex: {"message": "Aluno nao encontrado", "status": 404}.
// Ter UM formato unico facilita a vida de quem consome a API.
// @Builder permite montar assim: ErrorResponse.builder().message("...").status(404).build()
@Builder
public record ErrorResponse(
        String message,
        Integer status
) {
}
