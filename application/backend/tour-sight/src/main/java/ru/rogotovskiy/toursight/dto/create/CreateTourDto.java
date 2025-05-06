package ru.rogotovskiy.toursight.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для создания тура")
public class CreateTourDto{
        @Schema(description = "Название тура", example = "Тур по главным достопримечательностям Воронежа")
        String name;
        @Schema(description = "Описание тура")
        String description;
        @Schema(description = "Город, по которому проходит этот тур", example = "Воронеж")
        String city;
        @Schema(description = "Категория тура", example = "Исторический")
        String category;
        @Schema(description = "Тип тура", example = "Пеший")
        String type;
        @Schema(description = "Список ID достопримечательностей, из которых состоит этот тур", example = "[1, 2, 3]")
        List<Integer> sights;
}
