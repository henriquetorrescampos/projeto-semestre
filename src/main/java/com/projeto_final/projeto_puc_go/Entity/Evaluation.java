package com.projeto_final.projeto_puc_go.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = {"characteristics", "evaluated", "evaluator"})
@EqualsAndHashCode(of = "id")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "manager_type")
    private ManagerType managerType;

    @ManyToOne(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinColumn(name = "evaluated_id", nullable = false)
    private Evaluated evaluated;

    @ManyToOne(fetch = FetchType.EAGER) // Alterado para EAGER
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Evaluator evaluator;

    @Builder.Default
    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // Alterado para EAGER
    private Set<Characteristic> characteristics = new HashSet<>();

    public void addCharacteristic(Characteristic characteristic) {
        characteristics.add(characteristic);
        characteristic.setEvaluation(this);
    }

    public void removeCharacteristic(Characteristic characteristic) {
        characteristics.remove(characteristic);
        characteristic.setEvaluation(null);
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