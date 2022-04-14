package com.manage.employeemanagement.bll;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.exception.LoggingException;
import com.manage.employeemanagement.response.ResponseResult;
import com.manage.employeemanagement.response.employee.EmployeeInformationResponse;
import com.manage.employeemanagement.services.interfaces.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Component
public class EmployeeBLL {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeBLL(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public ResponseEntity<ResponseResult<EmployeeInformationResponse>> getCurrentInformation(String username) {
        EmployeeInformationResponse employeeInformationResponse = employeeService.getEmployee(username);
        return new ResponseEntity<>(
                new ResponseResult<>(employeeInformationResponse, new Date()),
                HttpStatus.OK
        );
    }

    public ResponseEntity logStartTime(String username) throws LoggingException {
        employeeService.logStartTime(username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity logEndTime(String username) throws LoggingException {
        employeeService.logEndTime(username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
