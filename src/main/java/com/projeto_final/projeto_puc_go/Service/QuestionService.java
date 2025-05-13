package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    
    /**
     * Cria uma nova pergunta.
     *
     * @param question A entidade Question a ser salva.
     * @return A entidade Question salva.
     */
    Question createQuestion(Question question);
    
    /**
     * Busca todas as perguntas.
     *
     * @return Uma lista de todas as Questions.
     */
    List<Question> getAllQuestions();
    
    /**
     * Busca uma pergunta pelo seu ID.
     *
     * @param id O ID da pergunta.
     * @return Um Optional contendo a Question se encontrada, ou Optional.empty() caso contrário.
     */
    Optional<Question> getQuestionById(Long id);
    
    /**
     * Atualiza uma pergunta existente.
     *
     * @param id              O ID da pergunta a ser atualizada.
     * @param questionDetails Os novos detalhes da pergunta.
     * @return Um Optional contendo a Question atualizada se encontrada e atualizada, ou Optional.empty() caso contrário.
     */
    Optional<Question> updateQuestion(Long id, Question questionDetails);
    
    /**
     * Deleta uma pergunta pelo seu ID.
     *
     * @param id O ID da pergunta a ser deletada.
     * @return true se a pergunta foi deletada com sucesso, false caso contrário.
     */
    boolean deleteQuestion(Long id);
}
