package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Question;
import com.projeto_final.projeto_puc_go.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    }
}
