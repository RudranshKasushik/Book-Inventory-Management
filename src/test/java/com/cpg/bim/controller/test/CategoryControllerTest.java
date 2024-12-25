package com.cpg.bim.controller.test;

import com.cpg.bim.controller.CategoryController;
import com.cpg.bim.dto.CategoryDTO;
import com.cpg.bim.exception.CategoryAlreadyExistsException;
import com.cpg.bim.exception.CategoryNotFoundException;
import com.cpg.bim.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
 
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
 
class CategoryControllerTest {
 
    @Mock
    private CategoryService categoryService;
 
    @InjectMocks
    private CategoryController categoryController;
 
    private CategoryDTO categoryDTO;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        categoryDTO = new CategoryDTO(1, "Test Category");
    }
 
    @Test
    void addCategory_Success() {
        // Arrange
        when(categoryService.addCategory(categoryDTO)).thenReturn(ResponseEntity.ok("Category added successfully"));
 
        // Act
        ResponseEntity<String> response = categoryController.addCategory(categoryDTO);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category added successfully", response.getBody());
 
        verify(categoryService, times(1)).addCategory(categoryDTO);
    }
 
    @Test
    void addCategory_CategoryAlreadyExists() {
        // Arrange
        when(categoryService.addCategory(categoryDTO)).thenThrow(new CategoryAlreadyExistsException("Category with ID 1 already exists."));
 
        // Act & Assert
        Exception exception = assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryController.addCategory(categoryDTO);
        });
 
        assertEquals("Category with ID 1 already exists.", exception.getMessage());
        verify(categoryService, times(1)).addCategory(categoryDTO);
    }
 
    @Test
    void getCategoryById_Success() {
        // Arrange
        when(categoryService.getCategoryById(1)).thenReturn(ResponseEntity.ok(categoryDTO));
 
        // Act
        ResponseEntity<CategoryDTO> response = categoryController.getCategoryById(1);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Test Category", response.getBody().getDescription());
 
        verify(categoryService, times(1)).getCategoryById(1);
    }
 
    @Test
    void getCategoryById_NotFound() {
        // Arrange
        when(categoryService.getCategoryById(1)).thenThrow(new CategoryNotFoundException("Category with ID 1 not found."));
 
        // Act & Assert
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.getCategoryById(1);
        });
 
        assertEquals("Category with ID 1 not found.", exception.getMessage());
        verify(categoryService, times(1)).getCategoryById(1);
    }
 
    @Test
    void updateCategoryDescription_Success() {
        // Arrange
        CategoryDTO updatedCategoryDTO = new CategoryDTO(1, "Updated Category");
        when(categoryService.updateCategoryDescription(1, "Updated Category")).thenReturn(ResponseEntity.ok(updatedCategoryDTO));
 
        // Act
        ResponseEntity<CategoryDTO> response = categoryController.updateCategoryDescription(1, "Updated Category");
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Updated Category", response.getBody().getDescription());
 
        verify(categoryService, times(1)).updateCategoryDescription(1, "Updated Category");
    }
 
    @Test
    void updateCategoryDescription_CategoryNotFound() {
        // Arrange
        when(categoryService.updateCategoryDescription(1, "Updated Category")).thenThrow(new CategoryNotFoundException("Category with ID 1 not found."));
 
        // Act & Assert
        Exception exception = assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.updateCategoryDescription(1, "Updated Category");
        });
 
        assertEquals("Category with ID 1 not found.", exception.getMessage());
        verify(categoryService, times(1)).updateCategoryDescription(1, "Updated Category");
    }
}
