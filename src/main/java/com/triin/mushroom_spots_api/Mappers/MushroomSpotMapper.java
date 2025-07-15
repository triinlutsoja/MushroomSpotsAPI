package com.triin.mushroom_spots_api.Mappers;

import com.triin.mushroom_spots_api.DTOs.MushroomSpotCreateDto;
import com.triin.mushroom_spots_api.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroom_spots_api.Entities.MushroomSpot;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MushroomSpotMapper {

    private final GeometryFactory geometryFactory;

    public MushroomSpotMapper(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    public MushroomSpotGeoJsonDto toGeoJsonDto(MushroomSpot spot) {
        Point location = spot.getCoordinates();

        Map<String, Object> geometry = new HashMap<>();
        geometry.put("type", "Point");
        geometry.put("coordinates", new double[]{ location.getX(), location.getY() });

        Map<String, Object> properties = new HashMap<>();
        properties.put("id", spot.getId());
        properties.put("description", spot.getDescription());

        return new MushroomSpotGeoJsonDto(geometry, properties);
    }

    public MushroomSpot dtoToEntity(MushroomSpotCreateDto dto) {
        MushroomSpot spot = new MushroomSpot();

        spot.setDescription(dto.getDescription());

        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        spot.setCoordinates(point);

        return spot;
    }
}
