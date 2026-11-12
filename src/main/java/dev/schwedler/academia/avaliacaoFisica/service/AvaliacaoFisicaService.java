package dev.schwedler.academia.avaliacaoFisica.service;

import dev.schwedler.academia.aluno.domain.Aluno;
import dev.schwedler.academia.aluno.repository.IAlunoRepository;
import dev.schwedler.academia.avaliacaoFisica.domain.AvaliacaoFisica;
import dev.schwedler.academia.avaliacaoFisica.dto.AvaliacoesFisicasProjection;
import dev.schwedler.academia.avaliacaoFisica.mapper.IAvaliacaoFisicaMapper;
import dev.schwedler.academia.avaliacaoFisica.repository.IAvaliacaoRepository;
import dev.schwedler.academia.avaliacaoFisica.request.AvaliacaoFisicaRequest;
import dev.schwedler.academia.avaliacaoFisica.response.AvaliacaoFisicaResponse;
import dev.schwedler.academia.exception.BadRequestException;
import dev.schwedler.academia.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ===== CAMADA SERVICE =====
// Aqui ficam as REGRAS DE NEGOCIO (validacoes, orquestracao entre repositories, transacoes).
// O controller so recebe/devolve HTTP; o repository so acessa o banco; o service decide o que fazer.
@Service // registra a classe como bean do Spring
@RequiredArgsConstructor // Lombok gera um construtor com todos os campos "final" -> injecao de dependencia por construtor
public class AvaliacaoFisicaService {

    // Dependencias injetadas pelo Spring via construtor (forma recomendada: campos final, imutaveis e faceis de testar).
    private final IAvaliacaoRepository avaliacaoRepository;
    private final IAlunoRepository alunoRepository;
    private final IAvaliacaoFisicaMapper mapper;


    // @Transactional: tudo dentro do metodo roda em UMA transacao. Se der qualquer excecao
    // (RuntimeException), o banco faz ROLLBACK e nada fica salvo pela metade.
    // Importante aqui porque salvamos em duas tabelas (avaliacao + aluno).
    @Transactional
    public AvaliacaoFisicaResponse criarAvaliacaoFisica(AvaliacaoFisicaRequest request){
        // 1) Regra: a avaliacao pertence a um aluno, entao ele precisa existir.
        Aluno aluno = alunoRepository.findByNome(request.nome());
        if (aluno == null){
            // NotFoundException e tratada pelo GlobalExceptionHandler e vira um HTTP 404.
            throw new NotFoundException("Aluno com o nome: " + request.nome() + " nao encontrado");
        }
        // 2) Regra: relacao 1 para 1, entao o aluno nao pode ter duas avaliacoes. Vira HTTP 400.
        if (aluno.getAvaliacaoFisica() != null){
            throw new BadRequestException("Aluno com avaliacao Fisica ja cadastrada");
        }else {
            // 3) Cria a entidade vazia e deixa o mapper copiar os dados do request para ela.
            AvaliacaoFisica domain = new AvaliacaoFisica();
            AvaliacaoFisica avaliacaoFisicaAtualizado = mapper.updateToDomain(request, domain);
            // 4) Salva primeiro a avaliacao (para o banco gerar o id)...
            avaliacaoRepository.save(avaliacaoFisicaAtualizado);
            // 5) ...e so depois liga ao aluno (a FK avaliacao_fisica_id esta na tabela alunos) e salva o aluno.
            aluno.setAvaliacaoFisica(avaliacaoFisicaAtualizado);
            alunoRepository.save(aluno);
            // 6) Devolve um Response (DTO), nunca a entidade.
            return mapper.toResponse(avaliacaoFisicaAtualizado);
        }
    }

    // readOnly = true: avisa que o metodo so LE. O Hibernate evita trabalho de "dirty checking"
    // e o banco pode otimizar. Use em todo metodo de consulta.
    @Transactional(readOnly = true)
    public List<AvaliacoesFisicasProjection> getAllAvaliacaoesFisicas(){
        return avaliacaoRepository.getAllAvaliacaoesFisicas();
    }

    // PageRequest.of(pagina, tamanho): a pagina comeca em 0 (0 = primeira pagina).
    // Retorna Page, que traz o conteudo da pagina + metadados (total de itens, total de paginas...).
    @Transactional(readOnly = true)
    public Page<AvaliacoesFisicasProjection> getAllAvaliacaoesFisicasPageable(Integer page, Integer size){
        return avaliacaoRepository.getAllAvaliacaoesFisicasPageable(PageRequest.of(page,size));
    }

//    @Transactional(readOnly = true)
//    public List<ExercicioResponse> findAll(){
//        return exercicioRepository.findAll().stream().map(mapper::toResponse).toList();
//    }
//
//    @Transactional(readOnly = true)
//    public List<ExercicioResponse> findByGrupoMuscular(String grupoMuscular){
//        List<Exercicio> domain = exercicioRepository.findAllByGrupoMuscular(grupoMuscular);
//        return domain.stream().map(mapper::toResponse).toList();
//    }

}
