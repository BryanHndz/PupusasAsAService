package com.pupaas.api.utils;

import org.springframework.stereotype.Component;

@Component
public class FilenameCreator {

    public String createFilename(int masa, int ingrediente) {
        String filename = "imagenespupusas";

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
