package com.manage.employeemanagement.response.employee;


import com.manage.employeemanagement.response.ProjectResponse;
import lombok.Data;


@Data
public class EmployeeInformationResponse {


    private String firstName;
    private String lastName;
    private ProjectResponse assignedProjectToday;

    public EmployeeInformationResponse(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
