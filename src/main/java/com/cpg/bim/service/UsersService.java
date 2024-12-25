package com.cpg.bim.service;
import com.cpg.bim.entity.Users;
import com.cpg.bim.exception.UserNotFoundException;
import com.cpg.bim.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class UsersService {
 
    @Autowired
    private UserRepository usersRepository;
 
    // Add new user
    public ResponseEntity<?> addUser(Users users) {
        if (usersRepository.existsByUsername(users.getUsername())) {
            Map<String, String> response = new HashMap<>();
            response.put("code", "ADDFAILS");
            response.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        usersRepository.save(users);
        Map<String, String> response = new HashMap<>();
        response.put("code", "POSTSUCCESS");
        response.put("message", "User added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
 
    // Get user by ID
    public ResponseEntity<?> getUserById(int userid) {
        Optional<Users> user = usersRepository.findById(userid);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            // Log the exception
            throw new UserNotFoundException("User with ID " + userid + " not found.");
        }
    }
 
 
    // Update first name by id
    public ResponseEntity<?> updateFirstName(int userid, String firstname) {
        Optional<Users> user = usersRepository.findById(userid);
        if (user.isPresent()) {
            Users existingUser = user.get();
            existingUser.setFirstname(firstname.trim());
            usersRepository.save(existingUser);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException("User with ID " + userid + " not found.");
        }
    }
 
    // Update last name by id
    public ResponseEntity<?> updateLastName(int userid, String lastname) {
        Optional<Users> user = usersRepository.findById(userid);
        if (user.isPresent()) {
            Users existingUser = user.get();
            existingUser.setLastname(lastname.trim());
            usersRepository.save(existingUser);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException("User with ID " + userid + " not found.");
        }
    }
 
    // Update phone number by id
    public ResponseEntity<?> updatePhoneNumber(int userid, String phonenumber) {
        Optional<Users> user = usersRepository.findById(userid);
        if (user.isPresent()) {
            Users existingUser = user.get();
            existingUser.setPhonenumber(phonenumber.trim());
            usersRepository.save(existingUser);
            return ResponseEntity.ok().build();
        } else {
            throw new UserNotFoundException("User with ID " + userid + " not found.");
        }
    }
    @Transactional
    public Users findByUserNameAndPassword(String username, String password) {
        return usersRepository.findByUserNameAndPassword(username, password);
    }

    
    // login
//    @Transactional
//	public String findByUserNameAndPassword(String username,String password)
//	{
//		String user = usersRepository.findByUserNameAndPassword(username,password);
//		return (user != null) ? user : "Invalid User"; 	
//	}
}