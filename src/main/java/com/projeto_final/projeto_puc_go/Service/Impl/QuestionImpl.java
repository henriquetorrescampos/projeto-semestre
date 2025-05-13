package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Repository.QuestionRepository;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// implementa as regras de negócio, lógicas
@Service
public class QuestionImpl implements QuestionService {
    
    private final QuestionRepository questionRepository;
    
    @Autowired
    public QuestionImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    
    @Transactional
    @Override
    public Question createQuestion(Question question) {
        // A data de criação (createdAt) já é definida por padrão na entidade
        // Se necessário, validações adicionais podem ser inseridas aqui
        return questionRepository.save(question);
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }
    
    @Transactional
    @Override
    public Optional<Question> updateQuestion(
            Long id,
            Question questionDetails
    ) {
        return questionRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setContent(questionDetails.getContent());
                    // A linha abaixo foi removida, pois 'updatedAt' não existe mais na entidade Question
                    // existingQuestion.setUpdatedAt(LocalDateTime.now());
                    
                    // Se você precisar atualizar outros campos da Question (além do content),
                    // como por exemplo a coleção 'answers', faça-o aqui.
                    // Exemplo (CUIDADO: isso substituiria todas as respostas existentes):
                    // if (questionDetails.getAnswers() != null) {
                    //     existingQuestion.getAnswers().clear();
                    //     existingQuestion.getAnswers().addAll(questionDetails.getAnswers());
                    //     // Certifique-se que o lado 'many' (Answer) tem a referência correta para 'question'
                    //     // existingQuestion.getAnswers().forEach(answer -> answer.setQuestion(existingQuestion));
                    // }
                    return questionRepository.save(existingQuestion);
                });
    }
    
    @Transactional
    @Override
    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}