package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Rating;
import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Repository.RatingRepository;
import com.projeto_final.projeto_puc_go.Repository.CharacteristicRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatorRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluationRepository;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final CharacteristicRepository characteristicRepository;
    private final EvaluatorRepository evaluatorRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository,
                         CharacteristicRepository characteristicRepository,
                         EvaluatorRepository evaluatorRepository,
                         EvaluationRepository evaluationRepository) {
        this.ratingRepository = ratingRepository;
        this.characteristicRepository = characteristicRepository;
        this.evaluatorRepository = evaluatorRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Transactional
    public Rating createRating(Rating rating) {
        if (rating.getCharacteristic() != null && rating.getCharacteristic().getId() != null) {
            Characteristic characteristic = characteristicRepository.findById(rating.getCharacteristic().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Characteristic not found with ID: " + rating.getCharacteristic().getId()));
            rating.setCharacteristic(characteristic);
        } else {
            throw new IllegalArgumentException("Rating must be associated with an existing Characteristic.");
        }

        if (rating.getEvaluator() != null && rating.getEvaluator().getId() != null) {
            Evaluator evaluator = evaluatorRepository.findById(rating.getEvaluator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + rating.getEvaluator().getId()));
            rating.setEvaluator(evaluator);
        } else {
            throw new IllegalArgumentException("Rating must be associated with an existing Evaluator.");
        }

        if (rating.getEvaluation() != null && rating.getEvaluation().getId() != null) {
            Evaluation evaluation = evaluationRepository.findById(rating.getEvaluation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + rating.getEvaluation().getId()));
            rating.setEvaluation(evaluation);
        } else {
            throw new IllegalArgumentException("Rating must be associated with an existing Evaluation.");
        }

        return ratingRepository.save(rating);
    }

    @Transactional(readOnly = true)
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Transactional
    public Optional<Rating> updateRating(Long id, Rating ratingDetails) {
        return ratingRepository.findById(id)
                .map(existingRating -> {
                    existingRating.setScore(ratingDetails.getScore());
                    existingRating.setComment(ratingDetails.getComment());
                    existingRating.setUpdatedAt(LocalDateTime.now());

                    if (ratingDetails.getCharacteristic() != null && ratingDetails.getCharacteristic().getId() != null) {
                        Characteristic characteristic = characteristicRepository.findById(ratingDetails.getCharacteristic().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Characteristic not found with ID: " + ratingDetails.getCharacteristic().getId()));
                        existingRating.setCharacteristic(characteristic);
                    }
                    if (ratingDetails.getEvaluator() != null && ratingDetails.getEvaluator().getId() != null) {
                        Evaluator evaluator = evaluatorRepository.findById(ratingDetails.getEvaluator().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + ratingDetails.getEvaluator().getId()));
                        existingRating.setEvaluator(evaluator);
                    }
                    if (ratingDetails.getEvaluation() != null && ratingDetails.getEvaluation().getId() != null) {
                        Evaluation evaluation = evaluationRepository.findById(ratingDetails.getEvaluation().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + ratingDetails.getEvaluation().getId()));
                        existingRating.setEvaluation(evaluation);
                    }

                    return ratingRepository.save(existingRating);
                });
    }

    @Transactional
    public boolean deleteRating(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Rating not found with ID: " + id);
    }

    @Transactional(readOnly = true)
    public List<Rating> getRatingsByCharacteristicId(Long characteristicId) {
        return ratingRepository.findByCharacteristicId(characteristicId);
    }

    @Transactional(readOnly = true)
    public List<Rating> getRatingsByEvaluatorId(Long evaluatorId) {
        return ratingRepository.findByEvaluatorId(evaluatorId);
    }

    @Transactional(readOnly = true)
    public List<Rating> getRatingsByEvaluationId(Long evaluationId) {
        return ratingRepository.findByEvaluationId(evaluationId);
    }
}