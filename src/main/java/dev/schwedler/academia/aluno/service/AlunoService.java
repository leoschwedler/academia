package dev.schwedler.academia.aluno.service;

import dev.schwedler.academia.aluno.domain.Aluno;
import dev.schwedler.academia.aluno.mapper.IAlunoMapper;
import dev.schwedler.academia.aluno.repository.IAlunoRepository;
import dev.schwedler.academia.aluno.request.AlunoRequest;
import dev.schwedler.academia.aluno.response.AlunoResponse;
import dev.schwedler.academia.avaliacaoFisica.domain.AvaliacaoFisica;
import dev.schwedler.academia.avaliacaoFisica.mapper.IAvaliacaoFisicaMapper;
import dev.schwedler.academia.avaliacaoFisica.repository.IAvaliacaoRepository;
import dev.schwedler.academia.avaliacaoFisica.response.AvaliacaoFisicaResponse;
import dev.schwedler.academia.exception.BadRequestException;
import dev.schwedler.academia.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Regras de negocio do aluno. Note que este service tambem usa o repository/mapper da avaliacao fisica
// porque algumas operacoes envolvem as duas entidades.
@Service
@RequiredArgsConstructor
public class AlunoService {

    private final IAlunoRepository repository;
    private final IAvaliacaoRepository avaliacaoRepository;
    private final IAlunoMapper mapper;
    private final IAvaliacaoFisicaMapper avaliacaoFisicaMapper;

    @Transactional
    public AlunoResponse save(AlunoRequest request){
        // Regra: email unico. Checamos ANTES para devolver uma mensagem amigavel (400)
        // em vez de deixar estourar o erro de constraint UNIQUE do banco (que viraria 500).
        // A constraint do banco continua sendo a garantia final contra duas requisicoes simultaneas.
        Aluno emailDuplicated = repository.findByEmail(request.email());
        if (emailDuplicated != null){
            throw new BadRequestException("Aluno ja cadastrado com esse email: " + request.email());
        }
        Aluno domain = repository.save(mapper.toDomain(request));
        return mapper.toResponse(domain);
    }

    @Transactional(readOnly = true)
    public AvaliacaoFisicaResponse getAvaliacaoFisicaByUser(Long alunoId){
        // findById devolve Optional; orElseThrow lanca NotFoundException (404) se estiver vazio.
        // A lambda () -> new ... so e executada se for necessario.
        Aluno aluno = repository.findById(alunoId).orElseThrow(() -> new NotFoundException("Aluno com o id:" + alunoId + " nao encontrado"));
        // O aluno pode existir e nao ter avaliacao (coluna avaliacao_fisica_id e opcional).
        if (aluno.getAvaliacaoFisica() == null){
            throw new NotFoundException("Aluno sem avaliacao fisica cadastrada");
        }else {
           AvaliacaoFisica avaliacaoFisica = aluno.getAvaliacaoFisica();
           return avaliacaoFisicaMapper.toResponse(avaliacaoFisica);
        }
    }

    @Transactional
    public void deletarAvaliacao(Long alunoId) {
        Aluno aluno = repository.findById(alunoId).orElseThrow(() -> new NotFoundException("Aluno com o id:" + alunoId + " nao encontrado"));
        if (aluno.getAvaliacaoFisica() == null){
            throw new NotFoundException("Aluno sem avaliacao cadastrada fisica para apagar");
        }else {
            AvaliacaoFisica avaliacaoFisica = aluno.getAvaliacaoFisica();
            // ORDEM IMPORTA: a FK fica em alunos.avaliacao_fisica_id apontando para a avaliacao.
            // Se apagassemos a avaliacao antes de soltar a FK, o banco recusaria (violacao de chave estrangeira).
            // Aqui o aluno e uma entidade "gerenciada" na transacao: o setAvaliacaoFisica(null) e gravado
            // sozinho (dirty checking) no commit, e o Hibernate executa o UPDATE antes do DELETE.
            aluno.setAvaliacaoFisica(null);
            avaliacaoRepository.deleteById(avaliacaoFisica.getId());
        }
    }
}
