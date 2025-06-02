package com.projeto_final.projeto_puc_go.Controller;

import com.projeto_final.projeto_puc_go.Entity.Evaluated; // Mudança para Evaluated
import com.projeto_final.projeto_puc_go.Service.EvaluatedService; // Mudança para EvaluatedService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluateds") // Nome do endpoint para avaliados
public class EvaluatedController {

    private final EvaluatedService evaluatedService;

    @Autowired
    public EvaluatedController(EvaluatedService evaluatedService) {
        this.evaluatedService = evaluatedService;
    }

    @PostMapping
    public ResponseEntity<Evaluated> createEvaluated(@RequestBody Evaluated evaluated) {
        try {
            Evaluated createdEvaluated = evaluatedService.createEvaluated(evaluated);
            return new ResponseEntity<>(createdEvaluated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Evaluated>> getAllEvaluated() {
        List<Evaluated> evaluateds = evaluatedService.getAllEvaluated();
        return new ResponseEntity<>(evaluateds, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluated> getEvaluatedById(@PathVariable Long id) {
        Optional<Evaluated> evaluated = evaluatedService.getEvaluatedById(id);
        return evaluated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluated> updateEvaluated(@PathVariable Long id, @RequestBody Evaluated evaluatedDetails) {
        Optional<Evaluated> updatedEvaluated = evaluatedService.updateEvaluated(id, evaluatedDetails);
        return updatedEvaluated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvaluated(@PathVariable Long id) {
        boolean isDeleted = evaluatedService.deleteEvaluated(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}