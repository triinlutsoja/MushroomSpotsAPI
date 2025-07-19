package com.triin.mushroomspotapi.Services;

import com.triin.mushroomspotapi.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroomspotapi.Entities.MushroomSpot;
import com.triin.mushroomspotapi.Mappers.MushroomSpotMapper;
import com.triin.mushroomspotapi.Repositories.MushroomSpotRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MushroomSpotServiceImplTest {

    private MushroomSpotServiceImpl mushroomSpotService;
    private MushroomSpotRepository mockRepository;
    private MushroomSpotMapper mockMapper;
    private GeometryFactory geometryFactory;

    private Point point1;
    private Point point2;
    private MushroomSpot spot1;
    private MushroomSpot spot2;
    private MushroomSpotGeoJsonDto dto1;
    private MushroomSpotGeoJsonDto dto2;
    private Map<MushroomSpot, MushroomSpotGeoJsonDto> spotToDtoMap;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(MushroomSpotRepository.class);
        mockMapper = Mockito.mock(MushroomSpotMapper.class);
        geometryFactory = new GeometryFactory();
        mushroomSpotService = new MushroomSpotServiceImpl(mockRepository, mockMapper, geometryFactory);

        // Set up test objects
        point1 = geometryFactory.createPoint(new Coordinate(27.755, 56.432));
        spot1 = new MushroomSpot(point1, "Magic mushrooms TEST");

        point2 = geometryFactory.createPoint(new Coordinate(27.754, 56.434));
        spot2 = new MushroomSpot(point2, "Freaky mushrooms TEST");

        // Set up test objects' GeoJSON DTOs
        dto1 = new MushroomSpotGeoJsonDto();
        dto1.setGeometry(Map.of("type", "Point","coordinates", point1));
        dto1.setProperties(Map.of("description", "Magic mushrooms TEST"));

        dto2 = new MushroomSpotGeoJsonDto();
        dto2.setGeometry(Map.of("type", "Point","coordinates", point2));
        dto2.setProperties(Map.of("description", "Freaky mushrooms TEST"));

        // Mock mapper behaviour
        spotToDtoMap = new HashMap<>();
        spotToDtoMap.put(spot1, dto1);
        spotToDtoMap.put(spot2, dto2);

        Mockito.when(mockMapper.toGeoJsonDto(Mockito.any())).thenAnswer(invocation -> {
            MushroomSpot arg = invocation.getArgument(0);
            System.out.println("MAPPER RECEIVED: " + arg.getDescription());
            return spotToDtoMap.get(arg);
        });
    }

    @Test
    void getAllSpots_ShouldReturnAListOfAllSpots() {
        // Arrange
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(spot1, spot2));

        // Act
        List<MushroomSpotGeoJsonDto> retrievedSpots = mushroomSpotService.getAllSpots();

        // Debug
        retrievedSpots.forEach(System.out::println);
        System.out.println("SPOT1: " + spot1.getDescription());
        System.out.println("SPOT2: " + spot2.getDescription());

        // Assert
        assertThat(retrievedSpots).isNotEmpty();
        assertThat(retrievedSpots)
                .contains(dto1, dto2);

    }

    @Test
    void getAllSpots_ShouldReturnEmptyList_WhenNoSpotsExist() {
        // Arrange
        Mockito.when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<MushroomSpotGeoJsonDto> retrievedSpots = mushroomSpotService.getAllSpots();

        // Assert
        assertThat(retrievedSpots).isEmpty();
    }
}
