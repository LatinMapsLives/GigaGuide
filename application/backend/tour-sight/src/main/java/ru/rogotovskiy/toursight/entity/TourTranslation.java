package ru.rogotovskiy.toursight.entity;

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

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "city")
    private String city;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;
}
