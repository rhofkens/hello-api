package com.example.peopleservice.service;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import com.example.peopleservice.exception.ResourceNotFoundException;
import java.util.List;
import com.example.peopleservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Value("${avatar.service.baseUrl}")
    private String avatarServiceBaseUrl;

    /**
     * Creates a new person with an automatically generated avatar image URL.
     *
     * @param person the person to create
     * @return the created person
     */
    public Person createPerson(Person person) {
        // Logic to generate avatar image URL
        String avatarImageUrl = generateAvatarImageUrl(person);
        person.setAvatarImageUrl(avatarImageUrl);
        return peopleRepository.save(person);
    }

    private String generateAvatarImageUrl(Person person) {
        // Implement logic to generate avatar image URL
        String firstName = person.getFirstName() != null ? person.getFirstName() : "";
        String lastName = person.getLastName() != null ? person.getLastName() : "";
        String seed = firstName + lastName;
        if (seed.isEmpty()) {
            seed = "default";
        }
        String options = "?set=set2&size=200x200";
        return avatarServiceBaseUrl + seed + ".png" + options;
    }
    public Person updatePerson(Long id, Person personDetails) {
        return peopleRepository.findById(id).map(person -> {
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            // Regenerate avatar image URL after updating name
            String avatarImageUrl = generateAvatarImageUrl(person);
            person.setAvatarImageUrl(avatarImageUrl);
            person.setGender(personDetails.getGender());
            person.setAge(personDetails.getAge());
            return peopleRepository.save(person);
        }).orElseThrow(() -> new ResourceNotFoundException("Person not found with id " + id));
    }
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public void deletePerson(Long id) {
        if (!peopleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Person not found with id " + id);
        }
        peopleRepository.deleteById(id);
    }
}
