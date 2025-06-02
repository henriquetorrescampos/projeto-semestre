package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Answer;
import org.springframework.transaction.annotation.Transactional; // Importe esta anotação

import java.util.List;
import java.util.Optional; // Importe Optional

public interface AnswerService {
    @Transactional // Indica que este método deve ser executado dentro de uma transação
    Answer createAnswer(Answer answer);

    @Transactional(readOnly = true) // Indica que esta transação é apenas para leitura
    List<Answer> getAllAnswers();

    @Transactional(readOnly = true)
    Optional<Answer> getAnswerById(Long id); // Retorna Optional para indicar que pode não encontrar

    @Transactional
    Optional<Answer> updateAnswer(Long id, Answer answerDetails); // Retorna Optional

    @Transactional
    boolean deleteAnswer(Long id); // Retorna boolean para indicar sucesso/falha
}