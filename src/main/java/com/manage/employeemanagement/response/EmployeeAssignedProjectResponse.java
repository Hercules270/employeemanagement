package com.manage.employeemanagement.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EmployeeAssignedProjectResponse extends EmployeesResponse{


    @Getter
    @Setter
    private List<AssignedProjectResponse> assignedProjects = new ArrayList<>();


    public EmployeeAssignedProjectResponse(String employeeId, String firstName, String lastName, String email) {
        super(employeeId, firstName, lastName, email);
    }


}
