package com.informatorio.trabaoFinal.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

//anotacion que le indica a la clase que puede manejar excepciones globales.
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NewsAppException.class)
    public ResponseEntity<ErrorDetail> handlerNewsAppException(NewsAppException exception, WebRequest webRequest){
        ErrorDetail errorDetail=new ErrorDetail(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handlerGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetail errorDetail=new ErrorDetail(new Date(),exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }
}
