package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Repository.EvaluatedRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluatorRepository; // Adicionado
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluatedServiceImpl implements EvaluatedService {

    private final EvaluatedRepository evaluatedRepository;
    private final EvaluatorRepository evaluatorRepository; // Adicionado

    @Autowired
    public EvaluatedServiceImpl(EvaluatedRepository evaluatedRepository,
                                EvaluatorRepository evaluatorRepository) {
        this.evaluatedRepository = evaluatedRepository;
        this.evaluatorRepository = evaluatorRepository;
    }

    @Transactional
    @Override
    public Evaluated createEvaluated(Evaluated evaluated) {
        // Garantir que o Evaluator associado existe antes de salvar
        if (evaluated.getEvaluator() != null && evaluated.getEvaluator().getId() != null) {
            Evaluator evaluator = evaluatorRepository.findById(evaluated.getEvaluator().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + evaluated.getEvaluator().getId()));
            evaluated.setEvaluator(evaluator);
        } else {
            throw new IllegalArgumentException("Evaluated must be associated with an existing Evaluator.");
        }
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
                    existingEvaluated.setUpdatedAt(LocalDateTime.now());
                    // Atualizar o Evaluator associado se provided
                    if (evaluatedDetails.getEvaluator() != null && evaluatedDetails.getEvaluator().getId() != null) {
                        Evaluator evaluator = evaluatorRepository.findById(evaluatedDetails.getEvaluator().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluator not found with ID: " + evaluatedDetails.getEvaluator().getId()));
                        existingEvaluated.setEvaluator(evaluator);
                    }
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
        throw new ResourceNotFoundException("Evaluated not found with ID: " + id);
    }
}