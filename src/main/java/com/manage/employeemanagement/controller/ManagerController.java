package com.manage.employeemanagement.controller;


import com.manage.employeemanagement.bll.ManagerBLL;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;
import com.manage.employeemanagement.response.EmployeesResponse;
import com.manage.employeemanagement.response.EmployeeRegistrationResponse;
import com.manage.employeemanagement.response.ProjectResponse;
import com.manage.employeemanagement.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/manager")
@Validated
public class ManagerController {

    private final ManagerBLL managerBLL;

    @Autowired
    public ManagerController(ManagerBLL managerBLL) {
        this.managerBLL = managerBLL;
    }

    @PostMapping("/employees")
    public ResponseEntity<ResponseResult<EmployeeRegistrationResponse>> addUser(@RequestBody EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        return managerBLL.addNewEmployee(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<ResponseResult<List<EmployeesResponse>>> getAllEmployees(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
            ) {
        return managerBLL.getAllEmployees(page, size, sort);
    }

    @GetMapping("/employee")
    public ResponseEntity<ResponseResult<EmployeesResponse>> getEmployee(@RequestParam String firstName, @RequestParam String lastName) {
        return managerBLL.getEmployee(firstName, lastName);
    }

    @GetMapping("/projects")
    public ResponseEntity<ResponseResult<List<ProjectResponse>>> getAllProjects(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        return managerBLL.getAllProjects(page, size, sort);
    }

    @PostMapping("/projects")
    public ResponseEntity addProject(@Valid @RequestBody ProjectRegistrationRequest project) throws ProjectRegistrationException {
        return managerBLL.addNewProject(project);
    }

}