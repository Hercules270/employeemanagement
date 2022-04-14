package com.manage.employeemanagement.controller;


import com.manage.employeemanagement.bll.ManagerBLL;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.exception.ProjectAssignmentException;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.request.*;
import com.manage.employeemanagement.response.*;
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
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        return managerBLL.getAllEmployees(page, size, sort);
    }

    @GetMapping("/employees/projects")
    public ResponseEntity<ResponseResult<List<EmployeeAssignedProjectResponse>>> getAllEmployeeProjects(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        return managerBLL.getAllEmployeeProjects(page, size, sort);
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

    @DeleteMapping("/projects/{projectName}")
    public ResponseEntity deleteProject(@PathVariable String projectName) {
        return managerBLL.deleteProject(projectName);
    }

    @PutMapping("/projects/{oldName}")
    public ResponseEntity renameProject(@PathVariable String oldName, @RequestBody UpdateProjectNameRequest name) {
        return managerBLL.renameProject(oldName, name.getNewName());
    }

    @PostMapping("/projects/assignment")
    public ResponseEntity<ResponseResult<ProjectAssignmentResponse>> assignProjectToEmployee(@RequestBody ProjectAssignmentRequest projectAssignmentRequest) throws ProjectAssignmentException {
        return managerBLL.assignProjectToEmployee(projectAssignmentRequest);
    }

    @PutMapping("/projects/assignment")
    public ResponseEntity changeAssignedProject(@RequestBody ProjectChangeRequest projectChangeRequest) throws ProjectAssignmentException {
        return managerBLL.changeAssignedProject(projectChangeRequest);
    }
}