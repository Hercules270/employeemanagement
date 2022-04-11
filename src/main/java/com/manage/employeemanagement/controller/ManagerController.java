package com.manage.employeemanagement.controller;


import com.manage.employeemanagement.bll.ManagerBLL;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.response.EmployeeRegistrationResponse;
import com.manage.employeemanagement.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerBLL managerBLL;

    @Autowired
    public ManagerController(ManagerBLL managerBLL) {
        this.managerBLL = managerBLL;
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<ResponseResult<EmployeeRegistrationResponse>> addUser(@RequestBody EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        return managerBLL.addNewEmployee(employee);
    }
}