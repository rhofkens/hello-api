package com.example.peopleservice.controller;

import com.example.peopleservice.model.Person;
import com.example.peopleservice.service.PersonService;
import com.example.peopleservice.repository.PeopleRepository;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
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
import static org.mockito.Mockito.doNothing;
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
    private PersonService personService;

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
        when(personService.getAllPeople()).thenReturn(Collections.singletonList(new Person()));

        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        // Verify that personService.getAllPeople() was called once
        verify(personService, times(1)).getAllPeople();
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

        when(personService.createPerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"gender\": \"Male\", \"age\": 30}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        // Verify that personService.createPerson() was called once
        verify(personService, times(1)).createPerson(any(Person.class));
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
        person.setFirstName("Jane");
        person.setLastName("Doe");

        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(person);

        mockMvc.perform(put("/people/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jane\", \"lastName\": \"Doe\", \"gender\": \"Female\", \"age\": 28}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        // Verify that personService.updatePerson() was called once
        verify(personService, times(1)).updatePerson(anyLong(), any(Person.class));
    }

    /**
     * Test deleting a person.
     * Verifies that the response status is OK.
     */
    @Test
    @Order(4)
    void testDeletePerson() throws Exception {
        doNothing().when(personService).deletePerson(anyLong());

        mockMvc.perform(delete("/people/1"))
                .andExpect(status().isOk());

        // Verify that personService.deletePerson() was called once
        verify(personService, times(1)).deletePerson(anyLong());
    }

    /**
     * Test retrieving all people.
     * Verifies that the response status is OK and the content type is JSON.
     */
    @Test
    @Order(1)
    void testGetAllPeople() throws Exception {
        when(personService.getAllPeople()).thenReturn(Collections.singletonList(new Person()));

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

        when(personService.createPerson(any(Person.class))).thenReturn(person);

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

        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(person);

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
        doNothing().when(personService).deletePerson(anyLong());

        mockMvc.perform(delete("/people/1"))
                .andExpect(status().isOk());
    }
}
