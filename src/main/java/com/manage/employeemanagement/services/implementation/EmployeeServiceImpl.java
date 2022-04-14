package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.Attendance;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.exception.LoggingException;
import com.manage.employeemanagement.repository.AssignedProjectRepository;
import com.manage.employeemanagement.repository.AttendanceRepository;
import com.manage.employeemanagement.repository.UserRepository;
import com.manage.employeemanagement.response.employee.EmployeeInformationResponse;
import com.manage.employeemanagement.services.interfaces.EmployeeService;
import com.manage.employeemanagement.utils.ConverterUtils;
import com.manage.employeemanagement.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final AssignedProjectRepository assignedProjectRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public EmployeeServiceImpl(UserRepository userRepository, AssignedProjectRepository assignedProjectRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.assignedProjectRepository = assignedProjectRepository;
        this.attendanceRepository = attendanceRepository;
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

    @Override
    public void logStartTime(String username) throws LoggingException {
        Optional<User> employeeOptional = userRepository.findUserByUsername(username);
        if(employeeOptional.isEmpty()) {
            throw new BadRequestException("Records of employee with id " + username + " can't be found");
        }
        User employee = employeeOptional.get();
        Optional<Attendance> attendanceByDate = attendanceRepository.findAttendanceByUserAndDate(employee, new Date());
        if(attendanceByDate.isPresent()) {
            throw new LoggingException("Employee " + employee.getFirstName() + " " + employee.getLastName() + " has already come to work today.");
        }
        attendanceRepository.save(CustomUtils.getStartTimeAttendance(employee));
    }

    @Override
    public void logEndTime(String username) throws LoggingException {
        Optional<User> employeeOptional = userRepository.findUserByUsername(username);
        if(employeeOptional.isEmpty()) {
            throw new BadRequestException("Records of employee with id " + username + " can't be found");
        }
        User employee = employeeOptional.get();
        Optional<Attendance> attendanceByDate = attendanceRepository.findAttendanceByUserAndDate(employee, new Date());
        if(attendanceByDate.isEmpty()) {
            throw new LoggingException("Employee " + employee.getFirstName() + " " + employee.getLastName() + " has not come to the office today");
        }
        if(attendanceByDate.get().getEndTime() != null) {
            throw new LoggingException("Employee " + employee.getFirstName() + " " + employee.getLastName() + " has already left office today");
        }
        attendanceRepository.save(CustomUtils.getEndTimeAttendance(attendanceByDate));
    }
}













