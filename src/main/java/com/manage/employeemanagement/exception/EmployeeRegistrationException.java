package com.manage.employeemanagement.exception;

import com.manage.employeemanagement.enums.EmployeeRegistrationErrorEnum;
import lombok.Data;

@Data
public class EmployeeRegistrationException extends Exception {

    private EmployeeRegistrationErrorEnum errorCode;


    public EmployeeRegistrationException(String message, EmployeeRegistrationErrorEnum errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
