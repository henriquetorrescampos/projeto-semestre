package com.projeto_final.projeto_puc_go.Service.Impl;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Repository.QuestionRepository;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException; // Importe a exceção
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Importe LocalDateTime se for usar updatedAt
import java.util.List;
import java.util.Optional;

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
    public Optional<Question> updateQuestion(Long id, Question questionDetails) {
        return questionRepository.findById(id)
                .map(existingQuestion -> {
                    existingQuestion.setContent(questionDetails.getContent());
                    // Se você tem um campo updatedAt na Question, atualize-o aqui
                    // existingQuestion.setUpdatedAt(LocalDateTime.now());
                    return questionRepository.save(existingQuestion);
                }); // Não usa orElseThrow aqui, o chamador (Controller/Console) decide lançar
    }

    @Transactional
    @Override
    public boolean deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        // Lança uma exceção se a questão não for encontrada para deletar
        throw new ResourceNotFoundException("Questão não encontrada com o ID: " + id);
    }
}