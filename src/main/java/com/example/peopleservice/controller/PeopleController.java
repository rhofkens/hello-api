package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.peopleservice.exception.ResourceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/people")
@Slf4j
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public List<Person> getAllPeople() {
        log.info("Fetching all people");
        return peopleRepository.findAll();
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        try {
            log.info("Adding a new person: {}", person);
            return peopleRepository.save(person);
        } catch (Exception e) {
            log.error("Failed to save person: {}", e.getMessage());
            throw new RuntimeException("Failed to save person: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        log.info("Updating person with id: {}", id);
        return peopleRepository.findById(id).map(person -> {
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            person.setGender(personDetails.getGender());
            person.setAge(personDetails.getAge());
            return peopleRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        log.info("Deleting person with id: {}", id);
        peopleRepository.deleteById(id);
    }
}
