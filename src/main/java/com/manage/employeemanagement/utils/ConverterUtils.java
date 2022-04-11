package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

public class ConverterUtils {

    public static UserRepresentation convertEmployeeToUserRepresentation(EmployeeRegisterRequest employee) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(employee.getUsername());
        userRepresentation.setFirstName(employee.getFirstName());
        userRepresentation.setLastName(employee.getLastName());
        userRepresentation.setEmail(employee.getEmail());
        userRepresentation.setEmailVerified(true);
//        userRepresentation.setRealmRoles(List.of("EMPLOYEE"));
        return userRepresentation;
    }

    public static User convertEmployeeToUser(EmployeeRegisterRequest employee) {
        return new User(employee.getFirstName(),
                employee.getLastName(),
                employee.getUsername(),
                employee.getEmail(),
                new Date());

    }
}
