package com.cpg.bim.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpg.bim.entity.State;
import com.cpg.bim.exception.StateAlreadyExistsException;
import com.cpg.bim.exception.StateNotFoundException;
import com.cpg.bim.repository.StateRepository;

import ch.qos.logback.classic.Logger;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;
    
    @Transactional
    public boolean addState(State state) {
    	Optional<State> state1 = stateRepository.findByStateCode(state.getStateCode());
        if (state1.isPresent()) {
            throw new StateAlreadyExistsException("State already exists with ID: " + state.getStateCode());
        }
        stateRepository.save(state);
        return true;
    }
    @Transactional(readOnly = true)
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<List<State>> getByStateId(String stateCode) throws StateNotFoundException {
        Optional<List<State>> state = stateRepository.findByStateCode1(stateCode);
        if (state.isPresent()) {
            return state; 
        } else {
            throw new StateNotFoundException("State not found for the provided code: " + stateCode);
        }
    }
    
    @Transactional
    public State updateStateName(String stateCode, State state) throws StateNotFoundException {
        Optional<State> existingState = stateRepository.findByStateCode(stateCode);
        if (existingState.isPresent()) {
            State stateToUpdate = existingState.get();
            stateToUpdate.setStateName(state.getStateName());
            return stateRepository.save(stateToUpdate);
        } else {
            throw new StateNotFoundException("State with code " + stateCode + " not found.");
        }
    }
}
