package com.manage.employeemanagement.controller;

import com.manage.employeemanagement.bll.EmployeeBLL;
import com.manage.employeemanagement.exception.LoggingException;
import com.manage.employeemanagement.response.ResponseResult;
import com.manage.employeemanagement.response.employee.EmployeeInformationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeBLL employeeBLL;

    @Autowired
    public EmployeeController(EmployeeBLL employeeBLL) {
        this.employeeBLL = employeeBLL;
    }

    @GetMapping("/information")
    public ResponseEntity<ResponseResult<EmployeeInformationResponse>> getCurrentInformation(@AuthenticationPrincipal Jwt jwt) {
        return employeeBLL.getCurrentInformation(jwt.getClaim("username"));
    }

    @PostMapping("/startTime")
    public ResponseEntity logStartTime(@AuthenticationPrincipal Jwt jwt) throws LoggingException {
        return employeeBLL.logStartTime(jwt.getClaim("username"));
    }

}
