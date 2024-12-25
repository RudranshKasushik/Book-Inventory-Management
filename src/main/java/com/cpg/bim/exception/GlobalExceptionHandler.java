package com.cpg.bim.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException ex) {
        // Create a response map to include the exception message
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());  // This will contain the message passed during the exception
        // Return a 404 Not Found with the exception message in the response body
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<?> handleReviewAlreadyExistsException(ReviewAlreadyExistsException ex) {
        // Create a response map to include the exception message
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());  // This will contain the message passed during the exception
 
        // Print the error message for debugging
        System.out.println("Error handled: " + ex.getMessage());
 
        // Return a 400 Bad Request with the exception message in the response body
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGeneralException(Exception ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("error", "An unexpected error occurred");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<?> bookNotFound(){
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PublisherNotFoundException.class)
	public ResponseEntity<?> publisherNotFound(){
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(ReviewerNotFoundException.class)
    public ResponseEntity<ReviewerExceptionResponse> handleReviewerNotFoundException(StateNotFoundException ex) {
        ReviewerExceptionResponse response = new ReviewerExceptionResponse("NOTFOUND", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    // Handle reviewer already exists error
//    @ExceptionHandler(ReviewAlreadyExistsException.class)
//    public ResponseEntity<ReviewerExceptionResponse> handleReviewerAlreadyExistsException(ReviewAlreadyExistsException ex) {
//        ReviewerExceptionResponse response = new ReviewerExceptionResponse("ADDFAILS", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    
    // Handle state already exists error
    @ExceptionHandler(StateAlreadyExistsException.class)
    public ResponseEntity<StateExceptionResponse> handleStateAlreadyExistsException(StateAlreadyExistsException ex) {
        StateExceptionResponse response = new StateExceptionResponse("ADDFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle state not found error
    @ExceptionHandler(StateNotFoundException.class)
    public ResponseEntity<StateExceptionResponse> handleStateNotFoundException(StateNotFoundException ex) {
        StateExceptionResponse response = new StateExceptionResponse("NOTFOUND", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    // Handle author already exists error
    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<AuthorExceptionResponse> handleAuthorAlreadyExistsException(AuthorAlreadyExistsException ex) {
    	AuthorExceptionResponse response = new AuthorExceptionResponse("ADDFAILS", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }
    // Handle author not found error
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<AuthorExceptionResponse> handleAuthorNotFoundException(AuthorNotFoundException ex) {
    	AuthorExceptionResponse response = new AuthorExceptionResponse("NOTFOUND", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found
    }

    // Generic Exception Handler for Unhandled Exceptions 

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
    }
    
    
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> handleCategoryAlreadyExists(CategoryAlreadyExistsException ex)
    {
        // Returning a null body with 409 Conflict status
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    // Handle CategoryNotFoundException
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFound(CategoryNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    
    @ExceptionHandler(PurchaseLogAlreadyExistsException.class)
    public ResponseEntity<String> handlePurchaseLogAlreadyExists(PurchaseLogAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
    }
    
 
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<String> handleInventoryNotFound(InventoryNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
    }
 
    @ExceptionHandler(PurchaseLogNotFoundException.class)
    public ResponseEntity<String> handlePurchaseLogNotFound(PurchaseLogNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
    }
    
    // Handle InventoryAlreadyTakenException
    @ExceptionHandler(InventoryAlreadyTakenException.class)
    public ResponseEntity<String> handleInventoryAlreadyTaken(InventoryAlreadyTakenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT); // Return 409 Conflict with the message
    }
    
    
    @ExceptionHandler(ShoppingCartAlreadyExistsException.class)
    public ResponseEntity<String> handleShoppingCartAlreadyExistsException(ShoppingCartAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
    // Handle ShoppingCartNotFoundException
    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<String> handleShoppingCartNotFoundException(ShoppingCartNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
 
    // Handle InvalidIsbnException
    @ExceptionHandler(InvalidIsbnException.class)
    public ResponseEntity<String> handleInvalidIsbnException(InvalidIsbnException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(PermroleAlreadyExistsException.class)
    public ResponseEntity<String> handlePermroleAlreadyExists(PermroleAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
 
   
 
    @ExceptionHandler(PermroleNotFoundException.class) // Handle PermroleNotFoundException
    public ResponseEntity<String> handlePermroleNotFound(PermroleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
 
//    // General exception handler
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
//    }
    
    
    @ExceptionHandler(BookconditionAlreadyExistsException.class)
    public ResponseEntity<String> handleBookconditionAlreadyExists(BookconditionAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
 
    // Handle BookconditionNotFoundException
    @ExceptionHandler(BookconditionNotFoundException.class)
    public ResponseEntity<String> handleBookconditionNotFound(BookconditionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
 
}
