package com.cpg.bim.controller.test;

import com.cpg.bim.controller.StateController;
import com.cpg.bim.entity.State;
import com.cpg.bim.service.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import java.util.Arrays;
import java.util.Optional;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
 
@WebMvcTest(StateController.class)
@ExtendWith(MockitoExtension.class)
public class StateControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private StateService stateService;
 
    private State s1 = new State("KA", "Karnataka");
    private State s2 = new State("MH", "Maharashtra");
    private State s3 = new State("TN", "Tamil Nadu");
 
    private ObjectMapper objectMapper = new ObjectMapper();
 
    @Test
    public void testPostMapping_addState() throws Exception {
        String stateJson = objectMapper.writeValueAsString(s3);
        when(stateService.addState(s3)).thenReturn(true);
 
        mockMvc.perform(post("/api/state/post")
                .contentType("application/json")
                .content(stateJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("State Added Successfully"));
    }
 
    @Test
    public void testGetMapping_getAllStates() throws Exception {
        when(stateService.getAllStates()).thenReturn(Arrays.asList(s1, s2));
 
        mockMvc.perform(get("/api/state/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].stateCode").value("KA"))
                .andExpect(jsonPath("$[1].stateCode").value("MH"));
    }
 
    @Test
    public void testGetMapping_getStateById() throws Exception {
        // Mock the stateService.getByStateId to return an Optional containing a list with the state
        when(stateService.getByStateId("KA")).thenReturn(Optional.of(Arrays.asList(s1)));
 
        mockMvc.perform(get("/api/state/{stateId}", "KA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].stateCode").value("KA"))
                .andExpect(jsonPath("$[0].stateName").value("Karnataka"));
    }

 
    @Test
    public void testPutMapping_updateState() throws Exception {
        State updatedState = new State("KA", "Karnataka Updated");
        when(stateService.updateStateName("KA", updatedState)).thenReturn(updatedState);
 
        String updatedStateJson = objectMapper.writeValueAsString(updatedState);
 
        mockMvc.perform(put("/api/state/update/name/{stateId}", "KA")
                .contentType("application/json")
                .content(updatedStateJson))
                .andExpect(status().isOk());
    }
}
 