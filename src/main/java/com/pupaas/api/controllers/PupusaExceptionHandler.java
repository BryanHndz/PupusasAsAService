package com.pupaas.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class PupusaExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> BadRequestException(Exception ex) {
        String message = "Usted ha enviado parámetros incorrectos. \n\n" +
                "OPCIONES DISPONIBLES PARA CATALOGACIÓN: \n" +
                "MASA: [Arroz = 1], [Maíz = 2] \n" +
                "INGREDIENTES: [1 = Frijol-queso], [2 = Revueltas], [3 = Queso]";

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
