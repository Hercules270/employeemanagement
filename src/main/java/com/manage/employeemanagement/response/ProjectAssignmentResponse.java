package com.manage.employeemanagement.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProjectAssignmentResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String employeeId;
    private String projectName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd E")
    private Date date;

}
