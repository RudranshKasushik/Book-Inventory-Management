package com.cpg.bim.service.test;
 
import com.cpg.bim.entity.Users;
import com.cpg.bim.exception.UserNotFoundException;
import com.cpg.bim.repository.UserRepository;
import com.cpg.bim.service.UsersService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class) // This ensures that Mockito annotations work
class UsersServiceTest {
 
    @Mock
    private UserRepository usersRepository; // Mock the repository
 
    @InjectMocks
    private UsersService usersService; // Inject the mock repository into the service
 
    private Users testUser;
 
    @BeforeEach
    void setUp() {
        testUser = new Users();
    }
 
    // Test case for adding a new user
    @Test
    void testAddUser_UserAlreadyExists() {
        // Arrange
        when(usersRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
 
        // Act
        Map<String, String> response = (Map<String, String>) usersService.addUser(testUser);
 
        // Assert
        assertEquals("ADDFAILS", response.get("code"));
        assertEquals("User already exists", response.get("message"));
    }
 
    @Test
    void testAddUser_Success() {
        // Arrange
        when(usersRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(usersRepository.save(testUser)).thenReturn(testUser);
 
        // Act
        Map<String, String> response = (Map<String, String>) usersService.addUser(testUser);
 
        // Assert
        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("User added successfully", response.get("message"));
    }
 
    // Test case for getting user by ID
//    @Test
//    void testGetUserById_UserExists() {
//        // Arrange
//        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.of(testUser));
// 
//        // Act
//        ResponseEntity<?> result = usersService.getUserById(testUser.getUserid());
// 
//        // Assert
//        assertNotNull(result);
//        assertEquals(testUser.getUserid(), ((Users) result).getUserid());
//    }
 
    @Test
    void testGetUserById_UserNotFound() {
        // Arrange
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> usersService.getUserById(testUser.getUserid()));
    }
 
    // Test case for updating first name
    @Test
    void testUpdateFirstName_UserExists() {
        // Arrange
        String newFirstName = "Johnathan";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.of(testUser));
 
        // Act
        usersService.updateFirstName(testUser.getUserid(), newFirstName);
 
        // Assert
        assertEquals(newFirstName, testUser.getFirstname());
        verify(usersRepository, times(1)).save(testUser);
    }
 
    @Test
    void testUpdateFirstName_UserNotFound() {
        // Arrange
        String newFirstName = "Johnathan";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> usersService.updateFirstName(testUser.getUserid(), newFirstName));
    }
 
    // Test case for updating last name
    @Test
    void testUpdateLastName_UserExists() {
        // Arrange
        String newLastName = "Smith";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.of(testUser));
 
        // Act
        usersService.updateLastName(testUser.getUserid(), newLastName);
 
        // Assert
        assertEquals(newLastName, testUser.getLastname());
        verify(usersRepository, times(1)).save(testUser);
    }
 
    @Test
    void testUpdateLastName_UserNotFound() {
        // Arrange
        String newLastName = "Smith";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> usersService.updateLastName(testUser.getUserid(), newLastName));
    }
 
    // Test case for updating phone number
    @Test
    void testUpdatePhoneNumber_UserExists() {
        // Arrange
        String newPhoneNumber = "9876543210";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.of(testUser));
 
        // Act
        usersService.updatePhoneNumber(testUser.getUserid(), newPhoneNumber);
 
        // Assert
        assertEquals(newPhoneNumber, testUser.getPhonenumber());
        verify(usersRepository, times(1)).save(testUser);
    }
 
    @Test
    void testUpdatePhoneNumber_UserNotFound() {
        // Arrange
        String newPhoneNumber = "9876543210";
        when(usersRepository.findById(testUser.getUserid())).thenReturn(Optional.empty());
 
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> usersService.updatePhoneNumber(testUser.getUserid(), newPhoneNumber));
    }
 
    // Test case for login (valid user)
//    @Test
//    void testLogin_ValidUser() {
//        // Arrange
//        String username = "john_doe";
//        String password = "password123";
//        when(usersRepository.findByUserNameAndPassword(username, password)).thenReturn("Valid User");
// 
//        // Act
//        Users result = usersService.findByUserNameAndPassword(username, password);
// 
//        // Assert
//        assertEquals("Valid User", result);
//    }
 
    // Test case for login (invalid user)
    @Test
    void testLogin_InvalidUser() {
        // Arrange
        String username = "invalid_user";
        String password = "wrongpassword";
        when(usersRepository.findByUserNameAndPassword(username, password)).thenReturn(null);
 
        // Act
        Users result = usersService.findByUserNameAndPassword(username, password);
 
        // Assert
        assertEquals("Invalid User", result);
    }
}