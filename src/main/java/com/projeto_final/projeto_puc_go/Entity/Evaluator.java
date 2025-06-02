package com.projeto_final.projeto_puc_go.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evaluator")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"evaluations", "ratings"}) // Adicionado "ratings"
@EqualsAndHashCode(of = "id")
public class Evaluator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Removido o relacionamento @OneToMany com Answer
    // @Builder.Default
    // @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private Set<Answer> answers = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Evaluated> evaluations = new HashSet<>();

    // Novo relacionamento OneToMany com Rating
    @Builder.Default
    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Rating> ratings = new HashSet<>();

    // Removido métodos relacionados a Answer
    // public void addAnswer(Answer answer) {
    //     answers.add(answer);
    //     answer.setEvaluator(this);
    // }

    // public void removeAnswer(Answer answer) {
    //     answers.remove(answer);
    //     answer.setEvaluator(null);
    // }

    public void addEvaluation(Evaluated evaluated) {
        evaluations.add(evaluated);
        evaluated.setEvaluator(this);
    }

    public void removeEvaluation(Evaluated evaluated) {
        evaluations.remove(evaluated);
        evaluated.setEvaluator(null);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setEvaluator(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setEvaluator(null);
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