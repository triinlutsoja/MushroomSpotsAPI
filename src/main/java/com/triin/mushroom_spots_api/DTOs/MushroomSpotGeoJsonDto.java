package com.triin.mushroom_spots_api.DTOs;

import java.util.Map;

public class MushroomSpotGeoJsonDto {

    private String type = "Feature";
    private Map<String, Object> geometry;
    private Map<String, Object> properties;

    public MushroomSpotGeoJsonDto(Map<String, Object> geometry, Map<String, Object> properties) {
        this.geometry = geometry;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getGeometry() {
        return geometry;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
