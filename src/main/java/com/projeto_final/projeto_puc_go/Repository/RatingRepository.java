package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCharacteristicId(Long characteristicId);
    List<Rating> findByEvaluatorId(Long evaluatorId);
    List<Rating> findByEvaluationId(Long evaluationId);
}