package dev.schwedler.academia.avaliacaoFisica.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// ===== CAMADA DOMAIN (ENTIDADE) =====
// A entidade e a classe Java que representa UMA LINHA de uma tabela do banco.
// O JPA/Hibernate le essas anotacoes e sabe como converter objeto <-> linha (ORM).

// Lombok: gera codigo repetitivo em tempo de compilacao, assim nao precisamos escrever na mao.
@Builder          // permite criar objetos assim: AvaliacaoFisica.builder().peso(...).build()
@Getter           // gera todos os getX()
@Setter           // gera todos os setX()
@NoArgsConstructor  // construtor vazio: o JPA EXIGE um (ele cria o objeto por reflexao e depois preenche os campos)
@AllArgsConstructor // construtor com todos os campos: o @Builder precisa dele para funcionar junto com o @NoArgsConstructor

@Entity // diz ao JPA: "esta classe e uma tabela gerenciada por voce"
@Table(name = "avaliacoes_fisicas") // nome real da tabela no banco (sem isso o JPA usaria o nome da classe)
public class AvaliacaoFisica {

    @Id // marca a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // IDENTITY = o proprio banco gera o id (AUTO_INCREMENT / SERIAL). Por isso nao setamos o id no insert.
    private Long id;

    // Sem @Column o JPA usa o nome do campo como nome da coluna (peso -> peso).
    // BigDecimal e o tipo correto para medidas/dinheiro: double/float tem erro de arredondamento.
    private BigDecimal peso;
    private BigDecimal altura;

    // No Java usamos camelCase, no banco usamos snake_case. O @Column faz a "ponte" entre os dois.
    @Column(name = "percentual_gordura")
    private BigDecimal percentualGordura;

    // insertable = false / updatable = false: o JPA NUNCA envia esse campo no INSERT/UPDATE.
    // Quem preenche e o banco (ex: DEFAULT CURRENT_TIMESTAMP). Nos so LEMOS o valor depois.
    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;
}
