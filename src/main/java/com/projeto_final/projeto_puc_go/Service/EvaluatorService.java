package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EvaluatorService {
    @Transactional
    Evaluator createEvaluator(Evaluator evaluator);

    @Transactional(readOnly = true)
    List<Evaluator> getAllEvaluators();

    @Transactional(readOnly = true)
    Optional<Evaluator> getEvaluatorById(Long id);

    @Transactional
    Optional<Evaluator> updateEvaluator(Long id, Evaluator evaluatorDetails);

    @Transactional
    boolean deleteEvaluator(Long id);
}