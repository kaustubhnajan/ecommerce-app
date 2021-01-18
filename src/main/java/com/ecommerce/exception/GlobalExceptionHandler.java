package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> handleResourceAlreadyExistException(
            ResourceAlreadyExistException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientProductQuantityException.class)
    public ResponseEntity<?> handleInsufficientProductQuantityException(
            InsufficientProductQuantityException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessRightException.class)
    public ResponseEntity<?> handleAccessRightException(AccessRightException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
