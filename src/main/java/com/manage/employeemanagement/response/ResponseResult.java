package com.manage.employeemanagement.response;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseResult<T> {

    private Date date;
    private T data;
    private String errorKey;
    private long errorCode;

}
