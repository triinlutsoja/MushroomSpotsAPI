package com.triin.mushroom_spots_api.Services;

import com.triin.mushroom_spots_api.DTOs.MushroomSpotCreateDto;
import com.triin.mushroom_spots_api.DTOs.MushroomSpotGeoJsonDto;

import java.util.List;

public interface MushroomSpotService {
    List<MushroomSpotGeoJsonDto> getAllSpots();
    MushroomSpotGeoJsonDto getSpotById(Long id);
    MushroomSpotGeoJsonDto saveSpot(MushroomSpotCreateDto spotDto);
    MushroomSpotGeoJsonDto updateSpot(Long id, MushroomSpotCreateDto spotDto);
    void deleteSpot(Long id);
}
