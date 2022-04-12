package com.manage.employeemanagement.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Future;
import java.util.Date;

@Data
@AllArgsConstructor
public class ProjectRegistrationRequest {

    private String name;

    @Future(message = "Start date must be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @Future(message = "End date must be in future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

}
