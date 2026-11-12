package dev.schwedler.academia.avaliacaoFisica.repository;

import dev.schwedler.academia.avaliacaoFisica.domain.AvaliacaoFisica;
import dev.schwedler.academia.avaliacaoFisica.dto.AvaliacoesFisicasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

// ===== CAMADA REPOSITORY =====
// Responsavel SO por falar com o banco. Nao tem regra de negocio aqui.
// Por ser uma INTERFACE que estende JpaRepository<Entidade, TipoDoId>, o Spring Data gera
// a implementacao sozinho e ja entrega save(), findById(), findAll(), delete(), etc.
@Repository
public interface IAvaliacaoRepository extends JpaRepository<AvaliacaoFisica, Long> {

    // @NativeQuery = o SQL escrito aqui roda EXATAMENTE como esta, direto no banco
    // (por isso usamos nomes de TABELAS/COLUNAS reais: avaliacoes_fisicas, percentual_gordura).
    // Alternativa: @Query com JPQL, que usa nomes de classes/campos Java e e validada ao subir a app.
    //
    // Por que native + projection? Porque o SELECT junta colunas de duas tabelas e o resultado
    // nao e uma entidade. Entao o retorno e uma projection (interface com getters).
    //
    // Cada coluna tem um ALIAS (ex: "alunos.nome nomeAluno") que precisa bater com o getter da projection.
    //
    // INNER JOIN: so traz alunos QUE TEM avaliacao (alunos sem avaliacao ficam de fora).
    // Se quisesse trazer todos os alunos, mesmo sem avaliacao, seria LEFT JOIN a partir de alunos.
    @NativeQuery(
            """
            SELECT
                alunos.id  idAluno,
                alunos.nome nomeAluno,
                avaliacoes_fisicas.id idAvaliacao,
                avaliacoes_fisicas.peso peso,
                avaliacoes_fisicas.altura altura,
                avaliacoes_fisicas.percentual_gordura percentualGordura,
                avaliacoes_fisicas.criado_em criadoEm
            FROM avaliacoes_fisicas
            INNER JOIN alunos
            ON alunos.avaliacao_fisica_id = avaliacoes_fisicas.id
"""
    )
    List<AvaliacoesFisicasProjection> getAllAvaliacaoesFisicas();


    // Versao PAGINADA: devolve so um "pedaco" dos resultados (ex: 10 por vez) em vez de tudo.
    // O Pageable (pagina + tamanho) e traduzido pelo Spring em LIMIT/OFFSET e anexado ao final do SQL.
    //
    // Por que precisa de countQuery? O Page tambem informa o TOTAL de registros (totalElements,
    // totalPages). Em native query o Spring nao consegue derivar o COUNT sozinho, entao damos o nosso.
    // O COUNT nao precisa de colunas nem de ORDER BY: so conta as linhas do mesmo FROM/JOIN.
    //
    // ORDER BY: sem ordem definida o banco nao garante a sequencia, e a mesma linha poderia aparecer
    // em duas paginas ou sumir. O "id" no final serve de desempate quando o criado_em for igual.
    @NativeQuery(
            value = """
            SELECT
                alunos.id  idAluno,
                alunos.nome nomeAluno,
                avaliacoes_fisicas.id idAvaliacao,
                avaliacoes_fisicas.peso peso,
                avaliacoes_fisicas.altura altura,
                avaliacoes_fisicas.percentual_gordura percentualGordura,
                avaliacoes_fisicas.criado_em criadoEm
            FROM avaliacoes_fisicas
            INNER JOIN alunos
            ON alunos.avaliacao_fisica_id = avaliacoes_fisicas.id
            ORDER BY avaliacoes_fisicas.criado_em DESC, avaliacoes_fisicas.id
""",
            countQuery = """
            SELECT
                COUNT(*)
            FROM avaliacoes_fisicas
            INNER JOIN alunos
            ON alunos.avaliacao_fisica_id = avaliacoes_fisicas.id
"""
    )
    Page<AvaliacoesFisicasProjection> getAllAvaliacaoesFisicasPageable(Pageable pageable);
}
