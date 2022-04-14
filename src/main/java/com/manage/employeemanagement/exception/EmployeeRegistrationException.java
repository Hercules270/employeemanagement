package com.manage.employeemanagement.exception;

import lombok.Data;

@Data
public class EmployeeRegistrationException extends Exception {

    public EmployeeRegistrationException(String message) {
        super(message);
    }
}
