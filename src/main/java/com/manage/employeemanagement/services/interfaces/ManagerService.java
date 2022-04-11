package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;

public interface ManagerService {

    void addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException;

}
