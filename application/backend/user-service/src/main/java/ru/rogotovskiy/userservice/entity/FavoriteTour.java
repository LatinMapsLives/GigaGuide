package ru.rogotovskiy.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_favorite_tours")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteTour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tour_id", nullable = false)
    private Integer tourId;
}
