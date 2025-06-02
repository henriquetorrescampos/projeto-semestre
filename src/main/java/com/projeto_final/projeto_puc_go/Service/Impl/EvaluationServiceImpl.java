package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import com.projeto_final.projeto_puc_go.Entity.Rating;
import com.projeto_final.projeto_puc_go.Entity.ManagerType; // Importar o enum
import com.projeto_final.projeto_puc_go.Repository.EvaluationRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatedRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatorRepository;
import com.projeto_final.projeto_puc_go.Service.EvaluationService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeDistributionDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeAverageScoreDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationDetailDto;
import com.projeto_final.projeto_puc_go.Dto.EvaluationSummaryDto;
import com.projeto_final.projeto_puc_go.Dto.ManagerTypeSkillAverageDto; // Importar o novo DTO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EvaluatedRepository evaluatedRepository; // Adicionado
    private final EvaluatorRepository evaluatorRepository; // Adicionado

    @Autowired
    public EvaluationServiceImpl(EvaluationRepository evaluationRepository,
                                 EvaluatedRepository evaluatedRepository,
                                 EvaluatorRepository evaluatorRepository) {
        this.evaluationRepository = evaluationRepository;
        this.evaluatedRepository = evaluatedRepository;
        this.evaluatorRepository = evaluatorRepository;
    }

    @Transactional
    @Override
    public Evaluation createEvaluation(Evaluation evaluation) {
        // Garantir que Evaluated e Evaluator existem antes de salvar a Evaluation
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

        // Setar a data de criação
        evaluation.setCreatedAt(LocalDateTime.now());

        // Associar características e ratings à avaliação antes de salvar
        evaluation.getCharacteristics().forEach(characteristic -> {
            characteristic.setEvaluation(evaluation);
            characteristic.getRatings().forEach(rating -> {
                rating.setCharacteristic(characteristic);
                rating.setEvaluation(evaluation); // Associar o rating à avaliação
            });
        });

        return evaluationRepository.save(evaluation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Evaluation> getEvaluationById(Long id) {
        return evaluationRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Evaluation> updateEvaluation(Long id, Evaluation evaluationDetails) {
        return evaluationRepository.findById(id)
                .map(existingEvaluation -> {
                    existingEvaluation.setTitle(evaluationDetails.getTitle());
                    existingEvaluation.setDescription(evaluationDetails.getDescription());
                    existingEvaluation.setStartDate(evaluationDetails.getStartDate());
                    existingEvaluation.setEndDate(evaluationDetails.getEndDate());
                    existingEvaluation.setUpdatedAt(LocalDateTime.now());
                    existingEvaluation.setManagerType(evaluationDetails.getManagerType()); // Atualiza o tipo de gestor

                    // Lógica para atualizar Evaluated e Evaluator se necessário
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

                    // Lógica para atualizar características e ratings: isso é mais complexo e pode exigir um service específico para características e ratings
                    // Por simplicidade, assumimos que características e ratings serão atualizados separadamente ou através de uma lógica mais elaborada aqui.
                    // Para este exemplo, não estamos atualizando características e ratings aninhados no update da avaliação principal.

                    return evaluationRepository.save(existingEvaluation);
                });
    }

    @Transactional
    @Override
    public boolean deleteEvaluation(Long id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Evaluation not found with ID: " + id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ManagerTypeDistributionDto> getManagerTypeDistribution() {
        return evaluationRepository.countEvaluationsByManagerType();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ManagerTypeAverageScoreDto> getAverageScoreByManagerType() {
        return evaluationRepository.findAverageScoreByManagerType();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ManagerTypeSkillAverageDto> getAverageScoreByManagerTypeAndSkillCategory() {
        return evaluationRepository.findAverageScoreByManagerTypeAndSkillCategory();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EvaluationSummaryDto> getEvaluationSummaries() {
        return evaluationRepository.findAll().stream()
                .map(evaluation -> {
                    Double averageScore = evaluation.getCharacteristics().stream()
                            .flatMap(c -> c.getRatings().stream())
                            .mapToDouble(Rating::getScore)
                            .average()
                            .orElse(0.0);
                    return new EvaluationSummaryDto(
                            evaluation.getId(),
                            evaluation.getTitle(),
                            evaluation.getStartDate(),
                            evaluation.getEndDate(),
                            evaluation.getEvaluated() != null ? evaluation.getEvaluated().getName() : "N/A",
                            evaluation.getEvaluator() != null ? evaluation.getEvaluator().getName() : "N/A",
                            averageScore
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<EvaluationDetailDto> getEvaluationDetail(Long id) {
        return evaluationRepository.findById(id)
                .map(evaluation -> {
                    EvaluationDetailDto detailDto = new EvaluationDetailDto();
                    detailDto.setId(evaluation.getId());
                    detailDto.setTitle(evaluation.getTitle());
                    detailDto.setDescription(evaluation.getDescription());
                    detailDto.setStartDate(evaluation.getStartDate());
                    detailDto.setEndDate(evaluation.getEndDate());
                    detailDto.setEvaluatedName(evaluation.getEvaluated() != null ? evaluation.getEvaluated().getName() : "N/A");
                    detailDto.setEvaluatorName(evaluation.getEvaluator() != null ? evaluation.getEvaluator().getName() : "N/A");
                    detailDto.setManagerType(evaluation.getManagerType()); // Usando o enum

                    detailDto.setCharacteristics(evaluation.getCharacteristics().stream()
                            .map(characteristic -> {
                                EvaluationDetailDto.CharacteristicDto charDto = new EvaluationDetailDto.CharacteristicDto();
                                charDto.setId(characteristic.getId());
                                charDto.setName(characteristic.getName());
                                charDto.setDescription(characteristic.getDescription());
                                // Adicionando skillCategory ao CharacteristicDto se você tiver um no EvaluationDetailDto.CharacteristicDto
                                // charDto.setSkillCategory(characteristic.getSkillCategory());
                                charDto.setRatings(characteristic.getRatings().stream()
                                        .map(rating -> new EvaluationDetailDto.RatingDto(rating.getId(), rating.getScore(), rating.getComment(), rating.getEvaluator().getName()))
                                        .collect(Collectors.toList()));
                                return charDto;
                            })
                            .collect(Collectors.toList()));
                    return detailDto;
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluation> getEvaluationsByEvaluatedId(Long evaluatedId) {
        return evaluationRepository.findByEvaluatedId(evaluatedId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluation> getEvaluationsByEvaluatorId(Long evaluatorId) {
        return evaluationRepository.findByEvaluatorId(evaluatorId);
    }
}