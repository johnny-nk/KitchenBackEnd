package com.praksa.KitchenBackEnd.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.praksa.KitchenBackEnd.controllers.RecipeController;
import com.praksa.KitchenBackEnd.models.dto.RecipeRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.services.RecipeService;

class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(recipeController).build();
    }

    @Test
    void testGetAllRecipes() throws Exception {
        when(recipeService.getRecipes()).thenReturn(Collections.singletonList(new Recipe()));

        mockMvc.perform(get("/api/v1/project/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetRecipeById() throws Exception {
        Long recipeId = 1L;
        RecipeRegisterDTO recipeDTO = new RecipeRegisterDTO();
        when(recipeService.getRecipe(eq(recipeId))).thenReturn(recipeDTO);

        mockMvc.perform(get("/api/v1/project/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeDTO.getId()));
    }

    @Test
    void testCreateRecipe() throws Exception {
        RecipeRegisterDTO recipeDTO = new RecipeRegisterDTO();
        when(recipeService.createRecipe(any(RecipeRegisterDTO.class), anyString())).thenReturn(recipeDTO);

        mockMvc.perform(post("/api/v1/project/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recipeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(recipeDTO.getId()));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        Long recipeId = 1L;
        RecipeRegisterDTO recipeDTO = new RecipeRegisterDTO();
        when(recipeService.updateRecipe(eq(recipeDTO), eq(recipeId))).thenReturn(recipeDTO);

        mockMvc.perform(put("/api/v1/project/recipes/{id}", recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(recipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipeDTO.getId()));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        Long recipeId = 1L;

        mockMvc.perform(delete("/api/v1/project/recipes/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Recipe with ID " + recipeId + " deleted successfully."));
    }

    // Helper method to convert objects to JSON
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
