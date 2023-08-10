package com.praksa.KitchenBackEnd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.praksa.KitchenBackEnd.controllers.LimitingFactorController;
import com.praksa.KitchenBackEnd.models.dto.LimFactorDTO;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.services.LimitingFactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LimitingFactorContorllerTest {

    private MockMvc mockMvc;

    @Mock
    private LimitingFactorService limitingFactorService;

    @InjectMocks
    private LimitingFactorController limitingFactorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(limitingFactorController).build();
    }

    @Test
    void testAddNewLimitingFactor() throws Exception {
        Long userId = 1L;
        LimFactorDTO limFactorDTO = new LimFactorDTO();
        LimitingFactor limitingFactor = new LimitingFactor();
        when(limitingFactorService.addLimitingFactor(eq(limFactorDTO), eq(userId)))
                .thenReturn(limitingFactor);

        mockMvc.perform(post("/api/v1/project/limitingFactor/newlimitingFactor/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(limFactorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(limitingFactor.getId()));
    }

    @Test
    void testUpdateLimitingFactor() throws Exception {
        Long limitingFactorId = 1L;
        LimFactorDTO limFactorDTO = new LimFactorDTO();
        LimitingFactor updatedLimitingFactor = new LimitingFactor();
        when(limitingFactorService.updateLimitingFactor(eq(limitingFactorId), eq(limFactorDTO)))
                .thenReturn(updatedLimitingFactor);

        mockMvc.perform(put("/api/v1/project/limitingFactor/{id}", limitingFactorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(limFactorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedLimitingFactor.getId()));
    }

    @Test
    void testGetLimitingFactorById() throws Exception {
        Long limitingFactorId = 1L;
        LimitingFactor limitingFactor = new LimitingFactor();
        when(limitingFactorService.getLimitingFactorbyId(eq(limitingFactorId)))
                .thenReturn(limitingFactor);

        mockMvc.perform(get("/api/v1/project/limitingFactor/{id}", limitingFactorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(limitingFactor.getId()));
    }

    @Test
    void testGetAllLimitingFactors() throws Exception {
        List<LimFactorDTO> limitingFactors = new ArrayList<>(); // Add sample limiting factors
        when(limitingFactorService.getLF()).thenReturn(limitingFactors);

        mockMvc.perform(get("/api/v1/project/limitingFactor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Assert it's an array
                .andExpect(jsonPath("$.length()").value(limitingFactors.size())); // Assert the length of the array
    }

    @Test
    void testDeleteLimitingFactor() throws Exception {
        Long limitingFactorId = 1L;
        LimitingFactor deletedLimitingFactor = new LimitingFactor();
        when(limitingFactorService.deleteLimitingFactor(eq(limitingFactorId)))
                .thenReturn(deletedLimitingFactor);

        mockMvc.perform(delete("/api/v1/project/limitingFactor/{id}", limitingFactorId))
                .andExpect(status().isOk())
                .andExpect(content().string("Limiting Factor was removed from db"));
    }

    // Helper method to convert objects to JSON
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
