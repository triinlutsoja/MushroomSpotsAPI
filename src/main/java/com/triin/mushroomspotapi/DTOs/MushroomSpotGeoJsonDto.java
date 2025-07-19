package com.triin.mushroomspotapi.DTOs;

import java.util.Map;
import java.util.Objects;

public class MushroomSpotGeoJsonDto {

    private String type = "Feature";
    private Map<String, Object> geometry;
    private Map<String, Object> properties;

    public MushroomSpotGeoJsonDto(){}

    public MushroomSpotGeoJsonDto(Map<String, Object> geometry, Map<String, Object> properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getGeometry() {
        return geometry;
    }

    public void setGeometry(Map<String, Object> geometry) {
        this.geometry = geometry;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "MushroomSpotGeoJsonDto{" +
                "type='" + type + '\'' +
                ", geometry=" + geometry +
                ", properties=" + properties +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof MushroomSpotGeoJsonDto that)) return false;

        return Objects.equals(type, that.type) && Objects.equals(geometry, that.geometry) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(geometry);
        result = 31 * result + Objects.hashCode(properties);
        return result;
    }
}
