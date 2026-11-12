package dev.schwedler.academia.treino.mapper;

import dev.schwedler.academia.treino.domain.Treino;
import dev.schwedler.academia.treino.response.TreinoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITreinoMapper {

    // Quando os nomes NAO batem, usamos @Mapping(source = origem, target = destino):
    //  - "aluno.id" (caminho dentro da entidade) -> "alunoId" do Response
    //  - "exercicios" (Set<Exercicio>) -> "exerciciosIds" (Set<Long>)
    // Para a segunda, o MapStruct precisa saber converter Exercicio em Long. Ele nao faz isso
    // sozinho, entao e preciso um metodo auxiliar, ex:
    //   default Long map(Exercicio e) { return e.getId(); }
    // Sem ele, a compilacao falha ou o resultado sai incorreto. Confira se o seu projeto compila.
    @Mapping(source = "aluno.id", target = "alunoId")
    @Mapping(source = "exercicios", target = "exerciciosIds")
    TreinoResponse toResponse(Treino domain);
}
