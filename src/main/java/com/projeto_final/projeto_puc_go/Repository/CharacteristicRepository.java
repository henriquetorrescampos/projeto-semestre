package com.projeto_final.projeto_puc_go.Repository;

import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    List<Characteristic> findByEvaluationId(Long evaluationId);
}