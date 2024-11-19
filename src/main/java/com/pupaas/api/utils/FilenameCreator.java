package com.pupaas.api.utils;

public class FilenameCreator {
    private static String filename = "imagenespupusas";

    public static String createFilename(int masa, int ingrediente) {


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
