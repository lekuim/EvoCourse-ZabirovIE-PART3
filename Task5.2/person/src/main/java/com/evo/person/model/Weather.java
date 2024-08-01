package com.evo.person.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weather {
    @Id
    @GeneratedValue
    private int id;
    @NonNull
    private Double pressure;
    @NonNull
    private Double temperature;
    @NonNull
    private String cloudiness;
    @NonNull
    private Double longitude;
    @NonNull
    private Double latitude;

    public Weather(@NonNull Double pressure, @NonNull Double temperature, @NonNull String cloudiness, @NonNull Double longitude, @NonNull Double latitude) {
        this.pressure = pressure;
        this.temperature = temperature;
        this.cloudiness = cloudiness;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
