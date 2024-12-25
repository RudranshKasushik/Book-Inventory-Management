package com.cpg.bim.service.test;

import com.cpg.bim.dto.PublisherDTO;
import com.cpg.bim.entity.Publisher;
import com.cpg.bim.exception.PublisherNotFoundException;
import com.cpg.bim.repository.PublisherRepository;
import com.cpg.bim.service.PublisherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTO publisherDTO;
    private Publisher publisher;

    @BeforeEach
    public void setup() {
        publisherDTO = new PublisherDTO(1, "O'Reilly Media", "Sebastopol", "CA");
        publisher = new Publisher(1, "O'Reilly Media", "Sebastopol", "CA", null);
    }

    @Test
    public void testAddPublisherSuccess() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Map<String, String> response = publisherService.addPublisher(publisherDTO);

        assertEquals("POSTSUCCESS", response.get("code"));
        assertEquals("Publisher added successfully", response.get("message"));
    }

    @Test
    public void testAddPublisherFailure() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(null);

        Map<String, String> response = publisherService.addPublisher(publisherDTO);

        assertEquals("POSTFAIL", response.get("code"));
        assertEquals("Failed to add publisher", response.get("message"));
    }

    @Test
    public void testGetAllPublisher() {
        List<Publisher> publishers = Collections.singletonList(publisher);
        when(publisherRepository.findAll()).thenReturn(publishers);

        List<PublisherDTO> publisherDTOList = publisherService.getAllPublisher();

        assertFalse(publisherDTOList.isEmpty());
        assertEquals(1, publisherDTOList.size());
        assertEquals("O'Reilly Media", publisherDTOList.get(0).getName());
    }

    @Test
    public void testGetAllPublisherNotFound() {
        when(publisherRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.getAllPublisher();
        });
    }

    @Test
    public void testGetPublisherById() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));

        PublisherDTO foundPublisherDTO = publisherService.getPublisherById(1);

        assertNotNull(foundPublisherDTO);
        assertEquals("O'Reilly Media", foundPublisherDTO.getName());
    }

    @Test
    public void testGetPublisherByIdNotFound() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.getPublisherById(1);
        });
    }

    @Test
    public void testGetPublisherByName() {
        when(publisherRepository.findByName(anyString())).thenReturn(Optional.of(publisher));

        PublisherDTO foundPublisherDTO = publisherService.getPublisherByName("O'Reilly Media");

        assertNotNull(foundPublisherDTO);
        assertEquals("O'Reilly Media", foundPublisherDTO.getName());
    }

    @Test
    public void testGetPublisherByNameNotFound() {
        when(publisherRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.getPublisherByName("O'Reilly Media");
        });
    }

    @Test
    public void testGetPublisherByCity() {
        List<Publisher> publishers = Collections.singletonList(publisher);
        when(publisherRepository.findByCity(anyString())).thenReturn(publishers);

        List<PublisherDTO> publisherDTOList = publisherService.getPublisherByCity("Sebastopol");

        assertFalse(publisherDTOList.isEmpty());
        assertEquals(1, publisherDTOList.size());
        assertEquals("O'Reilly Media", publisherDTOList.get(0).getName());
    }

//    @Test
//    public void testGetPublisherByCityNotFound() {
//        when(publisherRepository.findByCity(anyString())).thenReturn(Collections.emptyList());
//
//        assertNull(publisherService.getPublisherByCity("Sebastopol"));
//    }

    @Test
    public void testGetPublisherByState() {
        List<Publisher> publishers = Collections.singletonList(publisher);
        when(publisherRepository.findByState(anyString())).thenReturn(publishers);

        List<PublisherDTO> publisherDTOList = publisherService.getPublisherByState("CA");

        assertFalse(publisherDTOList.isEmpty());
        assertEquals(1, publisherDTOList.size());
        assertEquals("O'Reilly Media", publisherDTOList.get(0).getName());
    }

//    @Test
//    public void testGetPublisherByStateNotFound() {
//        when(publisherRepository.findByState(anyString())).thenReturn(Collections.emptyList());
//
//        assertNull(publisherService.getPublisherByState("CA"));
//    }

    @Test
    public void testUpdatePublisherName() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        PublisherDTO updatedPublisherDTO = publisherService.updatePublisherName(1, "New Publisher Name");

        assertNotNull(updatedPublisherDTO);
        assertEquals("New Publisher Name", updatedPublisherDTO.getName());
    }

    @Test
    public void testUpdatePublisherNameNotFound() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.updatePublisherName(1, "New Publisher Name");
        });
    }

    @Test
    public void testUpdatePublisherCity() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        PublisherDTO updatedPublisherDTO = publisherService.updatePublisherCity(1, "New City");

        assertNotNull(updatedPublisherDTO);
        assertEquals("New City", updatedPublisherDTO.getCity());
    }

    @Test
    public void testUpdatePublisherCityNotFound() {
        when(publisherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.updatePublisherCity(1, "New City");
        });
    }

    @Test
    public void testUpdatePublisherState() {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", 1);
        requestData.put("state", "NY");

        when(publisherRepository.findById(anyInt())).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        PublisherDTO updatedPublisherDTO = publisherService.updatePublisherStateByState(requestData);

        assertNotNull(updatedPublisherDTO);
        assertEquals("NY", updatedPublisherDTO.getState());
    }

    @Test
    public void testUpdatePublisherStateNotFound() {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", 1);
        requestData.put("state", "NY");

        when(publisherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> {
            publisherService.updatePublisherStateByState(requestData);
        });
    }
}
