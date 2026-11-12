package dev.schwedler.academia.exercicio.response;

// DTO de saida: o que devolvemos ao cliente. Inclui o id gerado pelo banco.
// Records sao imutaveis; os valores so sao definidos no construtor (o MapStruct chama ele).
public record ExercicioResponse(
        Long id,
        String nome,
        String grupoMuscular,
        String equipamento
) {
}
