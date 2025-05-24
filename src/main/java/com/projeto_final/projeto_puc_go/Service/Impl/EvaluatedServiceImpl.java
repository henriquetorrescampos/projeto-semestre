package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import com.projeto_final.projeto_puc_go.Repository.EvaluatedRepository;
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluatedServiceImpl implements EvaluatedService {

    private final EvaluatedRepository evaluatedRepository;

    @Autowired
    public EvaluatedServiceImpl(EvaluatedRepository evaluatedRepository) {
        this.evaluatedRepository = evaluatedRepository;
    }

    @Transactional
    @Override
    public Evaluated createEvaluated(Evaluated evaluated) {
        // The creation date (createdAt) is set by default in the entity
        return evaluatedRepository.save(evaluated);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluated> getAllEvaluated() {
        return evaluatedRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Evaluated> getEvaluatedById(Long id) {
        return evaluatedRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Evaluated> updateEvaluated(Long id, Evaluated evaluatedDetails) {
        return evaluatedRepository.findById(id)
                .map(existingEvaluated -> {
                    existingEvaluated.setFeedback(evaluatedDetails.getFeedback());
                    existingEvaluated.setName(evaluatedDetails.getName());
                    // Uncomment if you have updatedAt in the entity
                    // existingEvaluated.setUpdatedAt(LocalDateTime.now());
                    return evaluatedRepository.save(existingEvaluated);
                });
    }

    @Transactional
    @Override
    public boolean deleteEvaluated(Long id) {
        if (evaluatedRepository.existsById(id)) {
            evaluatedRepository.deleteById(id);
            return true;
        }
        return false;
    }
}