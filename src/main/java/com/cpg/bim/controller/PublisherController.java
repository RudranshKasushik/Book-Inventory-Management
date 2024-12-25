package com.cpg.bim.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpg.bim.dto.PublisherDTO;
import com.cpg.bim.service.PublisherService;

@RestController
@RequestMapping("/publisher")
@CrossOrigin(origins= {"http://localhost:4200"})
public class PublisherController {

    @Autowired
    PublisherService publisherService;
    
    @PostMapping(value = "/addPublisher")
    public ResponseEntity<Map<String, String>> addPublisher(@RequestBody PublisherDTO publisherDTO) {
        Map<String, String> response = publisherService.addPublisher(publisherDTO);
        if ("POSTSUCCESS".equals(response.get("code"))) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping(value = "/allpublisher", produces = "application/json")
    public ResponseEntity<List<PublisherDTO>> getAllPublisher() {
        List<PublisherDTO> publishers = publisherService.getAllPublisher();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/byId/{id}", produces = "application/json")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable int id) {
        PublisherDTO publisher = publisherService.getPublisherById(id);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{name}", produces = "application/json")
    public ResponseEntity<PublisherDTO> getPublisherByName(@PathVariable String name) {
        PublisherDTO publisher = publisherService.getPublisherByName(name);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }
    
    @GetMapping(value = "/city/{city}", produces = "application/json")
    public ResponseEntity<List<PublisherDTO>> getPublisherByCity(@PathVariable String city) {
        List<PublisherDTO> publishers = publisherService.getPublisherByCity(city);
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/state/{state}", produces = "application/json")
    public ResponseEntity<List<PublisherDTO>> getPublisherByState(@PathVariable String state) {
        List<PublisherDTO> publishers = publisherService.getPublisherByState(state);
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
    
    @PutMapping("/updateName/{id}")
    public ResponseEntity<PublisherDTO> updatePublisherName(@PathVariable int id, @RequestBody String newName) {
        PublisherDTO updatedPublisher = publisherService.updatePublisherName(id, newName);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
    }
    
    @PutMapping("/updateCity/{id}")
    public ResponseEntity<PublisherDTO> updatePublisherCity(@PathVariable int id, @RequestBody String newCity) {
        PublisherDTO updatedPublisher = publisherService.updatePublisherCity(id, newCity);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
    }
    
    @PutMapping("/updateStateByState/{state}")
    public ResponseEntity<PublisherDTO> updatePublisherStateByState(@PathVariable String state, @RequestBody Map<String, Object> requestData) {
        PublisherDTO updatedPublisher = publisherService.updatePublisherStateByState(requestData);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
    }
}
