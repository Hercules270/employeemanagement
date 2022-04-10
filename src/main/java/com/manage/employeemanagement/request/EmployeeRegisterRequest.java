package com.manage.employeemanagement.request;


import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Data
public class EmployeeRegisterRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
