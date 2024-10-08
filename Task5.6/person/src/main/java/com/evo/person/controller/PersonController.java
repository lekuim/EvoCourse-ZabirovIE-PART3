package com.evo.person.controller;

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

    @GetMapping
    public Optional<Person> findById(@RequestParam int id) {
        return repository.findById(id);
    }
    @GetMapping("/weather")
    public ResponseEntity<Weather> findWeatherByPersonId(@RequestParam int id){
        if(repository.findById(id).isPresent()) {
            Person person = repository.findById(id).get();
            String url = "http://localhost:8083/location/weather?name=" + person.getLocation();
            return new ResponseEntity<>(restTemplate.getForObject(url, Weather.class), HttpStatus.OK);
        }
        return new ResponseEntity(id, HttpStatus.BAD_REQUEST);
    }
    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }
}
