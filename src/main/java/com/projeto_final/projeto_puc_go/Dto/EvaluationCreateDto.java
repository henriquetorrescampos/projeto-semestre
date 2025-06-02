package com.projeto_final.projeto_puc_go.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationCreateDto {
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long evaluatedId;
    private Long evaluatorId;
    private String managerType; // Tipo de gestor da avaliação

    // DTO aninhado para características
    private List<CharacteristicInputDto> characteristics;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CharacteristicInputDto {
        private String name;
        private String description;
        private String skillCategory; // Categoria da habilidade (Administrativa, Técnica, Pessoal)

        // DTO aninhado para ratings dentro de cada característica
        private List<RatingInputDto> ratings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RatingInputDto {
        private Integer score;
        private String comment;
        // O avaliador para o rating será o mesmo da avaliação principal
        // Não é necessário um evaluatorId aqui, ele será inferido.
    }
}