package com.evo.location.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private String cityName;
    @NonNull
    private String countryCode;
    @NonNull
    private Double longitude;
    @NonNull
    private Double latitude;

    public Location(@NonNull String cityName, @NonNull String countryCode, @NonNull Double longitude, @NonNull Double latitude) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
