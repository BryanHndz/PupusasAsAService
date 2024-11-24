package com.pupaas.api.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class FilenameCreator {

    public String createFilename(int masa, int ingrediente) throws IllegalArgumentException {
        String filename = "imagenespupusas";

        if (masa < 1  || masa > 2) {
            throw new IllegalArgumentException("Valor de la masa inválido. Solo se aceptan valores entre 1 y 2");
        } else if (ingrediente < 1 || ingrediente > 3) {
            throw new IllegalArgumentException("Valor de ingrediente inválido. Valores entre 1 y 3");
        }


        switch (masa){
            case 1:
                filename += "/arroz";
                break;
            case 2:
                filename += "/maiz";
                break;
        }

        switch (ingrediente){
            case 1:
                filename += "/frijol-queso/";
                break;
            case 2:
                filename += "/revueltas/";
                break;
            case 3:
                filename += "/queso/";
        }
        return filename;

    }
}
