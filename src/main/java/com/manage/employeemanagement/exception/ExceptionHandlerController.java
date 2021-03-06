package com.manage.employeemanagement.exception;


import com.manage.employeemanagement.response.ErrorResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import java.util.Date;

import org.springframework.data.mapping.PropertyReferenceException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {


    @ExceptionHandler(value = EmployeeRegistrationException.class)
    public ResponseEntity<ErrorResponseResult> handleEmployeeRegistrationException(
            EmployeeRegistrationException ex, WebRequest request) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.OK);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseResult> handleParameterValidationException(
            ConstraintViolationException ex, WebRequest request) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<ErrorResponseResult> handleSortingException(
            PropertyReferenceException ex, WebRequest request) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponseResult> handleBadRequestException(BadRequestException ex, WebRequest request) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = ProjectRegistrationException.class)
    public ResponseEntity<ErrorResponseResult> handleBadRequestException(ProjectRegistrationException ex, WebRequest request) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseResult> handleRequestBodyPropertyValidationException(
            MethodArgumentNotValidException ex, WebRequest request, Errors errors) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(errors.getFieldError().getDefaultMessage(), new Date()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = TransactionException.class)
    public ResponseEntity<ErrorResponseResult> handleTransactionException(
            TransactionException ex, WebRequest request, Errors errors) {
        log.error("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(errors.getFieldError().getDefaultMessage(), new Date()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseResult> handleUnformattedRequestBody(
            Exception ex, WebRequest request) {
        log.info("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult("Request parameters are not valid", new Date()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = ProjectAssignmentException.class)
    public ResponseEntity<ErrorResponseResult> handleUnformattedRequestBody(
            ProjectAssignmentException ex, WebRequest request) {
        log.info("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = LoggingException.class)
    public ResponseEntity<ErrorResponseResult> handleLoggingException(
            LoggingException ex, WebRequest request) {
        log.info("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult(ex.getMessage(), new Date()), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseResult> handleAllOtherException(
            Exception ex, WebRequest request) {
        log.info("Error: ", ex);
        return new ResponseEntity<>(
                new ErrorResponseResult("Server error", new Date()), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}