package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.peopleservice.exception.ResourceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        try {
            return peopleRepository.save(person);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save person: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return peopleRepository.findById(id).map(person -> {
            person.setName(personDetails.getName());
            person.setAge(personDetails.getAge());
            return peopleRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        peopleRepository.deleteById(id);
    }
}
