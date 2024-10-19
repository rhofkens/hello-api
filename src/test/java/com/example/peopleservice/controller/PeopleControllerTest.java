package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
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

/**
 * Unit tests for the PeopleController class.
 * Uses MockMvc to simulate HTTP requests and verify responses.
 */
@TestMethodOrder(OrderAnnotation.class)
class PeopleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleController peopleController;

    /**
     * Set up the test environment before each test.
     * Initializes mocks and the MockMvc object.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
    }

    /**
     * Test retrieving all people.
     * Verifies that the response status is OK and the content type is JSON.
     */
    @Test
    @Order(1)
    void testGetAllPeople() throws Exception {
        /**
         * Test retrieving all people.
         * Verifies that the response status is OK and the content type is JSON.
         */
        when(peopleRepository.findAll()).thenReturn(Collections.singletonList(new Person()));

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Test adding a new person.
     * Verifies that the response status is OK and the returned person matches the input.
     */
    @Test
    @Order(2)
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

    /**
     * Test updating an existing person.
     * Verifies that the response status is OK and the updated person matches the input.
     */
    @Test
    @Order(3)
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

    /**
     * Test deleting a person.
     * Verifies that the response status is OK.
     */
    @Test
    @Order(4)
    void testDeletePerson() throws Exception {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(new Person()));

        mockMvc.perform(delete("/people/1"))
                .andExpect(status().isOk());
    }
}
