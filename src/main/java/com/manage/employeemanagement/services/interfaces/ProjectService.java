package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.exception.ProjectAssignmentException;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.request.ProjectAssignmentRequest;
import com.manage.employeemanagement.request.ProjectChangeRequest;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;

import javax.ws.rs.BadRequestException;
import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(int page, int size, String[] sort);

    Project addNewProject(ProjectRegistrationRequest project) throws ProjectRegistrationException;

    void deleteProject(String projectName);

    void renameProject(String oldName, String newName) throws BadRequestException;

    AssignedProject assignProjectToEmployee(ProjectAssignmentRequest projectAssignmentRequest) throws ProjectAssignmentException;

    AssignedProject changeAssignedProject(ProjectChangeRequest projectChangeRequest) throws ProjectAssignmentException;
}
