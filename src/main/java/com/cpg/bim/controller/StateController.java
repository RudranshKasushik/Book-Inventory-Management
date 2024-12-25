package com.cpg.bim.controller;

//package com.cpg.Book_Inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.cpg.bim.entity.State;
import com.cpg.bim.exception.StateAlreadyExistsException;
import com.cpg.bim.exception.StateExceptionResponse;
import com.cpg.bim.exception.StateNotFoundException;
import com.cpg.bim.service.StateService;

import java.nio.file.FileAlreadyExistsException;

//import com.cpg.Book_Inventory.entity.State;
//
//import com.cpg.Book_Inventory.exception.StateAddedSuccessfullyException;
//
//import com.cpg.Book_Inventory.exception.StateAlreadyExistsException;
//
//import com.cpg.Book_Inventory.service.StateService;
 
import java.util.List;
import java.util.Optional;
 
@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/post")// add state
    public ResponseEntity<StateExceptionResponse> addState(@RequestBody State state) throws Exception {
        try {
        	if(state.getStateCode()=="") {
        		throw new NullPointerException("State code is empty");
        	}
			stateService.addState(state);  // If successful, will return true
            return new ResponseEntity<>(new StateExceptionResponse("POSTSUCCESS", "State Added Successfully"),HttpStatus.CREATED);  // State added successfully, return 201 status
        } catch (StateAlreadyExistsException ex) {
            // If the state already exists, handle the error
            return new ResponseEntity<>(new StateExceptionResponse("ADDFAILS", ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<State>> getAllStates() {
        List<State> states = stateService.getAllStates();  // Fetches all states from the service
        if (states.isEmpty()) {
            throw new StateNotFoundException("No states found in the database.");  // Throw exception if no states are found
        }
        return new ResponseEntity<>(states, HttpStatus.OK);  // Return states with a 200 OK status
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<List<State>> getStateById(@PathVariable("stateId") String stateCode) {
            Optional<List<State>> state = stateService.getByStateId(stateCode);
            return state.map(stateFound -> new ResponseEntity<>(stateFound, HttpStatus.OK))
                        .orElseThrow(() -> new StateNotFoundException("State not found for the provided code",HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/name/{stateId}")
    public ResponseEntity<?> updateState(@PathVariable("stateId") String stateId, @RequestBody State state) {
        try {
            State updatedState = stateService.updateStateName(stateId, state);
            return new ResponseEntity<>(updatedState, HttpStatus.OK);
        } catch (StateNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}

 