package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(int page, int size, String[] sort);

}
