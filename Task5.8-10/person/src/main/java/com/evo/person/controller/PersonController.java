package com.evo.person.controller;

import com.evo.person.model.Person;
import com.evo.person.model.Weather;
import com.evo.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PersonRepository repository;
    @Value("${location.url}")
    String locationUrl;
    @GetMapping
    public List<Person> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Person> changeLocationByNam(@PathVariable Integer id, @RequestBody Person newPerson){
        if(repository.findById(id).isPresent()){
            Person person = repository.findById(id).get();
            person.setFirstname(newPerson.getFirstname());
            person.setLastname(newPerson.getLastname());
            person.setSurname(newPerson.getSurname());
            person.setLocation(newPerson.getLocation());
            person.setBirthday(newPerson.getBirthday());
            return new ResponseEntity<>(repository.save(person), HttpStatus.OK);
        }
        return new ResponseEntity<>(newPerson, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteLocationByName(@PathVariable Integer id){
        if(repository.findById(id).isPresent()){
            repository.delete(repository.findById(id).get());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/weather")
    public ResponseEntity<Weather> findWeatherByPersonId(@PathVariable int id){
        if(repository.findById(id).isPresent()) {
            Person person = repository.findById(id).get();
            String url = String.format("http://%s/location/weather?name=%s",locationUrl,person.getLocation());
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
