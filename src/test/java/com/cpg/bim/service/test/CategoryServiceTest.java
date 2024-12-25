package com.cpg.bim.service.test;


import com.cpg.bim.dto.CategoryDTO;
import com.cpg.bim.entity.Category;
import com.cpg.bim.exception.CategoryAlreadyExistsException;
import com.cpg.bim.exception.CategoryNotFoundException;
import com.cpg.bim.repository.CategoryRepository;
import com.cpg.bim.service.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

   @Mock
   private CategoryRepository categoryRepository;

   @InjectMocks
   private CategoryService categoryService;

   private CategoryDTO categoryDTO;

   @BeforeEach
   void setUp() {
       categoryDTO = new CategoryDTO(1, "Test Category");
   }

   @Test
   void addCategory_Success() {
       // Arrange
       Category category = new Category(1, "Test Category");
       when(categoryRepository.existsById(categoryDTO.getId())).thenReturn(false);
       when(categoryRepository.save(any(Category.class))).thenReturn(category);

       // Act
       ResponseEntity<String> response = categoryService.addCategory(categoryDTO);

       // Assert
       assertEquals(200, response.getStatusCodeValue());
       assertEquals("Category added successfully", response.getBody());
   }

   @Test
   void addCategory_CategoryAlreadyExists() {
       // Arrange
       when(categoryRepository.existsById(categoryDTO.getId())).thenReturn(true);

       // Act & Assert
       assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.addCategory(categoryDTO));
   }

   @Test
   void getCategoryById_Success() {
       // Arrange
       Category category = new Category(1, "Test Category");
       when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));

       // Act
       ResponseEntity<CategoryDTO> response = categoryService.getCategoryById(1);

       // Assert
       assertEquals(200, response.getStatusCodeValue());
       assertEquals("Test Category", response.getBody().getDescription());
   }

   @Test
   void getCategoryById_NotFound() {
       // Arrange
       when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());

       // Act & Assert
       assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(1));
   }

   @Test
   void updateCategoryDescription_Success() {
       // Arrange
       Category category = new Category(1, "Test Category");
       when(categoryRepository.findById(1)).thenReturn(java.util.Optional.of(category));
       when(categoryRepository.save(any(Category.class))).thenReturn(category);

       // Act
       ResponseEntity<CategoryDTO> response = categoryService.updateCategoryDescription(1, "Updated Category");

       // Assert
       assertEquals(200, response.getStatusCodeValue());
       assertEquals("Updated Category", response.getBody().getDescription());
   }

   @Test
   void updateCategoryDescription_CategoryNotFound() {
       // Arrange
       when(categoryRepository.findById(1)).thenReturn(java.util.Optional.empty());

       // Act & Assert
       assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategoryDescription(1, "Updated Category"));
   }
}