package com.manage.employeemanagement.request;


import com.manage.employeemanagement.enums.Workday;
import lombok.Data;

public class ProjectChangeRequest extends ProjectAssignmentRequest {

    public ProjectChangeRequest(String employeeId, String projectName, Workday day) {
        super(employeeId, projectName, day);
    }
}
