package ru.rogotovskiy.toursight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sights")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_url")
    private String imagePath;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "rating")
    private BigDecimal rating;

    @OneToMany(mappedBy = "sight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moment> moments = new ArrayList<>();
}
