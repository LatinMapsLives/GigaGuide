package ru.rogotovskiy.map.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour_translations", uniqueConstraints = @UniqueConstraint(columnNames = {"tour_id", "language_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tour_id")
    private Integer tourId;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
}
