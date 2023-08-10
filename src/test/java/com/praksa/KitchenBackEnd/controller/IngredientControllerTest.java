package com.praksa.KitchenBackEnd.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praksa.KitchenBackEnd.controllers.IngredientController;
import com.praksa.KitchenBackEnd.models.dto.IngredientDTO;
import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;
import com.praksa.KitchenBackEnd.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
public class IngredientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @Mock
    private IngredientRepository ingredientRepository;
    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Set up mock behavior
        Ingredient existingIngredient = new Ingredient(); // Create a sample Ingredient object
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(existingIngredient));
        
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }


    @Test
    void testAddNewIngredient() throws Exception {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName("Ingredient Name");

        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(26L);
        savedIngredient.setName("Ingredient Name");

        when(ingredientService.addIngredient(any())).thenReturn(savedIngredient);

        mockMvc.perform(post("/api/v1/project/ingredient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingredientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(26))
                .andExpect(jsonPath("$.name").value("Ingredient Name"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        IngredientDTO ingredientForUpdate = new IngredientDTO();
        ingredientForUpdate.setName("Updated Ingredient Name");

        // Set up your mockMvc with the controller
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        mockMvc.perform(put("/api/v1/project/ingredient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingredientForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Ingredient Name"));
    }

    @Test
    void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient Name");

        when(ingredientService.getIngredientById(anyLong())).thenReturn(ingredient);

        mockMvc.perform(get("/api/v1/project/ingredient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ingredient Name"));
    }

    @Test
    void testGetAllIngredients() throws Exception {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient 1");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Ingredient 2");

        when(ingredientService.getIngredients())
                .thenReturn(List.of(ingredient1, ingredient2)
                        .stream()
                        .map(ingredient -> {
                            IngredientDTO dto = new IngredientDTO();
                            dto.setId(ingredient.getId());
                            dto.setName(ingredient.getName());
                            return dto;
                        })
                        .collect(Collectors.toList()));

        mockMvc.perform(get("/api/v1/project/ingredient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Ingredient 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Ingredient 2"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        Ingredient deletedIngredient = new Ingredient();
        deletedIngredient.setId(1L);
        deletedIngredient.setName("Deleted Ingredient");

        when(ingredientService.deleteIngredient(anyLong())).thenReturn(deletedIngredient);

        mockMvc.perform(delete("/api/v1/project/ingredient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Deleted Ingredient"));
    }

    @Test
    void testConnectIngredientToLimitingFactor() throws Exception {
        LimitingFactor connectedLimitingFactor = new LimitingFactor();
        connectedLimitingFactor.setId(1L);
        connectedLimitingFactor.setName("Connected Limiting Factor");

        when(ingredientService.connectIngredientToLimitingFactor(anyLong(), anyLong()))
                .thenReturn(connectedLimitingFactor);

        mockMvc.perform(post("/api/v1/project/ingredient/addLfToIng/1/to/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Connected Limiting Factor"));
    }
}
