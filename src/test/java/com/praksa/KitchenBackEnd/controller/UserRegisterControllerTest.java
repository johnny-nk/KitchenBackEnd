package com.praksa.KitchenBackEnd.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praksa.KitchenBackEnd.controllers.UserRegisterController;
import com.praksa.KitchenBackEnd.models.dto.AdminRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.CookRegisterDTO;
import com.praksa.KitchenBackEnd.models.dto.RegularUserRegisterDTO;
import com.praksa.KitchenBackEnd.models.entities.Administrator;
import com.praksa.KitchenBackEnd.models.entities.Cook;
import com.praksa.KitchenBackEnd.models.entities.RegularUser;
import com.praksa.KitchenBackEnd.runtimeException.UserNotFoundException;
import com.praksa.KitchenBackEnd.services.UserService;

public class UserRegisterControllerTest {

    @InjectMocks
    private UserRegisterController userRegisterController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(userRegisterController).build();
    }

    @Test
    void testRegisterAdmin() throws Exception {
        AdminRegisterDTO adminDTO = new AdminRegisterDTO();
        adminDTO.setUsername("admin");
        adminDTO.setPassword("admin123");

        // Create a mock Administrator instance using the adminDTO
        Administrator administrator = new Administrator();
        administrator.setUsername(adminDTO.getUsername());
        administrator.setPassword(adminDTO.getPassword());

        // Mock the userService.addAdmin() method
        when(userService.addAdmin(any(AdminRegisterDTO.class))).thenReturn(administrator);

        mockMvc.perform(post("/api/v1/project/register/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(adminDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(administrator))); // Use the administrator instance here
    }

    @Test
    public void testRegisterRegUser() {
        RegularUserRegisterDTO mockDTO = new RegularUserRegisterDTO(); // Create a mock DTO
        RegularUser mockUser = new RegularUser(); // Create a mock RegularUser
        when(userService.addUser(any(RegularUserRegisterDTO.class))).thenReturn(mockUser);

        ResponseEntity<?> responseEntity = userRegisterController.registerRegUser(mockDTO);

        verify(userService, times(1)).addUser(mockDTO); // Verify that userService.addUser was called
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // Check the response status
        assertEquals(mockUser, responseEntity.getBody()); // Check the response body
    }
    
    @Test
    public void testRegisterCook() {
        CookRegisterDTO mockDTO = new CookRegisterDTO(); // Create a mock DTO
        Cook mockCook = new Cook(); // Create a mock Cook
        when(userService.addCook(any(CookRegisterDTO.class))).thenReturn(mockCook);

        ResponseEntity<?> responseEntity = userRegisterController.registerCook(mockDTO);

        verify(userService, times(1)).addCook(mockDTO); // Verify that userService.addCook was called
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()); // Check the response status
        assertEquals(mockCook, responseEntity.getBody()); // Check the response body
    }
    
    @Test
    public void testUpdateRegularUser() {
        RegularUserRegisterDTO mockDTO = new RegularUserRegisterDTO();
        Principal mockPrincipal = mock(Principal.class);

        // Configure the mock behavior to throw a RuntimeException
        when(userService.updateUser(any(RegularUserRegisterDTO.class), anyString())).thenThrow(new RuntimeException());

        ResponseEntity<?> responseEntity = userRegisterController.updateRegularUser(mockDTO, mockPrincipal);

        verify(userService, times(1)).updateUser(mockDTO, mockPrincipal.getName());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testUpdateRegularUser_UserNotFoundException() {
        RegularUserRegisterDTO mockDTO = new RegularUserRegisterDTO();
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username"); // Set up the behavior of mockPrincipal
        when(userService.updateUser(any(RegularUserRegisterDTO.class), anyString()))
            .thenThrow(new UserNotFoundException());

        ResponseEntity<?> responseEntity = userRegisterController.updateRegularUser(mockDTO, mockPrincipal);

        verify(userService, times(1)).updateUser(mockDTO, mockPrincipal.getName());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User not found with username: " + mockPrincipal.getName(), responseEntity.getBody());
    }

    @Test
    public void testUpdateRegularUser_Exception() {
        RegularUserRegisterDTO mockDTO = new RegularUserRegisterDTO();
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username"); // Set up the behavior of mockPrincipal
        when(userService.updateUser(any(RegularUserRegisterDTO.class), anyString())).thenThrow(new RuntimeException());

        ResponseEntity<?> responseEntity = userRegisterController.updateRegularUser(mockDTO, mockPrincipal);

        verify(userService, times(1)).updateUser(mockDTO, mockPrincipal.getName());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred", responseEntity.getBody());
    }
    
    @Test
    public void testUpdateRegularUser_Success() {
        RegularUserRegisterDTO mockDTO = new RegularUserRegisterDTO();
        RegularUserRegisterDTO updatedMockDTO = new RegularUserRegisterDTO();
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("username");
        when(userService.updateUser(any(RegularUserRegisterDTO.class), anyString())).thenReturn(updatedMockDTO);

        ResponseEntity<?> responseEntity = userRegisterController.updateRegularUser(mockDTO, mockPrincipal);

        verify(userService, times(1)).updateUser(mockDTO, mockPrincipal.getName());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedMockDTO, responseEntity.getBody());
    }
    
    
    @Test
    public void testDeleteRegularUser_Success() throws Exception {
        Long userId = 16L;

        // Mock the deleteRegularUser method to return null (as in your implementation)
        when(userService.deleteRegularUser(userId)).thenReturn(null);

        mockMvc.perform(delete("/deleteRegUserFromDB/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User was deleted"));

        verify(userService, times(1)).deleteRegularUser(userId);
    }

    
    // Helper method to convert objects to JSON
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
