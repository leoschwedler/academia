package dev.schwedler.academia.exception;

// Excecao de negocio: "o recurso procurado nao existe".
// Estende RuntimeException (unchecked): nao precisa de throws/try-catch, e o @Transactional
// faz rollback automatico em RuntimeException.
// O GlobalExceptionHandler captura esta excecao e a transforma em HTTP 404.
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message); // a mensagem vai para o JSON de erro
    }
}
