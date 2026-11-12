package dev.schwedler.academia.aluno.mapper;

import dev.schwedler.academia.aluno.domain.Aluno;
import dev.schwedler.academia.aluno.request.AlunoRequest;
import dev.schwedler.academia.aluno.response.AlunoResponse;
import org.mapstruct.Mapper;

// MapStruct: gera a implementacao em compilacao, copiando campos de mesmo nome.
// Campos da entidade que nao existem no request (id, avaliacaoFisica, treinos, criadoEm)
// ficam null; o MapStruct apenas avisa (warning) sobre "unmapped target properties".
@Mapper(componentModel = "spring")
public interface IAlunoMapper {

    Aluno toDomain(AlunoRequest request);

    AlunoResponse toResponse(Aluno domain);


}
