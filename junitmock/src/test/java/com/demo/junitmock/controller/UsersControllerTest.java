package com.demo.junitmock.controller;

import com.demo.junitmock.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.demo.junitmock.prototype.UsersPrototypeTest.aUserDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsersControllerTest {
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    UsersService usersService;

    @BeforeEach
    void setUp() {
        usersService = mock(UsersService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UsersController(usersService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveUsers() throws Exception {
        when(usersService.saveUser(any())).thenReturn(aUserDTO());
        mockMvc.perform(post("/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aUserDTO())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(aUserDTO())));
    }

    @Test
    void findAllUsers() throws Exception {
        when(usersService.findAll()).thenReturn(Collections.singletonList(aUserDTO()));
        mockMvc.perform(get("/users/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(Collections
                                .singletonList(aUserDTO()))))
                .andExpect(status().isOk());
    }

    @Test
    void findByLogin() {
    }

    @Test
    void deleteUsers() {
    }
}