package com.cpg.bim.controller;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cpg.bim.dto.CategoryDTO;
import com.cpg.bim.service.CategoryService;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/category")
public class CategoryController 
{
 
	@Autowired
	CategoryService categoryService;
	// Add a new category
    @PostMapping("/post")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDTO) 
    {
        // Delegate to the service and return its response
        return categoryService.addCategory(categoryDTO);
    }
// Get category by ID
    @GetMapping("/update/description/{catId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int catId) {
        // Delegate to the service and return its response
        return categoryService.getCategoryById(catId);
    }
    // Update category description
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDTO> updateCategoryDescription(@PathVariable int catId, @RequestBody @Validated String description) 
    {
        // Delegate to the service and return its response
        return categoryService.updateCategoryDescription(catId, description);
    }
// Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        // Delegate to the service and return its response
        return categoryService.getAllCategories();
    }
 
	
}