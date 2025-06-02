package ru.rogotovskiy.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
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
}
