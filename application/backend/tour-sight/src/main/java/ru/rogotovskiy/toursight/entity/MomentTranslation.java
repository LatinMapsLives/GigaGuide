package ru.rogotovskiy.toursight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moment_translations", uniqueConstraints = @UniqueConstraint(columnNames = {"moment_id", "language_id"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moment_id")
    private Integer momentId;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;
}
