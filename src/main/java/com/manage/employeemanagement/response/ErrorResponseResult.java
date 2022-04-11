package com.manage.employeemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponseResult {

    private String errorText;
    private Date date;

}
