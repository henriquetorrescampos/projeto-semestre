package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    @Transactional
    Question createQuestion(Question question);

    @Transactional(readOnly = true)
    List<Question> getAllQuestions();

    @Transactional(readOnly = true)
    Optional<Question> getQuestionById(Long id);

    @Transactional
    Optional<Question> updateQuestion(
            Long id,
            Question questionDetails
    );

    @Transactional
    boolean deleteQuestion(Long id);
}
