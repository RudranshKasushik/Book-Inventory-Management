package com.cpg.bim.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cpg.bim.entity.Users;
import com.cpg.bim.service.UsersService;
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
 
    // Add new user object
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody Users users) {
        return usersService.addUser(users);
    }
 
    // Get user by ID
    @GetMapping("/getuser/{userid}")
    public ResponseEntity<?> getUserById(@PathVariable int userid) {
        return usersService.getUserById(userid);  // This should trigger the exception if not found
    }
 
    // Update first name by id
    @PutMapping("/update/firstname/{userid}")
    public ResponseEntity<?> updateFirstName(@PathVariable int userid, @RequestBody String firstname) {
        return usersService.updateFirstName(userid, firstname);
    }
 
    // Update last name by id
    @PutMapping("/update/lastname/{userid}")
    public ResponseEntity<?> updateLastName(@PathVariable int userid, @RequestBody String lastname) {
        return usersService.updateLastName(userid, lastname);
    }
 
    // Update phone number by id
    @PutMapping("/update/phonenumber/{userid}")
    public ResponseEntity<?> updatePhoneNumber(@PathVariable int userid, @RequestBody String phonenumber) {
        return usersService.updatePhoneNumber(userid, phonenumber);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody Users user) {
        Users authenticatedUser = usersService.findByUserNameAndPassword(user.getUsername(), user.getPassword());
        if (authenticatedUser == null) {
            // If the user is not found, return "Invalid User"
            return new ResponseEntity<>(Map.of("message", "Invalid User"), HttpStatus.UNAUTHORIZED);
        } else {
            // Return the user details along with the userid if login is successful
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Valid User");
            response.put("userid", authenticatedUser.getUserid());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    
//    @PostMapping("/login")
//	public ResponseEntity<String> authenticate(@RequestBody Users user)
//	{
//		String response = usersService.findByUserNameAndPassword(user.getUsername(), user.getPassword());
//		if (response.equals("Invalid User")) {
//	        return new ResponseEntity<>("Invalid User", HttpStatus.UNAUTHORIZED);
//	    } else {
//	        return new ResponseEntity<>("Valid User", HttpStatus.OK);
//	    }
//	}
}