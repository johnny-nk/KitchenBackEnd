package com.praksa.KitchenBackEnd.RepositoryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.praksa.KitchenBackEnd.models.entities.User;
import com.praksa.KitchenBackEnd.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        // Disable foreign key constraints
        Session session = entityManager.unwrap(Session.class);
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

        // Clear all users from the database
        userRepository.deleteAll();

        // Enable foreign key constraints
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    @Test
    public void testFindByUsername() {
        // Create a user and set the password
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword"); // Set a valid password
        userRepository.save(user);

        // Find the user by username
        User foundUser = userRepository.findByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testFindAll() {
        // Create some users and set their passwords
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("password1"); // Set a valid password
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("password2"); // Set a valid password
        userRepository.save(user2);

        // Find all users in the repository
        Iterable<User> users = userRepository.findAll();

        // Make assertions about the retrieved users
        assertNotNull(users);
        int count = 0;
        for (User user : users) {
            count++;
        }
        assertEquals(2, count);
    }
}
