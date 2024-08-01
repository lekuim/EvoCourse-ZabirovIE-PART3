package com.evo.weather.controller;
import com.evo.weather.model.Weather;
import com.evo.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherRepository repository;

    @GetMapping
    public Iterable<Weather> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{longitude}/{latitude}")
    public Optional<Weather> findByLongitudeAndLatitude(@PathVariable Double longitude,@PathVariable  Double latitude){
        return repository.findByLongitudeAndLatitude(longitude,latitude);
    }
    @GetMapping("/{id}")
    public Optional<Weather> findById(@PathVariable int id){
        return repository.findById(id);
    }
    @PostMapping
    public ResponseEntity<Weather> save(@RequestBody Weather person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }
}
