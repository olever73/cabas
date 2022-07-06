package de.teleran.cabas.controller;

import de.teleran.cabas.dto.ResponseExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class HttpErrorHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseExceptionDTO> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResponseExceptionDTO
                        .builder()
                        .message(ex.getReason())
                        .status(ex.getStatus())
                        .build());
    }
}

