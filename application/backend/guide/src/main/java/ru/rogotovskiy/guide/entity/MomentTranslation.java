package ru.rogotovskiy.guide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moment_translations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "moment_id")
    private Integer momentId;

    @Column(name = "content")
    private String content;
}
