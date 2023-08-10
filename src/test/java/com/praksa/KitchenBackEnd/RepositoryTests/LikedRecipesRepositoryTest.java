package com.praksa.KitchenBackEnd.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.praksa.KitchenBackEnd.models.entities.LikedRecipes;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.LikedRecipesRepository;
import com.praksa.KitchenBackEnd.repositories.RecipeRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;
import com.praksa.KitchenBackEnd.models.entities.Recipe;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LikedRecipesRepositoryTest {

    @Autowired
    private LikedRecipesRepository likedRecipesRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() {
        // Clear all liked recipes from the database
        likedRecipesRepository.deleteAll();
        regularUserRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    public void testFindByRegularUserId() {
        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John"); // Set a valid first name
        regularUser.setLastName("Doe");   // Set a valid last name
        regularUser = regularUserRepository.save(regularUser);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Recipe 1");
        recipe1.setDescription("Test description"); // Set a valid description
        recipe1.setSteps("Step 1, Step 2"); // Set valid steps
        // Set other properties for recipe1
        recipe1 = recipeRepository.save(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Recipe 2");
        recipe2.setDescription("Test description"); // Set a valid description
        recipe2.setSteps("Step 1, Step 2"); // Set valid steps
        // Set other properties for recipe2
        recipe2 = recipeRepository.save(recipe2);

        LikedRecipes likedRecipe1 = new LikedRecipes();
        likedRecipe1.setRegularUser(regularUser);
        likedRecipe1.setRecipe(recipe1);
        // Set other properties for likedRecipe1
        likedRecipesRepository.save(likedRecipe1);

        LikedRecipes likedRecipe2 = new LikedRecipes();
        likedRecipe2.setRegularUser(regularUser);
        likedRecipe2.setRecipe(recipe2);
        // Set other properties for likedRecipe2
        likedRecipesRepository.save(likedRecipe2);

        Set<LikedRecipes> foundLikedRecipes = likedRecipesRepository.findByRegularUserId(regularUser.getId());
        assertEquals(2, foundLikedRecipes.size());
    }

    @Test
    public void testFindByRegularUserIdAndRecipeId() {
        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John"); // Set a valid first name
        regularUser.setLastName("Doe");   // Set a valid last name
        regularUser = regularUserRepository.save(regularUser);

        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description"); // Set a valid description
        recipe.setSteps("Step 1, Step 2"); // Set valid steps
        // Set other properties for recipe
        recipe = recipeRepository.save(recipe);

        LikedRecipes likedRecipe = new LikedRecipes();
        likedRecipe.setRegularUser(regularUser);
        likedRecipe.setRecipe(recipe);
        // Set other properties for likedRecipe
        likedRecipesRepository.save(likedRecipe);

        Optional<LikedRecipes> foundLikedRecipe = likedRecipesRepository.findByRegularUserIdAndRecipeId(
            regularUser.getId(), recipe.getId());

        assertTrue(foundLikedRecipe.isPresent());
        assertEquals(likedRecipe.getId(), foundLikedRecipe.get().getId());
    }

    @Test
    public void testDeleteAllByRegularUserId() {
        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John"); // Set a valid first name
        regularUser.setLastName("Doe");   // Set a valid last name
        regularUser = regularUserRepository.save(regularUser);

        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description"); // Set a valid description
        recipe.setSteps("Step 1, Step 2"); // Set valid steps
        // Set other properties for recipe
        recipe = recipeRepository.save(recipe);

        LikedRecipes likedRecipe1 = new LikedRecipes();
        likedRecipe1.setRegularUser(regularUser);
        likedRecipe1.setRecipe(recipe);
        // Set other properties for likedRecipe1
        likedRecipesRepository.save(likedRecipe1);

        LikedRecipes likedRecipe2 = new LikedRecipes();
        likedRecipe2.setRegularUser(regularUser);
        likedRecipe2.setRecipe(recipe);
        // Set other properties for likedRecipe2
        likedRecipesRepository.save(likedRecipe2);

        likedRecipesRepository.deleteAllByRegularUserId(regularUser.getId());

        assertFalse(likedRecipesRepository.findByRegularUserId(regularUser.getId()).iterator().hasNext());
    }

    @Test
    public void testDeleteAllByRecipeId() {
        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John"); // Set a valid first name
        regularUser.setLastName("Doe");   // Set a valid last name
        regularUser = regularUserRepository.save(regularUser);

        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description"); // Set a valid description
        recipe.setSteps("Step 1, Step 2"); // Set valid steps
        // Set other properties for recipe
        recipe = recipeRepository.save(recipe);

        LikedRecipes likedRecipe1 = new LikedRecipes();
        likedRecipe1.setRegularUser(regularUser);
        likedRecipe1.setRecipe(recipe);
        // Set other properties for likedRecipe1
        likedRecipesRepository.save(likedRecipe1);

        LikedRecipes likedRecipe2 = new LikedRecipes();
        likedRecipe2.setRegularUser(regularUser);
        likedRecipe2.setRecipe(recipe);
        // Set other properties for likedRecipe2
        likedRecipesRepository.save(likedRecipe2);

        likedRecipesRepository.deleteAllByRecipeId(recipe.getId());

        assertFalse(likedRecipesRepository.findByRegularUserId(regularUser.getId()).iterator().hasNext());
    }
}
