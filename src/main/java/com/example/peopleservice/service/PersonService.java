package com.example.peopleservice.service;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PeopleRepository peopleRepository;

    public Person createPerson(Person person) {
        // Logic to generate avatar image URL
        String avatarImageUrl = generateAvatarImageUrl(person);
        person.setAvatarImageUrl(avatarImageUrl);
        return peopleRepository.save(person);
    }

    private String generateAvatarImageUrl(Person person) {
        // Implement logic to generate avatar image URL
        return "http://example.com/avatar/" + person.getFirstName() + "_" + person.getLastName() + ".png";
    }
}
