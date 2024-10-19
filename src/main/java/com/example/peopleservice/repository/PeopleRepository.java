package com.example.peopleservice.repository;

import com.example.peopleservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<Person, Long> {
}
