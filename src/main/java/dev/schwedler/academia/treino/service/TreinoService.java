package dev.schwedler.academia.treino.service;

import dev.schwedler.academia.aluno.domain.Aluno;
import dev.schwedler.academia.aluno.repository.IAlunoRepository;
import dev.schwedler.academia.exception.BadRequestException;
import dev.schwedler.academia.exception.NotFoundException;
import dev.schwedler.academia.exercicio.domain.Exercicio;
import dev.schwedler.academia.exercicio.repository.IExercicioRepository;
import dev.schwedler.academia.treino.domain.Treino;
import dev.schwedler.academia.treino.mapper.ITreinoMapper;
import dev.schwedler.academia.treino.repository.ITreinoRepository;
import dev.schwedler.academia.treino.request.TreinoRequest;
import dev.schwedler.academia.treino.response.TreinoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final ITreinoRepository treinoRepository;
    private final IAlunoRepository alunoRepository;
    private final IExercicioRepository exercicioRepository;
    private final ITreinoMapper mapper;

    // Falta @Transactional aqui. Este metodo faz varias leituras e depois grava. Com a transacao,
    // tudo seria atomico (rollback se algo falhar) e o Hibernate manteria o estado consistente.
    // Funciona sem, pois o save() do repository abre uma transacao propria, mas e uma boa pratica incluir.
    public TreinoResponse saveTreino(TreinoRequest request) {
        Set<Exercicio> exercicios = new HashSet<>();
        // 1) O aluno precisa existir (senao 404).
        Aluno aluno = alunoRepository.findById(request.alunoId()).orElseThrow(() -> new NotFoundException("Aluno com o id: " + request.alunoId() + " nao foi encontrado"));
        // 2) Regra: o aluno nao pode ter dois treinos com o mesmo nome (senao 400).
        if(treinoRepository.existsByNomeAndAlunoId(request.nome(), request.alunoId())){
            throw new BadRequestException("Ja existe um treino com esse nome para o aluno");
        }else {
            // 3) Para cada id recebido, busca o Exercicio real. Se algum nao existir, aborta com 400.
            // (Isso faz uma consulta por id; findAllById(ids) faria uma so, mais eficiente.)
            request.exerciciosIds().forEach( id -> {
                Exercicio exercicio = exercicioRepository.findById(id).orElseThrow(() -> new BadRequestException("Exercicio nao encontrado"));
                exercicios.add(exercicio);
            });
            // 4) Monta a entidade com o Builder (nao ha mapper de request -> entidade aqui
            // porque precisamos converter ids em objetos) e salva.
            Treino treino = Treino.builder()
                    .aluno(aluno)
                    .nome(request.nome())
                    .objetivo(request.objetivo())
                    .exercicios(exercicios)
                    .build();
            treinoRepository.save(treino);
            // 5) Devolve o Response. Obs: criadoEm vem null aqui, pois e preenchido pelo banco
            // e a entidade nao foi recarregada depois do INSERT.
            return mapper.toResponse(treino);
        }
    }
}
