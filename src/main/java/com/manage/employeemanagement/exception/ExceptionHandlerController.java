package com.manage.employeemanagement.exception;


import com.manage.employeemanagement.enums.EmployeeRegistrationErrorEnum;
import com.manage.employeemanagement.response.ErrorResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(value = EmployeeRegistrationException.class)
    public ResponseEntity<ErrorResponseResult> handleEmployeeRegistrationException(
            EmployeeRegistrationException ex, WebRequest request) {
        if(ex.getErrorCode() == EmployeeRegistrationErrorEnum.USERNAME_ALREADY_EXISTS)
            return new ResponseEntity<>(new ErrorResponseResult("User with that username already exists", new Date()), HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    new ErrorResponseResult("Can't register user", new Date()), HttpStatus.OK);

    }



}
