package dev.schwedler.academia.treino.domain;

import dev.schwedler.academia.aluno.domain.Aluno;
import dev.schwedler.academia.exercicio.domain.Exercicio;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// ===== ENTIDADE Treino (tabela "treinos") =====
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "treinos")
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne: MUITOS treinos pertencem a UM aluno. A FK "aluno_id" fica na tabela treinos.
    // E o lado "dono" da relacao que o Aluno referencia com mappedBy = "aluno".
    // @ManyToOne e EAGER por padrao (carrega o aluno junto). Para evitar isso: @ManyToOne(fetch = FetchType.LAZY).
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    private String nome;
    private String objetivo;

    // Preenchido pelo banco; o JPA so le.
    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    // @ManyToMany: um treino tem varios exercicios E um exercicio aparece em varios treinos.
    // Relacao N para N exige uma TABELA DE LIGACAO (treinos_exercicios), descrita no @JoinTable:
    //  - joinColumns: coluna que aponta para ESTA entidade (treinos_id)
    //  - inverseJoinColumns: coluna que aponta para a OUTRA entidade (exercicios_id)
    // Set (em vez de List) porque nao faz sentido repetir o mesmo exercicio no mesmo treino.
    // Obs: com @Builder, o valor "= new HashSet<>()" e IGNORADO (o builder deixaria null)
    // a menos que o campo tenha @Builder.Default.
    @ManyToMany
    @JoinTable(
            name = "treinos_exercicios",
            joinColumns = @JoinColumn(name = "treinos_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicios_id")
    )
    private Set<Exercicio> exercicios = new HashSet<>();
}
