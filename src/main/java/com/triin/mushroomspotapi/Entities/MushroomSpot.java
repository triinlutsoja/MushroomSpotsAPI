package com.triin.mushroomspotapi.Entities;
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

    public MushroomSpot() {
    }

    public MushroomSpot(Point coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
    }

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

    @Override
    public String toString() {
        return "MushroomSpot{" +
                "id=" + id +
                ", coordinates=" + coordinates +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MushroomSpot that)) return false;

        return Objects.equals(coordinates, that.coordinates)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(coordinates);
        result = 31 * result + Objects.hashCode(description);
        return result;
    }
}
