package dev.schwedler.academia.aluno.repository;

import dev.schwedler.academia.aluno.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAlunoRepository extends JpaRepository<Aluno, Long>{

    // Query derivada: SELECT ... FROM alunos WHERE nome = ?
    // Retorna Aluno "cru": devolve null se nao achar e LANCA ERRO se houver mais de um com o mesmo nome
    // (o nome nao e unico no banco!). Mais seguro: Optional<Aluno>, que obriga a tratar a ausencia.
    Aluno findByNome(String nome);

    // WHERE email = ?  Aqui e seguro porque a coluna email e UNIQUE no banco (ver migration).
    Aluno findByEmail(String email);
}
