package com.evo.location.repository;

import com.evo.location.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Integer> {
    Optional<Location> findByCityName(String cityName);
}
