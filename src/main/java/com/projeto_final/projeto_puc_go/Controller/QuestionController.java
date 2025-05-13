package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/questions") // Define um caminho base para as requisições
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping // Mapeia a requisição GET para "/questions"
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
}
