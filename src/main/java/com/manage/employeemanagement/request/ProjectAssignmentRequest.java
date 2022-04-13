package com.manage.employeemanagement.request;


import com.manage.employeemanagement.enums.Workday;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class ProjectAssignmentRequest {

    private String employeeId;
    private String projectName;

    @Enumerated(EnumType.STRING)
    private Workday day;

}
