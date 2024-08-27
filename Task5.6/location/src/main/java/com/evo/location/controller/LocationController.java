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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    @Autowired
    private LocationRepository repository;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public List<Location> getLocations(){
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    @GetMapping(params = "name")
    public Optional<Location> getLocation(@RequestParam String name) {
        return repository.findByName(name);
    }
    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping
    public ResponseEntity<Location> changeLocationByNam(@RequestParam String name, @RequestBody Location newLocation){
        if(repository.findByName(name).isPresent()){
            Location location = repository.findByName(name).get();
            location.setName(newLocation.getName());
            location.setLatitude(newLocation.getLatitude());
            location.setLongitude(newLocation.getLongitude());
            return new ResponseEntity<>(repository.save(location), HttpStatus.OK);
        }
        return new ResponseEntity<>(newLocation, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLocationByName(@RequestParam String name){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if(repository.findByName(name).isPresent()){
            repository.delete(repository.findByName(name).get());
            return new ResponseEntity<>(name, HttpStatus.OK);
        }
        return new ResponseEntity<>(name, HttpStatus.NOT_FOUND);
    }
    @GetMapping("/weather")
    public ResponseEntity<Weather> redirectRequestWeather(@RequestParam String name) {
        if(repository.findByName(name).isPresent()) {
            Location location = repository.findByName(name).get();
            String url = String.format("http://localhost:8082/weather?lat=%s&lon=%s", location.getLatitude(), location.getLongitude());
            return new ResponseEntity<>(restTemplate.getForObject(url, Weather.class), HttpStatus.OK);
        }
        return new ResponseEntity(name, HttpStatus.BAD_REQUEST);
    }


}