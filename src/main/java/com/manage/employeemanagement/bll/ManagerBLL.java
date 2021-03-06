package com.manage.employeemanagement.bll;


import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.enums.Workday;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.exception.ProjectAssignmentException;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.request.ProjectAssignmentRequest;
import com.manage.employeemanagement.request.ProjectChangeRequest;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;
import com.manage.employeemanagement.response.*;
import com.manage.employeemanagement.services.interfaces.ManagerService;
import com.manage.employeemanagement.services.interfaces.ProjectService;
import com.manage.employeemanagement.utils.ConverterUtils;
import com.manage.employeemanagement.utils.CustomValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ManagerBLL {

    private final ManagerService managerService;
    private final ProjectService projectService;

    @Autowired
    public ManagerBLL(ManagerService managerService, ProjectService projectService) {
        this.managerService = managerService;
        this.projectService = projectService;
    }

    public ResponseEntity<ResponseResult<EmployeeRegistrationResponse>> addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException {
        UserRepresentation userRepresentation = managerService.addNewEmployee(employee);
        return new ResponseEntity<>(
                new ResponseResult<>(
                        new EmployeeRegistrationResponse(userRepresentation.getAttributes().get("employeeId").get(0),
                                userRepresentation.getUsername(),
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

    public ResponseEntity<ResponseResult<List<EmployeeAssignedProjectResponse>>> getAllEmployeeProjects(int page, int size, String[] sort) {
        List<User> employees = managerService.getAllEmployees(page, size, sort);

        ResponseEntity<ResponseResult<List<EmployeeAssignedProjectResponse>>> responseResultResponseEntity = new ResponseEntity<>(
                new ResponseResult<>(
                        employees.stream()
                                .map(ConverterUtils::convertEmployeeToEmployeeAssignedProject)
                                .collect(Collectors.toList()),
                        new Date()
                ),
                HttpStatus.OK
        );
        List<EmployeeAssignedProjectResponse> s = responseResultResponseEntity.getBody().getResult();
        return responseResultResponseEntity;
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

    public ResponseEntity<ResponseResult<List<ProjectResponse>>> getAllProjects(int page, int size, String[] sort) {
        List<Project> allProjects = projectService.getAllProjects(page, size, sort);
        List<ProjectResponse> collect = allProjects.stream()
                .map(ConverterUtils::convertProjectToProjectResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                new ResponseResult<>(
                        collect, new Date()
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity addNewProject(ProjectRegistrationRequest projectRequest) throws ProjectRegistrationException {
        if(!CustomValidationUtils.endDateBeforeStartDate(projectRequest.getStartDate(), projectRequest.getEndDate())) {
            throw new BadRequestException("End date must be after start date");
        }
        projectService.addNewProject(projectRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity deleteProject(String projectName) {
        projectService.deleteProject(projectName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity renameProject(String oldName, String newName) {
        projectService.renameProject(oldName, newName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ResponseResult<ProjectAssignmentResponse>> assignProjectToEmployee(ProjectAssignmentRequest projectAssignmentRequest) throws ProjectAssignmentException {
        if(projectAssignmentRequest.getDay() == Workday.SATURDAY || projectAssignmentRequest.getDay() == Workday.SUNDAY) {
            throw new BadRequestException("Day should be between MONDAY and FRIDAY");
        }
        AssignedProject assignedProject = projectService.assignProjectToEmployee(projectAssignmentRequest);
        ProjectAssignmentResponse projectAssignmentResponse = ConverterUtils.convertAssignedProjectToProjectAssignmentResponse(assignedProject);
        return new ResponseEntity<>(
                new ResponseResult<>(
                        projectAssignmentResponse, new Date()
                ),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity changeAssignedProject(ProjectChangeRequest projectChangeRequest) throws ProjectAssignmentException {
        projectService.changeAssignedProject(projectChangeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
