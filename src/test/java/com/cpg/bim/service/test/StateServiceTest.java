package com.cpg.bim.service.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cpg.bim.entity.State;
import com.cpg.bim.repository.StateRepository;
import com.cpg.bim.service.StateService;
 
 
@ExtendWith(MockitoExtension.class)
public class StateServiceTest {
 
    @Mock
    private StateRepository stateRepository;
 
    @InjectMocks
    private StateService stateService;
 
    State s1 = new State("KA", "Karnataka");
    State s2 = new State("TN", "Tamil Nadu");
    State s3 = new State("MH", "Maharashtra");
 
    @Test
    public void testAddState() {
        // Simulate that state "KA" doesn't already exist in the database
        when(stateRepository.findByStateCode(s1.getStateCode())).thenReturn(Optional.empty());
        when(stateRepository.save(s1)).thenReturn(s1);
 
        // Test the addState method
        boolean isAdded = stateService.addState(s1);
        assertTrue(isAdded);
 
        // Verify the save method was called
        verify(stateRepository, times(1)).save(s1);
    }
 
 
    @Test
    public void testGetAllStates() {
        // Simulate a list of states being returned from the repository
        when(stateRepository.findAll()).thenReturn(Arrays.asList(s1, s2));
 
        // Test the getAllStates method
        assertEquals(2, stateService.getAllStates().size());
    }
 
    @Test
    public void testGetByStateId() {
        when(stateRepository.findByStateCode1(s1.getStateCode())).thenReturn(Optional.of(Arrays.asList(s1)));
        Optional<List<State>> state = stateService.getByStateId(s1.getStateCode());
        assertTrue(state.isPresent());  // Check if the Optional is present
        assertEquals(1, state.get().size());  // The list should contain exactly one element
        assertEquals(s1, state.get().get(0));  // The first element should be the state "KA"
    }
 
 
   
 
    @Test
    public void testUpdateStateName() {
        // Simulate that state "KA" is found in the repository
        when(stateRepository.findByStateCode(s1.getStateCode())).thenReturn(Optional.of(s1));
        when(stateRepository.save(s1)).thenReturn(s1);
 
        // Test the updateStateName method
        s1.setStateName("New Karnataka");
        State updatedState = stateService.updateStateName(s1.getStateCode(), s1);
        assertEquals("New Karnataka", updatedState.getStateName());
 
        // Verify that save was called to persist the changes
        verify(stateRepository, times(1)).save(s1);
    }
 
 
}