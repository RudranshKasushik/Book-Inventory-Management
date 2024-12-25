package com.cpg.bim.service;
 
import com.cpg.bim.entity.Bookcondition;
import com.cpg.bim.exception.BookconditionAlreadyExistsException;
import com.cpg.bim.exception.BookconditionNotFoundException;
import com.cpg.bim.repository.BookConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.math.BigDecimal;
import java.util.Optional;
 
@Service
public class BookConditionService {
 
    @Autowired
    BookConditionRepository bookconditionRepository;
 
    public void addBookcondition(Bookcondition bookcondition) {
        Optional<Bookcondition> existingBookcondition = bookconditionRepository.findByRanks(bookcondition.getRanks());
        if (existingBookcondition.isPresent()) {
            throw new BookconditionAlreadyExistsException("Book condition with ranks " + bookcondition.getRanks() + " already exists");
        }
        bookconditionRepository.save(bookcondition);
    }
 
    // Get a Bookcondition by ranks
    public Optional<Bookcondition> getBookconditionByRanks(int ranks) {
        Optional<Bookcondition> bookcondition = bookconditionRepository.findById(ranks);
        if (bookcondition.isEmpty()) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found.");
        }
        return bookcondition;
    }
 
    // Update the price of a Bookcondition by ranks
    public void updatePrice(int ranks, BigDecimal price) {
        Optional<Bookcondition> bookcondition = bookconditionRepository.findById(ranks);
        if (bookcondition.isEmpty()) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found.");
        }
        Bookcondition existingBookcondition = bookcondition.get();
        existingBookcondition.setPrice(price);
        bookconditionRepository.save(existingBookcondition);
    }
 
    // Update the full description of a Bookcondition by ranks
    public void updateFullDescription(int ranks, String fullDescription) {
        Optional<Bookcondition> bookcondition = bookconditionRepository.findById(ranks);
        if (bookcondition.isEmpty()) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found.");
        }
        Bookcondition existingBookcondition = bookcondition.get();
        existingBookcondition.setFulldescription(fullDescription);
        bookconditionRepository.save(existingBookcondition);
    }
 
    // Update the description of a Bookcondition by ranks
    public void updateDescription(int ranks, String description) {
        Optional<Bookcondition> bookcondition = bookconditionRepository.findById(ranks);
        if (bookcondition.isEmpty()) {
            throw new BookconditionNotFoundException("Book condition with ranks " + ranks + " not found.");
        }
        Bookcondition existingBookcondition = bookcondition.get();
        existingBookcondition.setDescription(description);
        bookconditionRepository.save(existingBookcondition);

    }
}