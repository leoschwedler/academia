package dev.schwedler.academia.exercicio.mapper;

import dev.schwedler.academia.exercicio.domain.Exercicio;
import dev.schwedler.academia.exercicio.request.ExercicioRequest;
import dev.schwedler.academia.exercicio.response.ExercicioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

// MapStruct gera a implementacao em tempo de compilacao, ligando campos de mesmo nome.
// componentModel = "spring" faz a classe gerada virar um bean injetavel.
@Mapper(componentModel = "spring")
public interface IExercicioMapper {

    // Request -> Entidade nova (o id fica null; o banco gera ao salvar)
    Exercicio toDomain(ExercicioRequest request);

    // Entidade -> Response
    ExercicioResponse toResponse(Exercicio domain);

    // @MappingTarget: atualiza o objeto JA EXISTENTE em vez de criar outro (usado em updates)
    Exercicio updateDomain(ExercicioRequest request, @MappingTarget Exercicio domain);
}
