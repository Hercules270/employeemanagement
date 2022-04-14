package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.response.employee.EmployeeInformationResponse;

public interface EmployeeService {
    EmployeeInformationResponse getEmployee(String username);
}
