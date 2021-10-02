package com.assessment.mercedesclient.exception;

import com.assessment.mercedesclient.model.ErrorResponse;
import com.assessment.mercedesclient.model.UserResponse;
import com.assessment.mercedesclient.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InputValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse inputValidationFail(InputValidationException ex, WebRequest request){
        return new ErrorResponse(ex.getMessage(),400);
    }
    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse serviceDown(ConnectException ex, WebRequest request){
        return new ErrorResponse(Constants.SERVICES_ARE_DOWN,404);


    }

}
