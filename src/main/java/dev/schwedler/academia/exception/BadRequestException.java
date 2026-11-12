package dev.schwedler.academia.exception;

// Excecao de negocio: "a requisicao e invalida pelas regras do sistema"
// (ex: email duplicado, aluno que ja tem avaliacao). Vira HTTP 400 no GlobalExceptionHandler.
// Ter uma classe por tipo de erro permite tratar cada um de um jeito diferente no handler.
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
