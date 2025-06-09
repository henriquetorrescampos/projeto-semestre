package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import com.projeto_final.projeto_puc_go.Entity.Rating;
import com.projeto_final.projeto_puc_go.Entity.ManagerType;
import com.projeto_final.projeto_puc_go.Repository.EvaluationRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatedRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatorRepository;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EvaluatedRepository evaluatedRepository;
    private final EvaluatorRepository evaluatorRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository,
                             EvaluatedRepository evaluatedRepository,
                             EvaluatorRepository evaluatorRepository) {
        this.evaluationRepository = evaluationRepository;
        this.evaluatedRepository = evaluatedRepository;
        this.evaluatorRepository = evaluatorRepository;
    }

    @Transactional
    public Evaluation createEvaluation(Evaluation evaluation) {
        if (evaluation.getEvaluated() != null && evaluation.getEvaluated().getId() != null) {
            evaluatedRepository.findById(evaluation.getEvaluated().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluated not found with ID: " + evaluation.getEvaluated().getId()));
        } else {
            throw new IllegalArgumentException("Evaluated ID must not be null.");
        }

        if (evaluation.getEvaluator() != null && evaluation.getEvaluator().getId() != null) {
            evaluatorRepository.findById(evaluation.getEvaluator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + evaluation.getEvaluator().getId()));
        } else {
            throw new IllegalArgumentException("Evaluator ID must not be null.");
        }

        evaluation.setCreatedAt(LocalDateTime.now());

        evaluation.getCharacteristics().forEach(characteristic -> {
            characteristic.setEvaluation(evaluation);
            characteristic.getRatings().forEach(rating -> {
                rating.setCharacteristic(characteristic);
                rating.setEvaluation(evaluation);
            });
        });

        return evaluationRepository.save(evaluation);
    }

    @Transactional(readOnly = true)
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAllWithEvaluatedAndEvaluator();
    }

    @Transactional(readOnly = true)
    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Transactional
    public Optional<Evaluation> updateEvaluation(Long id, Evaluation evaluationDetails) {
        return evaluationRepository.findById(id)
                .map(existingEvaluation -> {
                    existingEvaluation.setTitle(evaluationDetails.getTitle());
                    existingEvaluation.setDescription(evaluationDetails.getDescription());
                    existingEvaluation.setStartDate(evaluationDetails.getStartDate());
                    existingEvaluation.setEndDate(evaluationDetails.getEndDate());
                    existingEvaluation.setUpdatedAt(LocalDateTime.now());
                    existingEvaluation.setManagerType(evaluationDetails.getManagerType());

                    if (evaluationDetails.getEvaluated() != null && evaluationDetails.getEvaluated().getId() != null) {
                        evaluatedRepository.findById(evaluationDetails.getEvaluated().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluated not found with ID: " + evaluationDetails.getEvaluated().getId()));
                        existingEvaluation.setEvaluated(evaluationDetails.getEvaluated());
                    }
                    if (evaluationDetails.getEvaluator() != null && evaluationDetails.getEvaluator().getId() != null) {
                        evaluatorRepository.findById(evaluationDetails.getEvaluator().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + evaluationDetails.getEvaluator().getId()));
                        existingEvaluation.setEvaluator(evaluationDetails.getEvaluator());
                    }

                    return evaluationRepository.save(existingEvaluation);
                });
    }

    @Transactional
    public boolean deleteEvaluation(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Evaluation not found with ID: " + id);
    }

    @Transactional(readOnly = true)
    public List<Object[]> getManagerTypeDistribution() {
        return evaluationRepository.countEvaluationsByManagerType();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getAverageScoreByManagerType() {
        return evaluationRepository.findAverageScoreByManagerType();
    }

    @Transactional(readOnly = true)
    public List<Object[]> getAverageScoreByManagerTypeAndSkillCategory() {
        return evaluationRepository.findAverageScoreByManagerTypeAndSkillCategory();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getEvaluationSummaries() {
        return evaluationRepository.findAllWithEvaluatedAndEvaluator().stream()
                .map(evaluation -> {
                    Double averageScore = evaluation.getCharacteristics().stream()
                            .flatMap(c -> c.getRatings().stream())
                            .mapToDouble(Rating::getScore)
                            .average()
                            .orElse(0.0);
                    Map<String, Object> summary = new HashMap<>();
                    summary.put("id", evaluation.getId());
                    summary.put("title", evaluation.getTitle());
                    summary.put("startDate", evaluation.getStartDate());
                    summary.put("endDate", evaluation.getEndDate());
                    summary.put("evaluatedName", evaluation.getEvaluated() != null ? evaluation.getEvaluated().getName() : "N/A");
                    summary.put("evaluatorName", evaluation.getEvaluator() != null ? evaluation.getEvaluator().getName() : "N/A");
                    summary.put("averageScore", averageScore);
                    return summary;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Evaluation> getEvaluationsByEvaluatedId(Long evaluatedId) {
        return evaluationRepository.findByEvaluatedId(evaluatedId);
    }

    @Transactional(readOnly = true)
    public List<Evaluation> getEvaluationsByEvaluatorId(Long evaluatorId) {
        return evaluationRepository.findByEvaluatorId(evaluatorId);
    }
}