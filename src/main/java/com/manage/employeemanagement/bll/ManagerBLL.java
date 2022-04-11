package com.manage.employeemanagement.bll;


import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.response.EmployeeRegistrationResponse;
import com.manage.employeemanagement.response.ResponseResult;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ManagerBLL {

    private final ManagerService managerService;

    @Autowired
    public ManagerBLL(ManagerService managerService) {
        this.managerService = managerService;
    }

    public ResponseEntity<ResponseResult<EmployeeRegistrationResponse>> addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        UserRepresentation userRepresentation = managerService.addNewEmployee(employee);
        return new ResponseEntity<>(
                new ResponseResult<>(
                        new EmployeeRegistrationResponse(userRepresentation.getUsername(),
                                userRepresentation.getEmail(),
                                userRepresentation.getCredentials().get(0).getValue()),
                        new Date()),
                HttpStatus.CREATED);
    }

}
