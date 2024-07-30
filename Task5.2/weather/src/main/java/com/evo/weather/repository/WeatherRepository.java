package com.evo.weather.repository;

import com.evo.weather.model.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {
    Optional<Weather> findByLongitudeAndLatitude(Double Longitude, Double Latitude);
}
