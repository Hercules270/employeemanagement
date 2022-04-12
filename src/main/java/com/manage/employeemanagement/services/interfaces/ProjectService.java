package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(int page, int size, String[] sort);

    Project addNewProject(ProjectRegistrationRequest project) throws ProjectRegistrationException;

    void deleteProject(String projectName);
}
