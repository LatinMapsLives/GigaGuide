package ru.rogotovskiy.toursight.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные тура")
public class TourDto {
        @Schema(description = "ID тура", example = "256")
        Integer id;
        @Schema(description = "Название тура", example = "Тур по главным достопримечательностям Воронежа")
        String name;
        @Schema(description = "Описание тура")
        String description;
        @Schema(description = "Город, по которому проходит этот тур", example = "Воронеж")
        String city;
        @Schema(description = "Длительность тура", example = "64")
        Integer durationMinutes;
        @Schema(description = "Протяжённость тура", example = "2.54")
        BigDecimal distanceKm;
        @Schema(description = "Категория тура", example = "Исторический")
        String category;
        @Schema(description = "Тип тура", example = "Пеший")
        String type;
        @Schema(description = "Рейтинг тура", example = "4.57")
        BigDecimal rating;
        @Schema(description = "Имя файла картинки", example = "a0d3c84b-f1d1-40e0-81ba-fabffd0d5ed4.jpg")
        String imagePath;
        @Schema(description = "Широта", example = "51.657333")
        BigDecimal latitude;
        @Schema(description = "Долгота", example = "39.216445")
        BigDecimal longitude;
        List<PreviewSightDto> sights;
}
