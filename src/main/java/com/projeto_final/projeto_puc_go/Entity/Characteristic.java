package com.projeto_final.projeto_puc_go.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "characteristic")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"ratings", "evaluation"})
@EqualsAndHashCode(of = "id")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "skill_category")
    private String skillCategory;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Builder.Default
    @OneToMany(mappedBy = "characteristic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // Alterado para EAGER
    private Set<Rating> ratings = new HashSet<>();

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setCharacteristic(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setCharacteristic(null);
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