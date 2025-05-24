package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Answer;
import com.projeto_final.projeto_puc_go.Repository.AnswerRepository; // Importe o Repository
import com.projeto_final.projeto_puc_go.Service.AnswerService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException; // Importe a exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Importe LocalDateTime
import java.util.List;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository; // Dependência do AnswerRepository

    @Autowired // Injeta o AnswerRepository
    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Transactional
    @Override
    public Answer createAnswer(Answer answer) {
        // A data de criação (createdAt) já é definida por padrão na entidade via @Builder.Default/@PrePersist
        // Validações adicionais (ex: se question_id e evaluator_id existem) podem ser feitas aqui,
        // mas por enquanto, vamos apenas salvar.
        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Answer> updateAnswer(Long id, Answer answerDetails) {
        return answerRepository.findById(id)
                .map(existingAnswer -> {
                    // Atualiza apenas os campos permitidos
                    existingAnswer.setScore(answerDetails.getScore());
                    existingAnswer.setContent(answerDetails.getContent());
                    // Se tiver updatedAt, atualize-o:
                    existingAnswer.setUpdatedAt(LocalDateTime.now());

                    // Importante: Se Answer tem relacionamento com Question, Evaluator, Evaluated,
                    // e você quer permitir a atualização dessas referências via API,
                    // você precisaria buscar e setar as entidades relacionadas aqui.
                    // Exemplo:
                    // if (answerDetails.getQuestion() != null && answerDetails.getQuestion().getId() != null) {
                    //    Question question = questionRepository.findById(answerDetails.getQuestion().getId())
                    //            .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + answerDetails.getQuestion().getId()));
                    //    existingAnswer.setQuestion(question);
                    // }
                    // E assim por diante para Evaluator e Evaluated.

                    return answerRepository.save(existingAnswer);
                });
    }

    @Transactional
    @Override
    public boolean deleteAnswer(Long id) {
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
            return true;
        }
        // Lança uma exceção se a resposta não for encontrada para deletar
        throw new ResourceNotFoundException("Resposta não encontrada com o ID: " + id);
    }
}