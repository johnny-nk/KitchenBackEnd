package com.praksa.KitchenBackEnd.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.Recipe;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private RegularUserRepository regularUserRepository;
    @Autowired
    private LikedRecipesRepository likedRecipesRepository;
    
    private RegularUser regularUser;

    @BeforeEach
    public void setup() {
        // Clear all recipes, regular users, and liked recipes from the database
        recipeRepository.deleteAll();
        regularUserRepository.deleteAll();
        likedRecipesRepository.deleteAll();

        // Create and save a regular user
        regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John");
        regularUser.setLastName("Doe");
        regularUserRepository.save(regularUser);
    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Delicious Pizza Recipe");
        recipe1.setDescription("This is a delicious recipe.");
        recipe1.setSteps("1. Preheat the oven...");
        // Set other properties for recipe1
        recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Delicious Recipe");
        recipe2.setDescription("This is a delicious recipe2.");
        recipe2.setSteps("1. Preheat the oven2...");
        // Set other properties for recipe2
        recipeRepository.save(recipe2);

        List<Recipe> foundRecipes = recipeRepository.findByTitleContainingIgnoreCase("pizza");
        assertEquals(1, foundRecipes.size()); // You expect only 1 result
        assertEquals("Delicious Pizza Recipe", foundRecipes.get(0).getTitle());
    }

//    @Test
//    public void testGetLikedRecipes() {
//        Recipe recipe1 = new Recipe();
//        recipe1.setTitle("Recipe 1");
//        recipeRepository.save(recipe1);
//
//        LikedRecipes likedRecipe = new LikedRecipes();
//        likedRecipe.setRegularUser(regularUser);
//        likedRecipe.setRecipe(recipe1);
//        likedRecipesRepository.save(likedRecipe);
//
//        Set<Recipe> expectedLikedRecipes = new HashSet<>();
//        expectedLikedRecipes.add(recipe1);
//
//        Set<Recipe> actualLikedRecipes = getLikedRecipes(regularUser);
//
//        assertEquals(expectedLikedRecipes, actualLikedRecipes);
//    }
//
//    private Set<Recipe> getLikedRecipes(RegularUser user) {
//        return user.getLikedRecipes().stream()
//                .map(LikedRecipes::getRecipe)
//                .collect(Collectors.toSet());
//    }
    

//    @Test
//    public void testFindByRegularUserLikedRecipesRegularUserId() {
//        Recipe recipe1 = new Recipe();
//        // ... set properties for recipe1 ...
//        recipeRepository.save(recipe1);
//
//        regularUser.getLikedRecipes().add(new LikedRecipes(regularUser, recipe1));
//        regularUserRepository.save(regularUser);
//
//        List<Recipe> foundRecipes = recipeRepository.findByRegularUserLikedRecipesRegularUserId(regularUser.getId());
//
//        assertEquals(1, foundRecipes.size());
//        assertEquals(recipe1.getId(), foundRecipes.get(0).getId());
//    }


}

