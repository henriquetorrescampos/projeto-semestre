package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Evaluated;
import com.projeto_final.projeto_puc_go.Repository.EvaluatedRepository; // Importe o Repository
import com.projeto_final.projeto_puc_go.Service.EvaluatedService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException; // Importe a exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Importe LocalDateTime
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
        // Validações adicionais (ex: se answer_id e evaluator_id existem) podem ser feitas aqui
        return evaluatedRepository.save(evaluated);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluated> getAllEvaluateds() {
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
                    existingEvaluated.setName(evaluatedDetails.getName());
                    existingEvaluated.setFeedback(evaluatedDetails.getFeedback());
                    // Se tiver updatedAt, atualize-o:
                    existingEvaluated.setUpdatedAt(LocalDateTime.now());

                    // Se Evaluated tem relacionamento com Answer ou Evaluator,
                    // e você quer permitir a atualização dessas referências,
                    // você precisaria buscar e setar as entidades relacionadas aqui.
                    // Ex: existingEvaluated.setAnswer(answerRepository.findById(evaluatedDetails.getAnswer().getId()).orElseThrow(...));

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
        throw new ResourceNotFoundException("Avaliado não encontrado com o ID: " + id);
    }
}