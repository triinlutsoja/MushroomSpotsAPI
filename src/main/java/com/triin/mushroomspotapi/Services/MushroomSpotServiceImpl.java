package com.triin.mushroomspotapi.Services;

import com.triin.mushroomspotapi.DTOs.MushroomSpotCreateDto;
import com.triin.mushroomspotapi.DTOs.MushroomSpotGeoJsonDto;
import com.triin.mushroomspotapi.Entities.MushroomSpot;
import com.triin.mushroomspotapi.Exceptions.MushroomSpotEntityNotFoundException;
import com.triin.mushroomspotapi.Mappers.MushroomSpotMapper;
import com.triin.mushroomspotapi.Repositories.MushroomSpotRepository;
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
        List<MushroomSpot> spots = repository.findAll();

        return spots.stream()
                .map(mapper::toGeoJsonDto)
                .collect(Collectors.toList());
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
