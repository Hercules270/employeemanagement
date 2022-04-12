package com.manage.employeemanagement.services.implementation;


import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.repository.ProjectRepository;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;
import com.manage.employeemanagement.services.interfaces.ProjectService;
import com.manage.employeemanagement.utils.ConverterUtils;
import com.manage.employeemanagement.utils.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects(int page, int size, String[] sort) {
        Pageable pageAndSort = CustomUtils.getPageAndSort(page, size, sort);
        return projectRepository.findAll(pageAndSort).getContent();
    }

    @Override
    public Project addNewProject(ProjectRegistrationRequest projectRequest) throws ProjectRegistrationException {
        Optional<Project> existingProject = projectRepository.findProjectByName(projectRequest.getName());
        if (existingProject.isPresent()) {
            log.info("Project with name: {} already exists", projectRequest.getName());
            throw new ProjectRegistrationException("Project with name: " + projectRequest.getName() + " already exists");
        }
        Project project = ConverterUtils.convertProjectRequestToProject(projectRequest);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String projectName) {
        Optional<Project> project = projectRepository.findProjectByName(projectName);
        project.ifPresent(projectRepository::delete);
    }

    @Override
    public void renameProject(String oldName, String newName) throws BadRequestException {
        Optional<Project> optionalProject = projectRepository.findProjectByName(oldName);
        optionalProject.ifPresentOrElse(project -> {
                    project.setName(newName);
                    projectRepository.save(project);
                }, () -> {
            throw new BadRequestException("Project with name: " + oldName + " doesn't exist");
        });
    }
}
