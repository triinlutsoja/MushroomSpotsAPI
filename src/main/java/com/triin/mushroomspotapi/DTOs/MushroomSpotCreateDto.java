package com.triin.mushroomspotapi.DTOs;

import java.util.Objects;

public class MushroomSpotCreateDto {
    private String description;
    private Double latitude;
    private Double longitude;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MushroomSpotCreateDto{" +
                "description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof MushroomSpotCreateDto that)) return false;

        return Objects.equals(description, that.description) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(latitude);
        result = 31 * result + Objects.hashCode(longitude);
        return result;
    }
}
