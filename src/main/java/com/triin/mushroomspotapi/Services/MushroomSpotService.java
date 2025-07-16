package com.triin.mushroomspotapi.Services;

import com.triin.mushroomspotapi.DTOs.MushroomSpotCreateDto;
import com.triin.mushroomspotapi.DTOs.MushroomSpotGeoJsonDto;

import java.util.List;

public interface MushroomSpotService {
    List<MushroomSpotGeoJsonDto> getAllSpots();
    MushroomSpotGeoJsonDto getSpotById(Long id);
    MushroomSpotGeoJsonDto saveSpot(MushroomSpotCreateDto spotDto);
    MushroomSpotGeoJsonDto updateSpot(Long id, MushroomSpotCreateDto spotDto);
    void deleteSpot(Long id);
}
