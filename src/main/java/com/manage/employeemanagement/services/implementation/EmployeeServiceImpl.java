package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.repository.AssignedProjectRepository;
import com.manage.employeemanagement.repository.UserRepository;
import com.manage.employeemanagement.response.employee.EmployeeInformationResponse;
import com.manage.employeemanagement.services.interfaces.EmployeeService;
import com.manage.employeemanagement.utils.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final AssignedProjectRepository assignedProjectRepository;

    @Autowired
    public EmployeeServiceImpl(UserRepository userRepository, AssignedProjectRepository assignedProjectRepository) {
        this.userRepository = userRepository;
        this.assignedProjectRepository = assignedProjectRepository;
    }

    @Override
    public EmployeeInformationResponse getEmployee(String username) {
        Optional<User> employeeOptional = userRepository.findUserByUsername(username);
        if(employeeOptional.isEmpty()) {
            throw new BadRequestException("Records of employee with id " + username + " can't be found");
        }
        User employee = employeeOptional.get();
        Optional<AssignedProject> assignedProjectByUserAndDate = assignedProjectRepository.findAssignedProjectByUserAndDate(employee, new Date());
        return ConverterUtils.convertUserToEmployeeInformation(employee, assignedProjectByUserAndDate);
    }
}













