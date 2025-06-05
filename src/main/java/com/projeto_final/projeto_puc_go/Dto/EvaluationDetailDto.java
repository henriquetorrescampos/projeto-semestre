package com.projeto_final.projeto_puc_go.Dto;

import com.projeto_final.projeto_puc_go.Entity.ManagerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDetailDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String evaluatedName;
    private String evaluatorName;
    private ManagerType managerType;
    private List<CharacteristicDto> characteristics;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CharacteristicDto {
        private Long id;
        private String name;
        private String description;
        private String skillCategory;
        private List<RatingDto> ratings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingDto {
        private Long id;
        private Integer score;
        private String comment;
        private String evaluatorName;
    }
}