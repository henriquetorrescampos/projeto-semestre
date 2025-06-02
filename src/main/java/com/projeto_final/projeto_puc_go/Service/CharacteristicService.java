package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CharacteristicService {
    @Transactional
    Characteristic createCharacteristic(Characteristic characteristic);

    @Transactional(readOnly = true)
    List<Characteristic> getAllCharacteristics();

    @Transactional(readOnly = true)
    Optional<Characteristic> getCharacteristicById(Long id);

    @Transactional
    Optional<Characteristic> updateCharacteristic(Long id, Characteristic characteristicDetails);

    @Transactional
    boolean deleteCharacteristic(Long id);

    @Transactional(readOnly = true)
    List<Characteristic> getCharacteristicsByEvaluationId(Long evaluationId);
}