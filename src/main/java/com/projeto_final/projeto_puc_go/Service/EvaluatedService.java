package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EvaluatedService {
    @Transactional
    Evaluated createEvaluated(Evaluated evaluated);

    @Transactional(readOnly = true)
    List<Evaluated> getAllEvaluateds();

    @Transactional(readOnly = true)
    Optional<Evaluated> getEvaluatedById(Long id);

    @Transactional
    Optional<Evaluated> updateEvaluated(Long id, Evaluated evaluatedDetails);

    @Transactional
    boolean deleteEvaluated(Long id);
}