package com.manage.employeemanagement.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeesResponse {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
}
