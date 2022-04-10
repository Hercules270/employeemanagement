package com.manage.employeemanagement.bll;


import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ManagerBLL {

    private ManagerService managerService;

    @Autowired
    public ManagerBLL(ManagerService managerService) {
        this.managerService = managerService;
    }

    public ResponseEntity addNewEmployee(EmployeeRegisterRequest employee) {
        try {
            managerService.addNewEmployee(employee);
        } catch (Exception ex) {

        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
