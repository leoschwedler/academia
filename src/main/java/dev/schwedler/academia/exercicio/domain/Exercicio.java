package dev.schwedler.academia.exercicio.domain;

import jakarta.persistence.*;
import lombok.*;

// ===== ENTIDADE Exercicio =====
// Representa uma linha da tabela "exercicios". Mesma ideia da AvaliacaoFisica:
// anotacoes do Lombok geram codigo repetitivo e anotacoes do JPA fazem o mapeamento objeto <-> tabela.
@Builder            // Exercicio.builder().nome("Supino").build()
@AllArgsConstructor // necessario para o @Builder funcionar junto com o construtor vazio
@NoArgsConstructor  // o JPA exige um construtor sem argumentos
@Getter
@Setter
@Entity
@Table(name = "exercicios")
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id gerado pelo banco (BIGSERIAL no Postgres)
    private Long id;
    private String nome;
    // camelCase no Java -> snake_case no banco
    @Column(name = "grupo_muscular")
    private String grupoMuscular;
    private String equipamento;
}
