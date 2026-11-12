package dev.schwedler.academia.treino.repository;

import dev.schwedler.academia.exercicio.domain.Exercicio;
import dev.schwedler.academia.treino.domain.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITreinoRepository extends JpaRepository<Treino, Long> {

    // Query derivada que retorna boolean: SELECT EXISTS(... WHERE nome = ? AND aluno_id = ?)
    // "AlunoId" navega pela relacao: campo "aluno" da entidade -> propriedade "id" dele.
    // existsBy e mais eficiente que buscar o objeto inteiro quando so queremos saber se existe.
    boolean existsByNomeAndAlunoId(String nome, Long alunoId);
}
