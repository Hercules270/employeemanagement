package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface ManagerService {

    UserRepresentation addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException;

}
