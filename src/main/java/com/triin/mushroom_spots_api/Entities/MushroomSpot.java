package com.triin.mushroom_spots_api.Entities;
import org.locationtech.jts.geom.Point;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mushroom_spot")
public class MushroomSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "geometry(Point,4326")
    private Point coordinates;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
