package ru.rogotovskiy.toursight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "tours")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "duration")
    private Integer durationMinutes;

    @Column(name = "distance")
    private BigDecimal distanceKm;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "image_url")
    private String imagePath;

    /*    @ManyToMany
        @JoinTable(
                name = "tour_sights",
                joinColumns = @JoinColumn(name = "tour_id"),
                inverseJoinColumns = @JoinColumn(name = "sight_id")
        )
        private List<Sight> sights = new ArrayList<>();*/
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<TourSight> tourSights = new ArrayList<>();
}
