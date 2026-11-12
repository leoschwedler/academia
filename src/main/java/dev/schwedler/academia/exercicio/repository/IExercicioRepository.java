package dev.schwedler.academia.exercicio.repository;

import dev.schwedler.academia.exercicio.domain.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExercicioRepository extends JpaRepository<Exercicio, Long> {

    // QUERY DERIVADA: o Spring Data le o NOME do metodo e gera o SQL sozinho.
    // findAllBy + GrupoMuscular -> SELECT ... FROM exercicios WHERE grupo_muscular = ?
    // O trecho depois de "By" precisa ser exatamente o nome de um campo da entidade (grupoMuscular).
    List<Exercicio> findAllByGrupoMuscular(String grupoMuscular);

    // MESMA consulta, mas com SQL escrito a mao (native). Aqui usamos nomes reais do banco (grupo_muscular).
    // ":grupoMuscular" e um parametro nomeado. Ele e ligado ao parametro do metodo pelo nome
    // (funciona quando o projeto compila com -parameters, o que o Spring Boot ja faz; senao use @Param).
    // Prefira a query derivada quando for simples; use native para SQL que o JPA nao expressa bem.
    @NativeQuery(
            """
            SELECT * From exercicios WHERE grupo_muscular = :grupoMuscular
            """)
    List<Exercicio> findAllByGrupoMuscularNativeQuery(String grupoMuscular);
}
