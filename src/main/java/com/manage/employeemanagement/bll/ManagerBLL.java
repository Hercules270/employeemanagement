package com.manage.employeemanagement.bll;


import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ManagerBLL {

    private ManagerService managerService;

    @Autowired
    public ManagerBLL(ManagerService managerService) {
        this.managerService = managerService;
    }

    public ResponseEntity addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        managerService.addNewEmployee(employee);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
