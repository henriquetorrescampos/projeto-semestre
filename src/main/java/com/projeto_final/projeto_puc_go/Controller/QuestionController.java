package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping; // Importação da anotação @GetMapping
import org.springframework.web.bind.annotation.RequestMapping; // Importação da anotação @RequestMapping
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/questions") // Mapeia a requisição para /questions
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping // Mapeia requisições GET para /questions
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
