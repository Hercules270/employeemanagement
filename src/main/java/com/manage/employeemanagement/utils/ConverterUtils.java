package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import com.manage.employeemanagement.response.AllEmployeesResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.util.Date;
import java.util.List;

import static org.passay.DigestDictionaryRule.ERROR_CODE;

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

    public static AllEmployeesResponse convertUserToEmployee(User user) {
        return new AllEmployeesResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
