package ru.rogotovskiy.toursight.dto.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourDto{
        String name;
        String description;
        String city;
        String category;
        String type;
        List<Integer> sights;
}
