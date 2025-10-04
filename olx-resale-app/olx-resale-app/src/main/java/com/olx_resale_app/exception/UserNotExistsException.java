package com.olx_resale_app.exception;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException(String message){
        super(message);
    }
}
