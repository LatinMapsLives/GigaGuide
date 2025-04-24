package ru.rogotovskiy.map.mapper;

import org.springframework.stereotype.Component;
import ru.rogotovskiy.map.dto.CoordinatesDto;
import ru.rogotovskiy.map.entity.Moment;
import ru.rogotovskiy.map.entity.Sight;

@Component
public class MapMapper {

    public CoordinatesDto toDto(Moment moment) {
        return new CoordinatesDto(moment.getId(), moment.getLatitude(), moment.getLongitude());
    }

    public CoordinatesDto toDto(Sight sight) {
        return new CoordinatesDto(sight.getId(), sight.getLatitude(), sight.getLongitude());
    }
}
