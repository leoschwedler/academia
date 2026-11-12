package dev.schwedler.academia.exercicio.service;

import dev.schwedler.academia.exercicio.domain.Exercicio;
import dev.schwedler.academia.exercicio.mapper.IExercicioMapper;
import dev.schwedler.academia.exercicio.repository.IExercicioRepository;
import dev.schwedler.academia.exercicio.request.ExercicioRequest;
import dev.schwedler.academia.exercicio.response.ExercicioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Camada de servico: regras de negocio + transacoes. Aqui e um CRUD simples, entao so orquestra
// repository e mapper. Em projetos maiores, e aqui que entram as validacoes e regras.
@Service
@RequiredArgsConstructor // injeta repository e mapper pelo construtor (campos final)
public class ExercicioService {

    private final IExercicioRepository exercicioRepository;
    private final IExercicioMapper mapper;

    // readOnly = true: so leitura (menos trabalho para o Hibernate e o banco pode otimizar)
    @Transactional(readOnly = true)
    public List<ExercicioResponse> findAll(){
        // findAll() devolve entidades; convertemos cada uma em Response. Nunca devolva a entidade na API.
        // mapper::toResponse e uma "method reference" (atalho para e -> mapper.toResponse(e)).
        return exercicioRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    // Sem readOnly: este metodo escreve no banco (INSERT) dentro de uma transacao.
    @Transactional
    public ExercicioResponse save(ExercicioRequest request){
        // Request -> entidade -> save() (o retorno ja traz o id gerado) -> Response
        Exercicio domain = exercicioRepository.save(mapper.toDomain(request));
        return mapper.toResponse(domain);
    }

    @Transactional(readOnly = true)
    public List<ExercicioResponse> findByGrupoMuscular(String grupoMuscular){
        // Metodo derivado do nome (ver IExercicioRepository)
        List<Exercicio> domain = exercicioRepository.findAllByGrupoMuscular(grupoMuscular);
        return domain.stream().map(mapper::toResponse).toList();
    }

}
