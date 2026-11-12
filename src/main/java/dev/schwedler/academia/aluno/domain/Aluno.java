package dev.schwedler.academia.aluno.domain;

import dev.schwedler.academia.avaliacaoFisica.domain.AvaliacaoFisica;
import dev.schwedler.academia.treino.domain.Treino;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// ===== ENTIDADE Aluno (tabela "alunos") =====
// Mostra os principais tipos de RELACIONAMENTO do JPA.
@Builder
@Getter
@Setter
@Entity
@Table(name = "alunos")
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @OneToOne: um aluno tem no maximo UMA avaliacao fisica (e vice-versa).
    // @JoinColumn: este lado e o DONO da relacao, ou seja, a coluna FK "avaliacao_fisica_id"
    // fica na tabela alunos. (No banco ela tambem e UNIQUE, garantindo o 1 para 1.)
    // @OneToOne e carregado de forma EAGER por padrao: ao buscar o aluno, a avaliacao vem junto (via JOIN).
    @OneToOne
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacaoFisica avaliacaoFisica;

    private String nome;
    private String email;

    // Preenchido pelo banco (DEFAULT NOW()): o JPA nunca escreve nele, so le.
    @Column(name = "criado_em", insertable = false, updatable = false)
    private LocalDateTime criadoEm;

    // LocalDate = so a data (sem hora), ideal para data de nascimento.
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    // @OneToMany: um aluno tem VARIOS treinos.
    // mappedBy = "aluno": este e o lado INVERSO; quem manda e o campo "aluno" da classe Treino
    // (la esta a FK aluno_id). Sem o mappedBy o JPA criaria uma tabela de ligacao extra.
    // @OneToMany e LAZY por padrao: os treinos so sao buscados quando voce acessa getTreinos().
    @OneToMany(mappedBy = "aluno")
    private List<Treino> treinos;
}
