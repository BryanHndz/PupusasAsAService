package com.pupaas.api.exceptions;

public class WrongFileUploadingException extends RuntimeException{
    public WrongFileUploadingException(String message) {
        super(message);
    }
}
