package com.triin.mushroom_spots_api.Repositories;

import com.triin.mushroom_spots_api.Entities.MushroomSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MushroomSpotRepository extends JpaRepository<MushroomSpot, Long> {
}
