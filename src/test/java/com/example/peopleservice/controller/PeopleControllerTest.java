package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PeopleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleController peopleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
    }

    @Test
    void testGetAllPeople() throws Exception {
        when(peopleRepository.findAll()).thenReturn(Collections.singletonList(new Person()));

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        when(peopleRepository.save(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"gender\": \"Male\", \"age\": 30}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testUpdatePerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");

        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(peopleRepository.save(any(Person.class))).thenReturn(person);

        mockMvc.perform(put("/people/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jane\", \"lastName\": \"Doe\", \"gender\": \"Female\", \"age\": 28}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testDeletePerson() throws Exception {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));

        mockMvc.perform(delete("/people/1"))
                .andExpect(status().isOk());
    }
}
