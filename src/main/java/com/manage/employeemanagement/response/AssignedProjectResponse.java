package com.manage.employeemanagement.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class AssignedProjectResponse extends ProjectResponse{

    @Getter
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd E")
    private Date assignedDate;

    public AssignedProjectResponse(String projectName, Date startDate, Date endDate, Date assignedDate) {
        super(projectName, startDate, endDate);
        this.assignedDate = assignedDate;
    }
}
