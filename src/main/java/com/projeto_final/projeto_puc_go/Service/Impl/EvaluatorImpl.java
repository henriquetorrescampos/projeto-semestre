package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Repository.EvaluatorRepository; // Importe o Repository (certifique-se de criá-lo)
import com.projeto_final.projeto_puc_go.Service.EvaluatorService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException; // Importe a exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Importe LocalDateTime
import java.util.List;
import java.util.Optional;

@Service
public class EvaluatorImpl implements EvaluatorService {

    private final EvaluatorRepository evaluatorRepository;

    @Autowired
    public EvaluatorImpl(EvaluatorRepository evaluatorRepository) {
        this.evaluatorRepository = evaluatorRepository;
    }

    @Transactional
    @Override
    public Evaluator createEvaluator(Evaluator evaluator) {
        // Validações adicionais (ex: email único) podem ser feitas aqui.
        // O @Email na entidade já ajuda, mas se precisar de uma validação de negócio mais profunda, faça aqui.
        return evaluatorRepository.save(evaluator);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluator> getAllEvaluators() {
        return evaluatorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Evaluator> getEvaluatorById(Long id) {
        return evaluatorRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Evaluator> updateEvaluator(Long id, Evaluator evaluatorDetails) {
        return evaluatorRepository.findById(id)
                .map(existingEvaluator -> {
                    existingEvaluator.setName(evaluatorDetails.getName());
                    existingEvaluator.setEmail(evaluatorDetails.getEmail());
                    // Se tiver updatedAt, atualize-o:
                    existingEvaluator.setUpdatedAt(LocalDateTime.now());
                    return evaluatorRepository.save(existingEvaluator);
                });
    }

    @Transactional
    @Override
    public boolean deleteEvaluator(Long id) {
        if (evaluatorRepository.existsById(id)) {
            evaluatorRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Avaliador não encontrado com o ID: " + id);
    }
}