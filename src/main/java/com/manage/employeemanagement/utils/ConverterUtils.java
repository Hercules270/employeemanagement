package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.enums.Workday;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.request.ProjectAssignmentRequest;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;
import com.manage.employeemanagement.response.*;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConverterUtils {

    public static UserRepresentation convertEmployeeToUserRepresentation(EmployeeRegisterRequest employee) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(employee.getUsername());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getLastName());
        userRepresentation.setEmail(employee.getEmail());
        userRepresentation.setEmailVerified(true);
        userRepresentation.setGroups(List.of("employee_group"));    // This is needed to avoid role setting bug of keycloak client

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(CustomUtils.generatePassayPassword());
        credential.setTemporary(true);
        userRepresentation.setCredentials(List.of(credential));
        return userRepresentation;
    }

    public static User convertEmployeeToUser(EmployeeRegisterRequest employee) {
        return new User(employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getEmail(),
                new Date());

    }

    public static EmployeesResponse convertUserToEmployee(User user) {
        return new EmployeesResponse(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public static Sort.Order convertStringToOrder(String sort) {
        String[] sortOrder = sort.split(",");
        return new Sort.Order(sortOrder[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortOrder[0]);
    }

    public static ProjectResponse convertProjectToProjectResponse(Project project) {
        return new ProjectResponse(project.getName(), project.getStartDate(), project.getEndDate());
    }

    public static Project convertProjectRequestToProject(ProjectRegistrationRequest projectRequest) {
        return new Project(projectRequest.getName(), projectRequest.getStartDate(), projectRequest.getEndDate());
    }

    public static Date workdayToDate(Workday date) {
        LocalDate nextWeekday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.valueOf(date.toString())));
        System.out.println(nextWeekday.getYear() + " " + nextWeekday.getMonthValue() + " " + nextWeekday.getDayOfMonth());
        Date from = Date.from(nextWeekday.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
//        from.setDate(from.getDate() + 1);
        return from;
    }

    public static AssignedProject convertProjectAssignmentRequestToAssignedProject(
            ProjectAssignmentRequest projectAssignmentRequest,
            User user,
            Project project,
            Date date) {
        return new AssignedProject(user, project, date);

    }

    public static ProjectAssignmentResponse convertAssignedProjectToProjectAssignmentResponse(AssignedProject assignedProject) {
        User user = assignedProject.getUser();
        Project project = assignedProject.getProject();
        Date dateCorrector = Date.from(assignedProject.getDate().toInstant());
        dateCorrector.setDate(dateCorrector.getDate() + 1);
        return new ProjectAssignmentResponse(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserId(),
                project.getName(),
                dateCorrector);
    }

    public static EmployeeAssignedProjectResponse convertEmployeeToEmployeeAssignedProject(User employee) {
        EmployeeAssignedProjectResponse employeeAssignedProjectResponse =
                new EmployeeAssignedProjectResponse(employee.getUserId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
        List<AssignedProjectResponse> projects = employee.getAssignments().stream()
                .map(assignedProject ->
                        new AssignedProjectResponse(
                                assignedProject.getProject().getName(),
                                assignedProject.getProject().getStartDate(),
                                assignedProject.getProject().getEndDate(),
                                assignedProject.getDate()
                        ))
                .collect(Collectors.toList());
        employeeAssignedProjectResponse.setAssignedProjects(projects);
        return employeeAssignedProjectResponse;


    }


}
