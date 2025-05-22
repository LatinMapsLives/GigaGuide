package ru.rogotovskiy.toursight.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Данные для создания момента")
public record CreateMomentDto(
        @Schema(description = "Название момента", example = "Мемориальный некрополь")
        String name,
        @Schema(description = "Порядковый номер момента", example = "1")
        Integer orderNumber,
        @Schema(description = "Текстовый гид момента", example = "Некрополь — одно из самых значимых и трогательных мест в парке...")
        String content,
        @Schema(description = "ID достопримечательности, которой принадлежит этот момент", example = "2")
        Integer sightId,
        @Schema(description = "Широта", example = "51.657333")
        BigDecimal latitude,
        @Schema(description = "Долгота", example = "39.216445")
        BigDecimal longitude
) {
}
