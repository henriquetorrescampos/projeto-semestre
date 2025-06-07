package com.projeto_final.projeto_puc_go.Service;

import com.projeto_final.projeto_puc_go.Entity.Characteristic;
import com.projeto_final.projeto_puc_go.Entity.Evaluation;
import com.projeto_final.projeto_puc_go.Repository.CharacteristicRepository;
import com.projeto_final.projeto_puc_go.Repository.EvaluationRepository;
import com.projeto_final.projeto_puc_go.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public CharacteristicService(CharacteristicRepository characteristicRepository,
                                 EvaluationRepository evaluationRepository) {
        this.characteristicRepository = characteristicRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Transactional
    public Characteristic createCharacteristic(Characteristic characteristic) {
        if (characteristic.getEvaluation() != null && characteristic.getEvaluation().getId() != null) {
            Evaluation evaluation = evaluationRepository.findById(characteristic.getEvaluation().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + characteristic.getEvaluation().getId()));
            characteristic.setEvaluation(evaluation);
        } else {
            throw new IllegalArgumentException("Characteristic must be associated with an existing Evaluation.");
        }
        return characteristicRepository.save(characteristic);
    }

    @Transactional(readOnly = true)
    public List<Characteristic> getAllCharacteristics() {
        return characteristicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Characteristic> getCharacteristicById(Long id) {
        return characteristicRepository.findById(id);
    }

    @Transactional
    public Optional<Characteristic> updateCharacteristic(Long id, Characteristic characteristicDetails) {
        return characteristicRepository.findById(id)
                .map(existingCharacteristic -> {
                    existingCharacteristic.setName(characteristicDetails.getName());
                    existingCharacteristic.setDescription(characteristicDetails.getDescription());
                    existingCharacteristic.setUpdatedAt(LocalDateTime.now());
                    if (characteristicDetails.getEvaluation() != null && characteristicDetails.getEvaluation().getId() != null) {
                        Evaluation evaluation = evaluationRepository.findById(characteristicDetails.getEvaluation().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + characteristicDetails.getEvaluation().getId()));
                        existingCharacteristic.setEvaluation(evaluation);
                    }
                    return characteristicRepository.save(existingCharacteristic);
                });
    }

    @Transactional
    public boolean deleteCharacteristic(Long id) {
        if (characteristicRepository.existsById(id)) {
            characteristicRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFoundException("Characteristic not found with ID: " + id);
    }

    @Transactional(readOnly = true)
    public List<Characteristic> getCharacteristicsByEvaluationId(Long evaluationId) {
        return characteristicRepository.findByEvaluationId(evaluationId);
    }
}