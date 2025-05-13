package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    
    private final QuestionService questionService;
    
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    /**
     * Endpoint para criar uma nova pergunta.
     * POST /questions/create-question
     *
     * @param question O corpo da requisição contendo os dados da pergunta.
     * @return ResponseEntity com a pergunta criada e status CREATED, ou BAD_REQUEST em caso de erro.
     */
    @PostMapping("/create-question")
    public ResponseEntity<Question> createQuestion(
            @RequestBody Question question
    ) {
        try {
            Question createdQuestion = questionService.createQuestion(question);
            return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Endpoint para buscar todas as perguntas.
     * GET /questions/get-all-questions
     *
     * @return ResponseEntity com a lista de perguntas e status OK.
     */
    @GetMapping("/get-all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    
    /**
     * Endpoint para buscar uma pergunta pelo ID.
     * GET /questions/{id}
     *
     * @param id O ID da pergunta.
     * @return ResponseEntity com a pergunta encontrada e status OK, ou NOT_FOUND caso não exista.
     */
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(
            @PathVariable Long id
    ) {
        Optional<Question> questionOptional = questionService.getQuestionById(id);
        return questionOptional
                .map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * Endpoint para deletar uma pergunta.
     * DELETE /question-delete/{id}
     *
     * @param id O ID da pergunta a ser deletada.
     * @return ResponseEntity com status NO_CONTENT se deletada com sucesso, ou NOT_FOUND caso não exista.
     */
    @DeleteMapping("/question-delete/{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable Long id) {
        boolean isDeleted = questionService.deleteQuestion(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}