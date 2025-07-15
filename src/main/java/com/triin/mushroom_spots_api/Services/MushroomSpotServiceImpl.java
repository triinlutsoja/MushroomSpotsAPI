package com.triin.mushroom_spots_api.Services;

import com.triin.mushroom_spots_api.DTOs.MushroomSpotCreateDto;
import com.triin.mushroom_spots_api.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroom_spots_api.Entities.MushroomSpot;
import com.triin.mushroom_spots_api.Exceptions.MushroomSpotEntityNotFoundException;
import com.triin.mushroom_spots_api.Mappers.MushroomSpotMapper;
import com.triin.mushroom_spots_api.Repositories.MushroomSpotRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MushroomSpotServiceImpl implements MushroomSpotService {

    private final MushroomSpotRepository repository;
    private final MushroomSpotMapper mapper;
    private final GeometryFactory geometryFactory;

    public MushroomSpotServiceImpl(MushroomSpotRepository repository, MushroomSpotMapper mapper, GeometryFactory geometryFactory) {
        this.repository = repository;
        this.mapper = mapper;
        this.geometryFactory = geometryFactory;
    }

    private void validateSpot(MushroomSpotCreateDto dto) {
        if (dto.getDescription() == null || dto.getDescription().isBlank()){
            throw new IllegalArgumentException("Description must not be null or blank.");
        }

        if (dto.getLongitude() == null || dto.getLatitude() == null){
            throw new IllegalArgumentException("Both longitude and latitude must be provided.");
        }
    }

    @Override
    public List<MushroomSpotGeoJsonDto> getAllSpots() {
        List<MushroomSpot> spots = repository.findAll();  // mul on list entiteid, tuleb dtoks teha

        return spots.stream()
                .map(mapper::toGeoJsonDto)
                .collect(Collectors.toList());
    }

    @Override
    public MushroomSpotGeoJsonDto getSpotById(Long id) {
        MushroomSpot spot = repository.findById(id)
                .orElseThrow(() -> new MushroomSpotEntityNotFoundException("MushroomSpotService: Mushroom spot with the id " + id + " not found."));
        return mapper.toGeoJsonDto(spot);
    }

    @Override
    public MushroomSpotGeoJsonDto saveSpot(MushroomSpotCreateDto dto) {
        validateSpot(dto);

        MushroomSpot spot = mapper.dtoToEntity(dto);
        MushroomSpot savedEntity = repository.save(spot);
        return mapper.toGeoJsonDto(savedEntity);
    }

    @Override
    public MushroomSpotGeoJsonDto updateSpot(Long id, MushroomSpotCreateDto spotDto) {
        validateSpot(spotDto);

        MushroomSpot existingSpot = repository.findById(id)
                .orElseThrow(() -> new MushroomSpotEntityNotFoundException("MushroomSpotService: Mushroom spot with " +
                        "the id " + id + " not found."));

        existingSpot.setDescription(spotDto.getDescription());
        Point point = geometryFactory.createPoint(new Coordinate(spotDto.getLongitude(), spotDto.getLatitude()));
        existingSpot.setCoordinates(point);

        MushroomSpot updatedSpot = repository.save(existingSpot);
        return mapper.toGeoJsonDto(updatedSpot);
    }

    @Override
    public void deleteSpot(Long id) {
        if (!repository.existsById(id)) {
            throw new MushroomSpotEntityNotFoundException("MushroomSpotService: Mushroom spot with id " + id + " not found.");
        }
        repository.deleteById(id);
    }
}
