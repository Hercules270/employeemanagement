package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.exception.EmployeeRegistrationException;
import com.manage.employeemanagement.request.EmployeeRegisterRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    UserRepresentation addNewEmployee(EmployeeRegisterRequest employee) throws EmployeeRegistrationException;

    List<User> getAllEmployees();

    Optional<User> getEmployee(String firstName, String lastName);
}
