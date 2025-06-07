package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Entity.ManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT e.managerType, COUNT(e.id) FROM Evaluation e WHERE e.managerType IS NOT NULL GROUP BY e.managerType")
    List<Object[]> countEvaluationsByManagerType();

    @Query("SELECT ev.managerType, AVG(r.score) FROM Evaluation ev JOIN ev.characteristics c JOIN c.ratings r GROUP BY ev.managerType")
    List<Object[]> findAverageScoreByManagerType();

    @Query("SELECT e.managerType, c.skillCategory, AVG(r.score) " +
            "FROM Evaluation e " +
            "JOIN e.characteristics c " +
            "JOIN c.ratings r " +
            "GROUP BY e.managerType, c.skillCategory")
    List<Object[]> findAverageScoreByManagerTypeAndSkillCategory();

    List<Evaluation> findByEvaluatedId(Long evaluatedId);
    List<Evaluation> findByEvaluatorId(Long evaluatorId);

    @Query("SELECT e FROM Evaluation e JOIN FETCH e.evaluated ev JOIN FETCH e.evaluator eva")
    List<Evaluation> findAllWithEvaluatedAndEvaluator();

    @EntityGraph(attributePaths = {"evaluated", "evaluator", "characteristics", "characteristics.ratings"})
    Optional<Evaluation> findById(Long id); // Sobrescreve findById para carregar tudo ansiosamente

}