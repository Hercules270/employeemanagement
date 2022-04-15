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
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/attendance/{come}")
    public ResponseEntity logAttendanceTime(@AuthenticationPrincipal Jwt jwt,
                                       @PathVariable boolean come) throws LoggingException {
        if(come) {
            return employeeBLL.logStartTime(jwt.getClaim("username"));
        } else {
             return employeeBLL.logEndTime(jwt.getClaim("username"));
        }
    }

}
