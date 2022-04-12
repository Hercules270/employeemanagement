package com.manage.employeemanagement.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllEmployeesResponse {

    private String firstName;
    private String lastName;
    private String email;

}
