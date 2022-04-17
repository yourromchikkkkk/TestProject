package com.petapp.exception.handler;

import com.petapp.exception.ItemNotFoundException;
import com.petapp.exception.NotEnoughItemsException;
import com.petapp.exception.OrderNotFoundException;
import com.petapp.exception.IllegalInputException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalInputException.class)
    public final ResponseEntity wrongInputHandler(HttpServletRequest req, IllegalInputException ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity itemNotFoundHandler(HttpServletRequest req, ItemNotFoundException ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity orderNotFoundHandler(HttpServletRequest req, OrderNotFoundException ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(NotEnoughItemsException.class)
    public final ResponseEntity notEnoughItemsHandler(HttpServletRequest req, NotEnoughItemsException ex) {
        log.error("Request: " + req.getRequestURL() + " raised " + ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
