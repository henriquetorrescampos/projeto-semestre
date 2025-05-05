package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Evaluator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//CREATE, READ, UPDATE E DELETE
@Repository
public interface EvaluatorRepository extends JpaRepository<Evaluator, Long> {
}

