package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import com.projeto_final.projeto_puc_go.Service.EvaluatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluators") // Nome do endpoint para avaliadores
public class EvaluatorController {

    private final EvaluatorService evaluatorService;

    @Autowired
    public EvaluatorController(EvaluatorService evaluatorService) {
        this.evaluatorService = evaluatorService;
    }

    @PostMapping
    public ResponseEntity<Evaluator> createEvaluator(@RequestBody Evaluator evaluator) {
        try {
            Evaluator createdEvaluator = evaluatorService.createEvaluator(evaluator);
            return new ResponseEntity<>(createdEvaluator, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Evaluator>> getAllEvaluators() {
        List<Evaluator> evaluators = evaluatorService.getAllEvaluators();
        return new ResponseEntity<>(evaluators, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluator> getEvaluatorById(@PathVariable Long id) {
        Optional<Evaluator> evaluator = evaluatorService.getEvaluatorById(id);
        return evaluator.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluator> updateEvaluator(@PathVariable Long id, @RequestBody Evaluator evaluatorDetails) {
        Optional<Evaluator> updatedEvaluator = evaluatorService.updateEvaluator(id, evaluatorDetails);
        return updatedEvaluator.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvaluator(@PathVariable Long id) {
        boolean isDeleted = evaluatorService.deleteEvaluator(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}