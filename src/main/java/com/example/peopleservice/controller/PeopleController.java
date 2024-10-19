package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return peopleRepository.save(person);
    }
}
