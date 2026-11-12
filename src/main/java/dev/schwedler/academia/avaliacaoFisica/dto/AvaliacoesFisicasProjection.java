package dev.schwedler.academia.avaliacaoFisica.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// ===== PROJECTION (interface) =====
// Usada quando o resultado de uma query NAO e uma entidade (aqui mistura colunas de
// "alunos" e "avaliacoes_fisicas"). O Spring Data cria uma implementacao dessa interface
// automaticamente (um proxy) e preenche os valores a partir das colunas retornadas pelo SQL.
//
// REGRA IMPORTANTE: o nome do getter precisa bater com o ALIAS da coluna no SELECT.
//   getNomeAluno()  <->  "alunos.nome nomeAluno"
// Se o alias for diferente do getter, o valor vem null (sem dar erro!).
public interface AvaliacoesFisicasProjection {

    Long getIdAluno();                  // alias: idAluno
    String getNomeAluno();              // alias: nomeAluno
    Long getIdAvaliacao();              // alias: idAvaliacao
    BigDecimal getPeso();               // alias: peso
    BigDecimal getAltura();             // alias: altura
    BigDecimal getPercentualGordura();  // alias: percentualGordura
    LocalDateTime getCriadoEm();        // alias: criadoEm

}
