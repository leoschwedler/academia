-- ===== MIGRATION FLYWAY =====
-- Arquivos em db/migration com o padrao V<versao>__<descricao>.sql sao executados pelo Flyway, em ordem,
-- uma unica vez cada (ele registra o que ja rodou numa tabela de controle).
-- NUNCA edite uma migration ja aplicada: o Flyway detecta a mudanca (checksum) e falha.
-- Para alterar o banco, crie uma nova (V2__...sql).
-- Assim o esquema do banco fica versionado junto com o codigo, em vez de o Hibernate cria-lo sozinho.

CREATE TABLE avaliacoes_fisicas (
  id BIGSERIAL PRIMARY KEY ,                -- BIGSERIAL = bigint auto-incrementavel (casa com Long + IDENTITY)
  peso NUMERIC(5,2)NOT NULL ,               -- NUMERIC(5,2) = ate 5 digitos, 2 depois da virgula (casa com BigDecimal)
  altura NUMERIC(5,2) NOT NULL ,
  percentual_gordura NUMERIC NOT NULL,
  criado_em TIMESTAMP DEFAULT NOW()         -- o banco preenche; por isso a entidade usa insertable = false
);

CREATE TABLE alunos (
    id BIGSERIAL PRIMARY KEY ,
    -- FK opcional (aceita NULL) para a avaliacao. UNIQUE garante o relacionamento 1 para 1:
    -- duas linhas de alunos nao podem apontar para a mesma avaliacao.
    avaliacao_fisica_id BIGINT UNIQUE REFERENCES avaliacoes_fisicas(id),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL ,    -- UNIQUE: impede emails duplicados, mesmo com requisicoes simultaneas
    criado_em TIMESTAMP DEFAULT NOW(),
    data_nascimento DATE
);

CREATE TABLE treinos (
    id BIGSERIAL PRIMARY KEY ,
    nome VARCHAR(255) NOT NULL,
    objetivo VARCHAR(255) NOT NULL,
    aluno_id BIGINT NOT NULL REFERENCES alunos(id),   -- FK do relacionamento N treinos : 1 aluno
    criado_em TIMESTAMP DEFAULT NOW()
);
CREATE TABLE exercicios (
    id BIGSERIAL PRIMARY KEY ,
    nome VARCHAR(255) NOT NULL ,
    grupo_muscular VARCHAR(255) NOT NULL ,
    equipamento VARCHAR(255) NOT NULL
);

-- Tabela de ligacao do relacionamento N para N entre treinos e exercicios
-- (mapeada pelo @JoinTable na entidade Treino).
CREATE TABLE treinos_exercicios(
    id BIGSERIAL PRIMARY KEY ,
    treinos_id BIGINT NOT NULL REFERENCES treinos(id),
    exercicios_id BIGINT NOT NULL REFERENCES exercicios(id)
);
