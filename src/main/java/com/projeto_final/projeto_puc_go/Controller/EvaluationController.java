package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationDetailDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationSummaryDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto; // Importar o novo DTO
import com.projeto_final.projeto_puc_go.Service.EvaluationService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException; // Importar a exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        try {
            Evaluation createdEvaluation = evaluationService.createEvaluation(evaluation);
            return new ResponseEntity<>(createdEvaluation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Long id) {
        Optional<Evaluation> evaluation = evaluationService.getEvaluationById(id);
        return evaluation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluationDetails) {
        try {
            Optional<Evaluation> updatedEvaluation = evaluationService.updateEvaluation(id, evaluationDetails);
            return updatedEvaluation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvaluation(@PathVariable Long id) {
        try {
            boolean isDeleted = evaluationService.deleteEvaluation(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/distribution-by-manager-type")
    public ResponseEntity<List<ManagerTypeDistributionDto>> getManagerTypeDistribution() {
        List<ManagerTypeDistributionDto> distribution = evaluationService.getManagerTypeDistribution();
        return new ResponseEntity<>(distribution, HttpStatus.OK);
    }

    @GetMapping("/average-score-by-manager-type")
    public ResponseEntity<List<ManagerTypeAverageScoreDto>> getAverageScoreByManagerType() {
        List<ManagerTypeAverageScoreDto> averageScores = evaluationService.getAverageScoreByManagerType();
        return new ResponseEntity<>(averageScores, HttpStatus.OK);
    }

    // Novo endpoint para obter a média de pontuações por tipo de gestor e categoria de habilidade
    @GetMapping("/average-score-by-manager-type-and-skill-category")
    public ResponseEntity<List<ManagerTypeSkillAverageDto>> getAverageScoreByManagerTypeAndSkillCategory() {
        List<ManagerTypeSkillAverageDto> averageScores = evaluationService.getAverageScoreByManagerTypeAndSkillCategory();
        return new ResponseEntity<>(averageScores, HttpStatus.OK);
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<EvaluationSummaryDto>> getEvaluationSummaries() {
        List<EvaluationSummaryDto> summaries = evaluationService.getEvaluationSummaries();
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<EvaluationDetailDto> getEvaluationDetail(@PathVariable Long id) {
        Optional<EvaluationDetailDto> detail = evaluationService.getEvaluationDetail(id);
        return detail.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/by-evaluated/{evaluatedId}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByEvaluatedId(@PathVariable Long evaluatedId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEvaluatedId(evaluatedId);
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }

    @GetMapping("/by-evaluator/{evaluatorId}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByEvaluatorId(@PathVariable Long evaluatorId) {
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEvaluatorId(evaluatorId);
        return new ResponseEntity<>(evaluations, HttpStatus.OK);
    }
}