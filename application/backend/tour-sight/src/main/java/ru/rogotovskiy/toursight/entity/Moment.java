package ru.rogotovskiy.toursight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "moments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "content")
    private String content;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @ManyToOne
    @JoinColumn(name = "sight_id")
    private Sight sight;
}
