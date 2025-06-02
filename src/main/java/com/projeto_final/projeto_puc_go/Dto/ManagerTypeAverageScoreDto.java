package com.projeto_final.projeto_puc_go.Dto;

import com.projeto_final.projeto_puc_go.Entity.ManagerType; // Importar o enum
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerTypeAverageScoreDto {
    private ManagerType managerType; // Alterado para ManagerType enum
    private Double averageScore;
}