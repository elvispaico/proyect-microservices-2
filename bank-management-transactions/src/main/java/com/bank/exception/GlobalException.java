package com.bank.exception;

import com.bank.model.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> throwNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageResponse> throwAttributeException(AttributeException e) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
