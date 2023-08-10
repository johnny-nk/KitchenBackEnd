package com.praksa.KitchenBackEnd.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingIngredientRepository;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LimitingFactorRepositoryTest {

    @Autowired
    private LimitingFactorRepository limitingFactorRepository;

    @Autowired
    private LimitingIngredientRepository limitingIngredientRepository;
 

    @BeforeEach
    public void setup() {
        // Clear all limiting factors and ingredients from the database
        limitingFactorRepository.deleteAll();
        limitingIngredientRepository.deleteAll();
    }


    @Test
    public void testFindByName() {
        LimitingFactor limitingFactor = new LimitingFactor();
        limitingFactor.setName("Test Factor");
        limitingFactorRepository.save(limitingFactor);

        LimitingFactor foundFactor = limitingFactorRepository.findByName("Test Factor");
        assertEquals(limitingFactor.getId(), foundFactor.getId());
    }

    @Test
    public void testExistsByName() {
        LimitingFactor limitingFactor = new LimitingFactor();
        limitingFactor.setName("Test Factor");
        limitingFactorRepository.save(limitingFactor);

        boolean exists = limitingFactorRepository.existsByName("Test Factor");
        assertTrue(exists);

        exists = limitingFactorRepository.existsByName("Nonexistent Factor");
        assertFalse(exists);
    }
}
