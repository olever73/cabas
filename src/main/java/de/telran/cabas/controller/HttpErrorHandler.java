package de.telran.cabas.controller;

import de.telran.cabas.dto.CabasApiErrorResponseDTO;
import de.telran.cabas.dto.ResponseExceptionDTO;
import de.telran.cabas.dto.ResponseExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;


@ControllerAdvice
public class HttpErrorHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CabasApiErrorResponseDTO> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(CabasApiErrorResponseDTO
                        .builder()
                        .message(ex.getReason())
                        .status(ex.getStatus())
                        .build());
    }
}