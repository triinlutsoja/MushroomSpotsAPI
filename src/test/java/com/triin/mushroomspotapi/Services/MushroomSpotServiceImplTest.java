package com.triin.mushroomspotapi.Services;

import com.triin.mushroomspotapi.DTOs.MushroomSpotCreateDto;
import com.triin.mushroomspotapi.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroomspotapi.Entities.MushroomSpot;
import com.triin.mushroomspotapi.Exceptions.MushroomSpotEntityNotFoundException;
import com.triin.mushroomspotapi.Mappers.MushroomSpotMapper;
import com.triin.mushroomspotapi.Repositories.MushroomSpotRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.Mockito;

import java.util.*;

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
        spot1.setId(1L);

        point2 = geometryFactory.createPoint(new Coordinate(27.754, 56.434));
        spot2 = new MushroomSpot(point2, "Freaky mushrooms TEST");
        spot2.setId(2L);

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
    }

    @Test
    void getAllSpots_ShouldReturnAListOfAllSpots() {
        // Arrange
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(spot1, spot2));
        Mockito.when(mockMapper.toGeoJsonDto(Mockito.any())).thenAnswer(invocation -> {
            MushroomSpot arg = invocation.getArgument(0);
            if (arg == null) return null;
            System.out.println("MAPPER RECEIVED: " + arg.getDescription());
            return spotToDtoMap.get(arg);
        });

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
        Mockito.when(mockMapper.toGeoJsonDto(Mockito.any())).thenAnswer(invocation -> {
            MushroomSpot arg = invocation.getArgument(0);
            if (arg == null) return null;
            System.out.println("MAPPER RECEIVED: " + arg.getDescription());
            return spotToDtoMap.get(arg);
        });

        // Act
        List<MushroomSpotGeoJsonDto> retrievedSpots = mushroomSpotService.getAllSpots();

        // Assert
        assertThat(retrievedSpots).isEmpty();
    }

    @Test
    void saveSpot_ShouldReturnCorrectDtoWithoutException_WhenSpotIsValid() {
        // Arrange
        String description = "New valid mushrooms TEST";
        Point newPoint = geometryFactory.createPoint(new Coordinate(28.754, 55.534));
        MushroomSpot newValidSpot = new MushroomSpot(newPoint, description);

        MushroomSpotGeoJsonDto newGeoJsonDto = new MushroomSpotGeoJsonDto();
        newGeoJsonDto.setGeometry(Map.of("type", "Point","coordinates", newPoint));
        newGeoJsonDto.setProperties(Map.of("description", description));

        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(description);
        newSpotDto.setLongitude(newPoint.getX());
        newSpotDto.setLatitude(newPoint.getY());

        Mockito.when(mockRepository.save(newValidSpot)).thenReturn(newValidSpot);
        Mockito.when(mockMapper.dtoToEntity(newSpotDto)).thenReturn(newValidSpot);
        Mockito.when(mockMapper.toGeoJsonDto(newValidSpot)).thenReturn(newGeoJsonDto);

        // Act
        MushroomSpotGeoJsonDto savedSpot = mushroomSpotService.saveSpot(newSpotDto);

        // Assert
        assertThat(savedSpot).isEqualTo(newGeoJsonDto);
        assertThatCode(() -> mushroomSpotService.saveSpot(newSpotDto))
                .doesNotThrowAnyException();
    }

    @Test
    void saveSpot_ShouldThrowException_WhenSpotDescriptionIsBlank() {
        // Arrange
        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription("");
        newSpotDto.setLongitude(28.754);
        newSpotDto.setLatitude(55.534);

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.saveSpot(newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description must not be null or blank.");
    }

    @Test
    void saveSpot_ShouldThrowException_WhenSpotDescriptionIsNull() {
        // Arrange
        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(null);
        newSpotDto.setLongitude(28.754);
        newSpotDto.setLatitude(55.534);

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.saveSpot(newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description must not be null or blank.");
    }

    @Test
    void saveSpot_ShouldThrowException_WhenSpotLongitudeIsNull() {
        // Arrange
        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription("New valid mushrooms TEST");
        newSpotDto.setLongitude(null);
        newSpotDto.setLatitude(55.534);

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.saveSpot(newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Both longitude and latitude must be provided.");
    }

    @Test
    void saveSpot_ShouldThrowException_WhenSpotLatitudeIsNull() {
        // Arrange
        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription("New valid mushrooms TEST");
        newSpotDto.setLongitude(28.754);
        newSpotDto.setLatitude(null);

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.saveSpot(newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Both longitude and latitude must be provided.");
    }

    @Test
    void updateSpot_ShouldUpdate_WhenSpotExistsAndNewSpotDescriptionIsValid() {
        // Arrange
        String newDescription = "New description for valid mushrooms TEST";

        MushroomSpotGeoJsonDto newGeoJsonDto = new MushroomSpotGeoJsonDto();
        newGeoJsonDto.setGeometry(Map.of("type", "Point","coordinates", point1));
        newGeoJsonDto.setProperties(Map.of("description", newDescription));

        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(newDescription);
        newSpotDto.setLongitude(point1.getX());
        newSpotDto.setLatitude(point1.getY());

        Mockito.when(mockRepository.findById(spot1.getId())).thenReturn(Optional.of(spot1));
        Mockito.when(mockRepository.save(Mockito.any(MushroomSpot.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockMapper.toGeoJsonDto(Mockito.any(MushroomSpot.class))).thenReturn(newGeoJsonDto);

        // Act
        MushroomSpotGeoJsonDto updatedSpot = mushroomSpotService.updateSpot(spot1.getId(), newSpotDto);

        // Assert
        assertThat(updatedSpot).isEqualTo(newGeoJsonDto);
        assertThat(spot1.getDescription()).isEqualTo(newDescription);
        assertThatCode(() -> mushroomSpotService.updateSpot(spot1.getId(), newSpotDto))
                .doesNotThrowAnyException();
    }

    @Test
    void updateSpot_ShouldThrowException_WhenSpotExistsAndNewSpotDescriptionIsBlank() {
        // Arrange
        String newDescription = "";

        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(newDescription);
        newSpotDto.setLongitude(point1.getX());
        newSpotDto.setLatitude(point1.getY());

        Mockito.when(mockRepository.findById(spot1.getId())).thenReturn(Optional.of(spot1));

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.updateSpot(spot1.getId(), newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description must not be null or blank.");
    }

    @Test
    void updateSpot_ShouldThrowException_WhenSpotExistsAndNewSpotDescriptionIsNull() {
        // Arrange
        String newDescription = null;

        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(newDescription);
        newSpotDto.setLongitude(point1.getX());
        newSpotDto.setLatitude(point1.getY());

        Mockito.when(mockRepository.findById(spot1.getId())).thenReturn(Optional.of(spot1));

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.updateSpot(spot1.getId(), newSpotDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description must not be null or blank.");
    }

    @Test
    void updateSpot_ShouldThrowException_WhenSpotDoesNotExist() {
        // Arrange
        String newDescription = "New description for valid mushrooms TEST";
        Long nonExistingId = 3L;

        MushroomSpotGeoJsonDto newGeoJsonDto = new MushroomSpotGeoJsonDto();
        newGeoJsonDto.setGeometry(Map.of("type", "Point","coordinates", point1));
        newGeoJsonDto.setProperties(Map.of("description", newDescription));

        MushroomSpotCreateDto newSpotDto = new MushroomSpotCreateDto();
        newSpotDto.setDescription(newDescription);
        newSpotDto.setLongitude(point1.getX());
        newSpotDto.setLatitude(point1.getY());

        Mockito.when(mockRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(mockRepository.save(Mockito.any(MushroomSpot.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(mockMapper.toGeoJsonDto(Mockito.any(MushroomSpot.class))).thenReturn(newGeoJsonDto);

        // Act & Assert
        assertThatThrownBy(() -> mushroomSpotService.updateSpot(nonExistingId, newSpotDto))
                .isInstanceOf(MushroomSpotEntityNotFoundException.class)
                .hasMessageContaining("MushroomSpotService: Mushroom spot with " +
                        "the id " + nonExistingId + " not found.");
    }
}
