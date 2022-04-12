package com.manage.employeemanagement.bll;


import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.response.EmployeesResponse;
import com.manage.employeemanagement.response.EmployeeRegistrationResponse;
import com.manage.employeemanagement.response.ResponseResult;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import com.manage.employeemanagement.utils.ConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<ResponseResult<List<EmployeesResponse>>> getAllEmployees(int page, int size, String[] sort) {
        List<User> allUsers = managerService.getAllEmployees(page, size, sort);
        List<EmployeesResponse> allEmployees = allUsers.stream()
                .map(ConverterUtils::convertUserToEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                new ResponseResult<>(
                        allEmployees, new Date()
                ), HttpStatus.OK
        );
    }

    public ResponseEntity<ResponseResult<EmployeesResponse>> getEmployee(String firstName, String lastName) {
        Optional<User> employee = managerService.getEmployee(firstName, lastName);
        if(employee.isPresent()) {
            return new ResponseEntity<>(
                    new ResponseResult<>(
                            ConverterUtils.convertUserToEmployee(employee.get()),
                            new Date()
                    ),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
