package com.praksa.KitchenBackEnd.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
	
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.praksa.KitchenBackEnd.models.entities.Ingredient;
import com.praksa.KitchenBackEnd.repositories.IngredientRepository;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testFindByNameStartingWith() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("Apple");
        // Set other properties for ingredient1
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("Banana");
        // Set other properties for ingredient2
        ingredientRepository.save(ingredient2);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setName("Orange");
        // Set other properties for ingredient3
        ingredientRepository.save(ingredient3);

        // Test the findByNameStartingWith method
        Ingredient foundIngredient = ingredientRepository.findByNameStartingWith("A");
        assertNotNull(foundIngredient);
        assertEquals("Apple", foundIngredient.getName());

        Ingredient notFoundIngredient = ingredientRepository.findByNameStartingWith("C");
        assertNull(notFoundIngredient);
    }
}
