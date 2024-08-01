package com.evo.person.controller;

import com.evo.person.model.Location;
import com.evo.person.model.Person;
import com.evo.person.model.Weather;
import com.evo.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PersonRepository repository;

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }
    @GetMapping("/{id}/weather")
    public Weather findWeatherByPersonId(@PathVariable int id){
        Person person = repository.findById(id).get();
        Location location = restTemplate.getForObject("http://localhost:8080/location/name/" + person.getLocation(), Location.class);
        Weather weather = restTemplate.getForObject(String.format("http://localhost:8080/location/%d/weather", location.getId()), Weather.class);
        return weather;
    }
    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }
}
