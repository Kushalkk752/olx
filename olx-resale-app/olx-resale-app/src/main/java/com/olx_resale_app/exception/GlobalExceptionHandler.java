package com.olx_resale_app.exception;

import com.olx_resale_app.payload.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.CONFLICT);
        error.setStatusCodes(HttpStatus.CONFLICT.value());
        error.setMessage(exception.getMessage());
        error.setSubError(new ArrayList<>());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ApiError> handleUserNotExistsException(UserNotExistsException exception){
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setStatusCodes(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setSubError(new ArrayList<>());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleGeneralException(Exception e, WebRequest request){
        ApiError error = new ApiError();
        error.setStatus(HttpStatus.FORBIDDEN);
        error.setStatusCodes(HttpStatus.FORBIDDEN.value());
        error.setMessage(e.getMessage());
        error.setSubError(new ArrayList<>());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);

    }
}
