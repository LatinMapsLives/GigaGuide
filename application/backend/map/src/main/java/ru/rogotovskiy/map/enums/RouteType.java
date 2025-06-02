package ru.rogotovskiy.map.enums;

import lombok.Getter;

@Getter
public enum RouteType {
    AUTO("auto", "Автомобильный", "car"),
    PEDESTRIAN("pedestrian", "Пешеходный", "foot"),
    BICYCLE("bicycle", "Велосипедный", "bike");

    private final String tourType;
    private final String graphHopperProfile;

    RouteType(String internalType, String tourType, String graphHopperProfile) {
        this.tourType = tourType;
        this.graphHopperProfile = graphHopperProfile;
    }

    public static RouteType fromString(String value) {
        for (RouteType type : values()) {
            if (type.tourType.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown route type: " + value);
    }

}
