package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.response.EmployeesResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

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
        return new EmployeesResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public static Sort.Order convertStringToOrder(String sort) {
        String[] sortOrder = sort.split(",");
        return new Sort.Order(sortOrder[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortOrder[0]);
    }
}
