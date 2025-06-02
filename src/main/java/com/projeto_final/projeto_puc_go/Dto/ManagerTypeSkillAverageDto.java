package com.projeto_final.projeto_puc_go.Dto;

import com.projeto_final.projeto_puc_go.Entity.ManagerType; // Importar o enum ManagerType
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerTypeSkillAverageDto {
    private ManagerType managerType; // Alterado para o tipo enum ManagerType
    private String skillCategory;
    private Double averageScore;

}