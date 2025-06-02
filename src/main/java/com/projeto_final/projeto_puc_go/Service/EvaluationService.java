package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationDetailDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationSummaryDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto; // Importar o novo DTO
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EvaluationService {
    @Transactional
    Evaluation createEvaluation(Evaluation evaluation);

    @Transactional(readOnly = true)
    List<Evaluation> getAllEvaluations();

    @Transactional(readOnly = true)
    Optional<Evaluation> getEvaluationById(Long id);

    @Transactional
    Optional<Evaluation> updateEvaluation(Long id, Evaluation evaluationDetails);

    @Transactional
    boolean deleteEvaluation(Long id);

    @Transactional(readOnly = true)
    List<ManagerTypeDistributionDto> getManagerTypeDistribution();

    @Transactional(readOnly = true)
    List<ManagerTypeAverageScoreDto> getAverageScoreByManagerType();

    @Transactional(readOnly = true)
    List<ManagerTypeSkillAverageDto> getAverageScoreByManagerTypeAndSkillCategory();

    @Transactional(readOnly = true)
    List<EvaluationSummaryDto> getEvaluationSummaries();

    @Transactional(readOnly = true)
    Optional<EvaluationDetailDto> getEvaluationDetail(Long id);

    @Transactional(readOnly = true)
    List<Evaluation> getEvaluationsByEvaluatedId(Long evaluatedId);

    @Transactional(readOnly = true)
    List<Evaluation> getEvaluationsByEvaluatorId(Long evaluatorId);
}