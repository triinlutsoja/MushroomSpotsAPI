package com.triin.mushroom_spots_api.Mappers;

import com.triin.mushroom_spots_api.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroom_spots_api.Entities.MushroomSpot;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;

public class MushroomSpotMapper {

    public static MushroomSpotGeoJsonDto toGeoJsonDto(MushroomSpot spot) {
        Point location = spot.getCoordinates();

        Map<String, Object> geometry = new HashMap<>();
        geometry.put("type", "Point");
        geometry.put("coordinates", new double[]{ location.getX(), location.getY() });

        Map<String, Object> properties = new HashMap<>();
        properties.put("id", spot.getId());
        properties.put("description", spot.getDescription());

        return new MushroomSpotGeoJsonDto(geometry, properties);
    }
}
