package com.cpg.bim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpg.bim.dto.PublisherDTO;
import com.cpg.bim.entity.Publisher;
import com.cpg.bim.exception.PublisherNotFoundException;
import com.cpg.bim.repository.PublisherRepository;

@Service
public class PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    private PublisherDTO convertToDto(Publisher publisher) {
        return new PublisherDTO(publisher.getPublisherid(), publisher.getName(), publisher.getCity(), publisher.getStatecode());
    }

    private Publisher convertToEntity(PublisherDTO publisherDTO) {
        return new Publisher(publisherDTO.getId(), publisherDTO.getName(), publisherDTO.getCity(), publisherDTO.getState(), null);
    }

    @Transactional
    public Map<String, String> addPublisher(PublisherDTO publisherDTO) {
        Map<String, String> response = new HashMap<>();
        Publisher publisher = convertToEntity(publisherDTO);
        Publisher savedPublisher = publisherRepository.save(publisher);
        if (savedPublisher != null) {
            response.put("code", "POSTSUCCESS");
            response.put("message", "Publisher added successfully");
        } else {
            response.put("code", "POSTFAIL");
            response.put("message", "Failed to add publisher");
        }
        return response;
    }

    @Transactional(readOnly = true)
    public List<PublisherDTO> getAllPublisher() {
        List<Publisher> publisherList = publisherRepository.findAll();
        if (publisherList.size() != 0) {
            return publisherList.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        throw new PublisherNotFoundException();
    }

    @Transactional(readOnly = true)
    public PublisherDTO getPublisherById(int id) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException());
        return convertToDto(publisher);
    }

    @Transactional(readOnly = true)
    public PublisherDTO getPublisherByName(String name) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findByName(name).orElseThrow(() -> new PublisherNotFoundException());
        return convertToDto(publisher);
    }

    @Transactional
    public List<PublisherDTO> getPublisherByCity(String city) throws PublisherNotFoundException {
        List<Publisher> publishers = publisherRepository.findByCity(city);
        if (publishers != null) {
            return publishers.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public List<PublisherDTO> getPublisherByState(String state) throws PublisherNotFoundException {
        List<Publisher> publishers = publisherRepository.findByState(state);
        if (publishers != null) {
            return publishers.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public PublisherDTO updatePublisherName(int id, String newName) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));
        publisher.setName(newName);
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return convertToDto(updatedPublisher);
    }

    @Transactional
    public PublisherDTO updatePublisherCity(int id, String newCity) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));
        publisher.setCity(newCity);
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return convertToDto(updatedPublisher);
    }

    @Transactional
    public PublisherDTO updatePublisherStateByState(Map<String, Object> requestData) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById((Integer) requestData.get("id")).orElseThrow(() -> new PublisherNotFoundException("Publisher not found"));
        String newState = (String) requestData.get("state");
        publisher.setStatecode(newState);
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return convertToDto(updatedPublisher);
    }
}
