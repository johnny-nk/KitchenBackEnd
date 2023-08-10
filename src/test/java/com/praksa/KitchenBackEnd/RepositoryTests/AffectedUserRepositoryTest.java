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

import com.praksa.KitchenBackEnd.models.entities.AffectedUsers;
import com.praksa.KitchenBackEnd.models.entities.LimitingFactor;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.repositories.AffectedUserRepository;
import com.praksa.KitchenBackEnd.repositories.LimitingFactorRepository;
import com.praksa.KitchenBackEnd.repositories.RegularUserRepository;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AffectedUserRepositoryTest {

    @Autowired
    private AffectedUserRepository affectedUserRepository;
    
    @Autowired
    private RegularUserRepository regularUserRepository;
    
    @Autowired
    private LimitingFactorRepository limitingFactorRepository;
    
    @BeforeEach
    public void setup() {
        // Clear all affected users from the database
        affectedUserRepository.deleteAll();
    }

    @Test
    public void testFindByRegularUserIdAndLimitingFactorId() {
        // Set other regularUser properties here

        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John"); // Set a valid first name
        regularUser.setLastName("Doe");   // Set a valid last name
        regularUser = regularUserRepository.save(regularUser);

        LimitingFactor limitingFactor = new LimitingFactor();
        limitingFactor.setName("Some Limiting Factor");
        // Set other limitingFactor properties here

        limitingFactor = limitingFactorRepository.save(limitingFactor);

        AffectedUsers affectedUser = new AffectedUsers();
        affectedUser.setRegularUser(regularUser);
        affectedUser.setLimitingFactor(limitingFactor);
        // Set other properties for affectedUser

        affectedUser = affectedUserRepository.save(affectedUser);

        Optional<AffectedUsers> foundAffectedUser = affectedUserRepository.findByRegularUserIdAndLimitingFactorId(
            regularUser.getId(), limitingFactor.getId());

        assertTrue(foundAffectedUser.isPresent());
        assertEquals(affectedUser.getId(), foundAffectedUser.get().getId());
    }


    @Test
    public void testFindByRegularUserId() {
        RegularUser regularUser = new RegularUser();
        regularUser.setEmail("user@example.com");
        regularUser.setUsername("username");
        regularUser.setPassword("password");
        regularUser.setFirstName("John");
        regularUser.setLastName("Doe");

        // Save the regularUser in the database
        regularUser = regularUserRepository.save(regularUser);

        AffectedUsers affectedUser1 = new AffectedUsers();
        affectedUser1.setRegularUser(regularUser);
        // Set other properties for affectedUser1
        affectedUserRepository.save(affectedUser1);

        AffectedUsers affectedUser2 = new AffectedUsers();
        affectedUser2.setRegularUser(regularUser);
        // Set other properties for affectedUser2
        affectedUserRepository.save(affectedUser2);

        Set<AffectedUsers> foundUsers = affectedUserRepository.findByRegularUserId(regularUser.getId());
        assertEquals(2, foundUsers.size());
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

        AffectedUsers affectedUser1 = new AffectedUsers();
        affectedUser1.setRegularUser(regularUser);
        // Set other properties for affectedUser1

        AffectedUsers affectedUser2 = new AffectedUsers();
        affectedUser2.setRegularUser(regularUser);
        // Set other properties for affectedUser2

        affectedUserRepository.save(affectedUser1);
        affectedUserRepository.save(affectedUser2);

        affectedUserRepository.deleteAllByRegularUserId(regularUser.getId());

        assertFalse(affectedUserRepository.findByRegularUserId(regularUser.getId()).iterator().hasNext());
    }


}
