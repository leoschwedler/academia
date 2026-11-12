package dev.schwedler.academia.avaliacaoFisica.mapper;

import dev.schwedler.academia.avaliacaoFisica.domain.AvaliacaoFisica;
import dev.schwedler.academia.avaliacaoFisica.request.AvaliacaoFisicaRequest;
import dev.schwedler.academia.avaliacaoFisica.response.AvaliacaoFisicaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

// ===== MAPPER (MapStruct) =====
// Converte um objeto em outro (Request -> Entidade, Entidade -> Response) sem escrever
// getter/setter na mao. Por que? Para NUNCA expor a entidade direto na API e para a
// API poder ter um formato diferente do banco.
//
// Voce so declara a assinatura; o MapStruct GERA a implementacao em tempo de compilacao,
// ligando campos que tem o MESMO NOME (peso -> peso, altura -> altura...).
//
// componentModel = "spring": a classe gerada vira um @Component, entao pode ser
// injetada com @RequiredArgsConstructor / @Autowired como qualquer bean.
@Mapper(componentModel = "spring")
public interface IAvaliacaoFisicaMapper {

    // Cria uma entidade NOVA a partir do request.
    AvaliacaoFisica toDomain(AvaliacaoFisicaRequest request);

    // Converte a entidade salva no formato que devolvemos ao cliente.
    AvaliacaoFisicaResponse toResponse(AvaliacaoFisica domain);

    // @MappingTarget = em vez de criar um objeto novo, PREENCHE o objeto ja existente
    // (avaliacaoFisica) com os dados do request e o devolve. Util para updates.
    AvaliacaoFisica updateToDomain(AvaliacaoFisicaRequest request, @MappingTarget AvaliacaoFisica avaliacaoFisica);
}
