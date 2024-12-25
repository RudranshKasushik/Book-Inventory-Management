package com.cpg.bim.service;
 
import java.util.ArrayList;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.cpg.bim.dto.CategoryDTO;
import com.cpg.bim.entity.Category;
import com.cpg.bim.exception.CategoryAlreadyExistsException;
import com.cpg.bim.exception.CategoryNotFoundException;
import com.cpg.bim.repository.CategoryRepository;
 
@Service
public class CategoryService
{
   @Autowired
   CategoryRepository categoryRepository;
     
// Helper method to convert entity to DTO
   private CategoryDTO convertToDto(Category category)
   {
       return new CategoryDTO(category.getId(), category.getDescription());
   }
 
   // Helper method to convert DTO to entity
   private Category convertToEntity(CategoryDTO categoryDTO)
   {
       return new Category(categoryDTO.getId(), categoryDTO.getDescription());
   }
   
   @Transactional
   public ResponseEntity<String> addCategory(CategoryDTO categoryDTO) {
       // Check if the category already exists
       if (categoryRepository.existsById(categoryDTO.getId()))
       {
    	   throw new CategoryAlreadyExistsException("Category with ID " + categoryDTO.getId() + " already exists.");
       }
 
       // Convert DTO to entity
       Category category = convertToEntity(categoryDTO);
 
       // Save the new category
       categoryRepository.save(category);
 
       // Return success response
       return ResponseEntity.ok("Category added successfully");
   }
   
   @Transactional(readOnly = true)
   public ResponseEntity<CategoryDTO> getCategoryById(int catId)
   {
       // Retrieve the category by ID
       Category category = categoryRepository.findById(catId).orElseThrow(
               () -> new CategoryNotFoundException("Category with ID " + catId + " not found.") );
 
 
       // Convert entity to DTO and return it wrapped in ResponseEntity
       return ResponseEntity.ok(convertToDto(category));
   }
 
   @Transactional
   public ResponseEntity<CategoryDTO> updateCategoryDescription(int catId, String description)
   {
       // Find the category by ID
       Category category = categoryRepository.findById(catId).orElseThrow(
               () -> new CategoryNotFoundException("Category with ID " + catId + " not found."));
 
 
       // Trim and update the description
       description = description.trim();
       category.setDescription(description);
 
       // Save the updated category
       categoryRepository.save(category);
 
       // Convert the updated entity to DTO and return it in the response
       CategoryDTO updatedCategoryDTO = convertToDto(category);
 
       // Return the updated category in the response with 200 OK
       return ResponseEntity.ok(updatedCategoryDTO);
   }
   
   @Transactional(readOnly = true)
   public ResponseEntity<List<CategoryDTO>> getAllCategories() {
       // Retrieve all categories from the repository
       List<Category> categories = categoryRepository.findAll();
 
       // Create a new list to store the CategoryDTOs
       List<CategoryDTO> categoryDTOs = new ArrayList<>();
 
       // Manually iterate over the categories and convert them to CategoryDTO
       for (Category category : categories) {
           categoryDTOs.add(convertToDto(category));
       }
 
       // Return the list of CategoryDTOs
       return ResponseEntity.ok(categoryDTOs);
   }
 
 
 
}
 