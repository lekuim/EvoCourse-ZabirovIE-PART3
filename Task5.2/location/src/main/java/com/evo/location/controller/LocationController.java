package com.evo.location.controller;

import com.evo.location.model.Location;
import com.evo.location.model.Weather;
import com.evo.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{locationId}/weather")
    public Weather fingWeatherByLocationId(@PathVariable int locationId){
        Location location = repository.findById(locationId).get();
        return restTemplate.getForObject(String.format(Locale.ROOT, "http://localhost:8082/weather/%f/%f",location.getLongitude(), location.getLatitude()), Weather.class);
    }
    @GetMapping
    public Iterable<Location> findAll(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Location> findById(@PathVariable int id){
        return repository.findById(id);
    }

    @GetMapping("/name/{cityName}")
    public Optional<Location> findByCityName(@PathVariable String cityName){
        return repository.findByCityName(cityName);
    }
    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

}
