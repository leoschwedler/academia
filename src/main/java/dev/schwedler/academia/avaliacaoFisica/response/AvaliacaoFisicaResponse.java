package dev.schwedler.academia.avaliacaoFisica.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// ===== RESPONSE (DTO de saida) =====
// Formato do JSON que DEVOLVEMOS ao cliente. Separar do Entity evita vazar campos internos
// e evita problemas como serializar relacoes LAZY (LazyInitializationException) ou loops infinitos.
// Aqui incluimos o id e o criadoEm, que o cliente nao envia mas precisa receber.
public record AvaliacaoFisicaResponse(
        Long id,
        BigDecimal peso,
        BigDecimal altura,
        BigDecimal percentualGordura,
        LocalDateTime criadoEm
) {
}
