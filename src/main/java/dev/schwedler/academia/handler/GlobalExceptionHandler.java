package dev.schwedler.academia.handler;

import dev.schwedler.academia.exception.BadRequestException;
import dev.schwedler.academia.exception.ErrorResponse;
import dev.schwedler.academia.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @RestControllerAdvice = "conselheiro" global de TODOS os controllers.
// Quando uma excecao escapa de um controller/service, o Spring procura aqui um metodo
// @ExceptionHandler que combine com o tipo da excecao e usa o retorno dele como resposta.
// Vantagem: os services so fazem "throw new ...Exception" e nao se preocupam com HTTP;
// o mapeamento excecao -> status fica centralizado neste unico lugar.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // NotFoundException -> HTTP 404 com o corpo ErrorResponse
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse>  handleNotFoundException(NotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value()) // .value() converte o enum para o numero (404)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // BadRequestException -> HTTP 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse>  handleBadRequestException(BadRequestException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Rede de seguranca: qualquer outra excecao vira HTTP 500.
    // O Spring escolhe o handler MAIS ESPECIFICO, entao os dois acima tem prioridade.
    // ATENCAO: como este handler pega TODA Exception, ele tambem captura erros de validacao do @Valid
    // (MethodArgumentNotValidException), que deveriam ser 400, e os transforma em 500.
    // Para corrigir, crie um @ExceptionHandler(MethodArgumentNotValidException.class) retornando 400.
    // Tambem evite devolver exception.getMessage() de erros inesperados em producao: pode vazar detalhes internos.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>  handleGenericException(Exception exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
