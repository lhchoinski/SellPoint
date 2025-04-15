package com.system.pos.exeptions.handlers;

import com.system.pos.exeptions.BadRequestException;
import com.system.pos.exeptions.InvalidTokenException;
import com.system.pos.exeptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificHandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(getMessage(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, String>> handleInvalidTokenException(InvalidTokenException ex) {
        return new ResponseEntity<>(getMessage(ex), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(getMessage(ex), HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> getMessage(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex.getMessage() != null) {
            errors.put("message", ex.getMessage());
        }

        return errors;
    }
}
