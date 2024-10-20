package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.peopleservice.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing people.
 * Provides endpoints to perform CRUD operations on Person entities.
 */
@RestController
@RequestMapping("/people")
@Slf4j
@Tag(name = "People", description = "The People API")
public class PeopleController {

    @Autowired
    private PersonService personService;

    /**
     * Retrieve all people from the repository.
     *
     * @return a list of all people
     */
    @GetMapping
    @Operation(summary = "Retrieve all people")
    public List<Person> getAllPeople() {
        log.info("Fetching all people");
        return personService.getAllPeople();
    }

    /**
     * Adds a new person with an automatically generated avatar image URL.
     *
     * @param person the person to add
     * @return the added person
     */
    @PostMapping
    @Operation(summary = "Add a new person")

    public Person addPerson(@RequestBody Person person) {
        try {
            log.info("Adding a new person: {} {}, Avatar URL: {}", person.getFirstName(), person.getLastName(), person.getAvatarImageUrl());
            return personService.createPerson(person);
        } catch (Exception e) {
            log.error("Failed to save person: {}", e.getMessage());
            throw new RuntimeException("Failed to save person: " + e.getMessage());
        }
    }

    /**
     * Update an existing person in the repository.
     *
     * @param id            the ID of the person to update
     * @param personDetails the updated person details
     * @return the updated person
     * @throws ResourceNotFoundException if the person is not found
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing person")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        log.info("Updating person with id: {} to {} {}", id, personDetails.getFirstName(), personDetails.getLastName());
        return personService.updatePerson(id, personDetails);
    }

    /**
     * Delete a person from the repository.
     *
     * @param id the ID of the person to delete
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person")
    public void deletePerson(@PathVariable Long id) {
        log.info("Deleting person with id: {}", id);
        personService.deletePerson(id);
    }

    @GetMapping("/welcome")
    @Operation(summary = "Welcome all people")
    public String welcomeAllPeople() {
        List<Person> people = personService.getAllPeople();
        if (people.isEmpty()) {
            return "No people found.";
        }
        return people.stream()
                .map(person -> {
                    String firstName = person.getFirstName() != null ? person.getFirstName() : "";
                    String lastName = person.getLastName() != null ? person.getLastName() : "";
                    return "Welcome " + firstName + " " + lastName;
                })
                .collect(Collectors.joining("\n"));
    }
}
