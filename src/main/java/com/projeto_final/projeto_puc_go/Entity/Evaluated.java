package com.projeto_final.projeto_puc_go.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evaluated")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"evaluator", "evaluations"})
@EqualsAndHashCode(of = "id")
public class Evaluated {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private String name;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Evaluator evaluator;

    @Builder.Default
    @OneToMany(mappedBy = "evaluated", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // Alterado para EAGER
    private Set<Evaluation> evaluations = new HashSet<>();

    public void addEvaluation(Evaluation evaluation) {
        evaluations.add(evaluation);
        evaluation.setEvaluated(this);
    }

    public void removeEvaluation(Evaluation evaluation) {
        evaluations.remove(evaluation);
        evaluation.setEvaluated(null);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}