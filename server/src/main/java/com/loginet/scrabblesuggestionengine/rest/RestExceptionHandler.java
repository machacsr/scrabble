package com.loginet.scrabblesuggestionengine.rest;

import java.io.Serializable;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Data
    private static class Error implements Serializable {
        private String message;

        public Error(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Error> illegalArgument(Exception ex) {

        return ResponseEntity.badRequest().body(new Error(ex.getMessage()));
    }
}
