package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Rating;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    @Transactional
    Rating createRating(Rating rating);

    @Transactional(readOnly = true)
    List<Rating> getAllRatings();

    @Transactional(readOnly = true)
    Optional<Rating> getRatingById(Long id);

    @Transactional
    Optional<Rating> updateRating(Long id, Rating ratingDetails);

    @Transactional
    boolean deleteRating(Long id);

    @Transactional(readOnly = true)
    List<Rating> getRatingsByCharacteristicId(Long characteristicId);

    @Transactional(readOnly = true)
    List<Rating> getRatingsByEvaluatorId(Long evaluatorId);

    @Transactional(readOnly = true)
    List<Rating> getRatingsByEvaluationId(Long evaluationId);
}