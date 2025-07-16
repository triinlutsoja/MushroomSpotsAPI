package com.triin.mushroomspotapi.Repositories;

import com.triin.mushroomspotapi.Entities.MushroomSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MushroomSpotRepository extends JpaRepository<MushroomSpot, Long> {
}
