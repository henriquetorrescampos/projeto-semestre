package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Entity.ManagerType; // Importar o enum
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto; // Importar o novo DTO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT new com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto(e.managerType, COUNT(e.id)) FROM Evaluation e WHERE e.managerType IS NOT NULL GROUP BY e.managerType")
    List<ManagerTypeDistributionDto> countEvaluationsByManagerType();

    @Query("SELECT new com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto(ev.managerType, AVG(r.score)) FROM Evaluation ev JOIN ev.characteristics c JOIN c.ratings r GROUP BY ev.managerType")
    List<ManagerTypeAverageScoreDto> findAverageScoreByManagerType();

    // Novo método para obter a média de pontuações por tipo de gestor e categoria de habilidade
    @Query("SELECT new com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto(e.managerType, c.skillCategory, AVG(r.score)) " +
            "FROM Evaluation e " +
            "JOIN e.characteristics c " +
            "JOIN c.ratings r " +
            "GROUP BY e.managerType, c.skillCategory")
    List<ManagerTypeSkillAverageDto> findAverageScoreByManagerTypeAndSkillCategory();

    List<Evaluation> findByEvaluatedId(Long evaluatedId);
    List<Evaluation> findByEvaluatorId(Long evaluatorId);
}