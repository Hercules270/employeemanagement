package com.manage.employeemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRegistrationResponse {

    private String username;
    private String email;
    private String temporaryPassword;

}
