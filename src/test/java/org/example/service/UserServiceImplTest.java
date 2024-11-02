package org.example.service;

import org.example.io.UsersDatabase;
import org.example.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Adilson");
        user.put("lastName", "Fuxe");

        createdUserId = userService.createUser(user);

        Assertions.assertNotNull(createdUserId);
    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Lucas");
        newUserDetails.put("lastName", "Almeida");

        Map<String, String> updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        Assertions.assertEquals(newUserDetails.get("firstName"), updatedUserDetails.get("firstName"));
        Assertions.assertEquals(newUserDetails.get("lastName"), updatedUserDetails.get("lastName"));
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        Map<String, String> user = userService.getUserDetails(createdUserId);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(createdUserId, user.get("userId"));
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        userService.deleteUser(createdUserId);
        Map<String, String> user = userService.getUserDetails(createdUserId);
        Assertions.assertNull(user);
    }

}
