package com.cpg.bim.controller;
 
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cpg.bim.entity.PermRole;
import com.cpg.bim.exception.PermroleAlreadyExistsException;
import com.cpg.bim.exception.PermroleNotFoundException;
import com.cpg.bim.service.PermroleService;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/permrole")
public class PermroleController {
 
    @Autowired
    private PermroleService permService;
 
    
// Add new permrole
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addPerm(@RequestBody PermRole perm) {
        Map<String, String> response = new HashMap<>();
        try {
            permService.addPerm(perm);
            // Return success response with code and message
            response.put("code", "POSTSUCCESS");
            response.put("message", "Perm Role added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (PermroleAlreadyExistsException e) {
            // Return failure response when permrole already exists
            response.put("code", "ADDFAILS");
            response.put("message", "Perm Role already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
 
    @GetMapping("/update/permrole/{rolenumber}")
    public ResponseEntity<Map<String, Object>> getRole(@PathVariable("rolenumber") Integer roleNumber) {
        Map<String, Object> response = new HashMap<>();
        try {
            PermRole permrole = permService.getPermrole(roleNumber).orElseThrow();
            
            // Prepare the response
            response.put("roleNumber", permrole.getRolenumber());
            response.put("permRole", permrole.getPermrole());
           // response.put("users", permrole.getUsers());  // Include the users in the response
            
            return ResponseEntity.ok(response);
        } catch (PermroleNotFoundException e) {
            response.put("code", "NOT_FOUND");
            response.put("message", "PermRole with RoleNumber " + roleNumber + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
 
 
 
    // Update permrole by role number
    @PutMapping("/{rolenumber}")
    public ResponseEntity<String> updPerm(@PathVariable("rolenumber") Integer roleNumber, @RequestBody PermRole perm) {
        try {
            PermRole updatedPermrole = permService.updatePermrole(roleNumber, perm.getPermrole());
            return ResponseEntity.ok("PermRole updated: " + updatedPermrole.getPermrole());
        } catch (PermroleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PermRole with RoleNumber " + roleNumber + " not found.");
        }
    }
}