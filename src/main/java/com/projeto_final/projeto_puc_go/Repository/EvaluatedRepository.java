package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Evaluated; // Mudança para Evaluated
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// conversa com o banco de dados
@Repository
public interface EvaluatedRepository extends JpaRepository<Evaluated, Long> {
    // Não precisamos mais das consultas de DTO aqui, pois elas foram movidas para EvaluationRepository
}