package com.cpg.bim.controller.test;
 
import com.cpg.bim.controller.PermroleController;
import com.cpg.bim.entity.PermRole;
import com.cpg.bim.exception.PermroleAlreadyExistsException;
import com.cpg.bim.exception.PermroleNotFoundException;
import com.cpg.bim.service.PermroleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
 
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
class PermroleControllerTest {
 
    @Mock
    private PermroleService permroleService;
 
    @InjectMocks
    private PermroleController permroleController;
 
    private PermRole permrole;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        permrole = new PermRole();
        permrole.setRolenumber(1);
        permrole.setPermrole("Admin");
    }
 
    @Test
    void testAddPerm_Success() {
        // Arrange
        when(permroleService.getPermrole(1)).thenReturn(Optional.empty());
 
        Map<String, String> expectedResponse = new HashMap<>();
        expectedResponse.put("code", "POSTSUCCESS");
        expectedResponse.put("message", "Perm Role added successfully");
 
        // Act
        ResponseEntity<Map<String, String>> response = permroleController.addPerm(permrole);
 
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
 
    @Test
    void testAddPerm_AlreadyExists() {
        // Arrange: Mock the service to simulate that the permrole already exists
        doThrow(PermroleAlreadyExistsException.class).when(permroleService).addPerm(any(PermRole.class));
 
        // Act: Call the controller method
        ResponseEntity<Map<String, String>> response = permroleController.addPerm(permrole);
 
        // Assert: Check that the response status is BAD_REQUEST (400)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
 
        // Check the response body
        assertEquals("ADDFAILS", response.getBody().get("code"));
        assertEquals("Perm Role already exists", response.getBody().get("message"));
    }
 
 
    @Test
    void testGetRole_Success() {
        // Arrange
        when(permroleService.getPermrole(1)).thenReturn(Optional.of(permrole));
 
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("roleNumber", permrole.getRolenumber());
        expectedResponse.put("permRole", permrole.getPermrole());
 
        // Act
        ResponseEntity<Map<String, Object>> response = permroleController.getRole(1);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
 
    @Test
    void testGetRole_NotFound() {
        // Arrange
        when(permroleService.getPermrole(1)).thenThrow(PermroleNotFoundException.class);
 
        // Act
        ResponseEntity<Map<String, Object>> response = permroleController.getRole(1);
 
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
 
    @Test
    void testUpdatePerm_Success() {
        // Arrange
        PermRole updatedPermrole = new PermRole(1, "SuperAdmin");
        when(permroleService.updatePermrole(1, "SuperAdmin")).thenReturn(updatedPermrole);
 
        // Act
        ResponseEntity<String> response = permroleController.updPerm(1, updatedPermrole);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("PermRole updated: SuperAdmin", response.getBody());
    }
 
    @Test
    void testUpdatePerm_NotFound() {
        // Arrange: Make sure the update fails by throwing a PermroleNotFoundException
        when(permroleService.updatePermrole(1, "SuperAdmin")).thenThrow(PermroleNotFoundException.class);
 
        // Act: Call the controller's update method
        ResponseEntity<String> response = permroleController.updPerm(1, new PermRole(1, "SuperAdmin"));
 
        // Assert: The response should indicate that the PermRole was not found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("PermRole with RoleNumber 1 not found.", response.getBody());
    }
 
 
   
   
}