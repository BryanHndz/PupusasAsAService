package com.pupaas.api.handlers;

import com.pupaas.api.exceptions.WrongFileUploadingException;
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
                "OPCIONES DISPONIBLES PARA DESCRIBIR LA IMAGEN: \n" +
                "MASA: [Arroz = 1], [Maíz = 2] \n" +
                "INGREDIENTES: [1 = Frijol-queso], [2 = Revueltas], [3 = Queso]";

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongFileUploadingException.class)
    public ResponseEntity<String> WrongFileUploadingException(Exception ex) {
        String message = "Usted esta intentando subir un tipo de archivo incorrecto: " + ex.getMessage() +
                "\n\nTipos de archivos permitidos: JPG, JPEG, PNG y GIF";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> IllegalArgumentException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
