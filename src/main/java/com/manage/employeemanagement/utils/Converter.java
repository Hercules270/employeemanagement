package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class Converter {

    public static UserRepresentation convert(EmployeeRegisterRequest employee) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(employee.getUsername());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getLastName());
        userRepresentation.setEmail(employee.getEmail());
        userRepresentation.setRealmRoles(List.of("employee"));
        return userRepresentation;
    }
}
