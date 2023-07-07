package de.workshops.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> error() {
        return new ResponseEntity<>("Buch konnte nicht gefunden werden.", HttpStatus.NOT_FOUND);
    }
}
