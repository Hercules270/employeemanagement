package com.manage.employeemanagement.services.implementation;


import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.Project;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.exception.ProjectAssignmentException;
import com.manage.employeemanagement.exception.ProjectRegistrationException;
import com.manage.employeemanagement.repository.AssignedProjectRepository;
import com.manage.employeemanagement.repository.ProjectRepository;
import com.manage.employeemanagement.repository.UserRepository;
import com.manage.employeemanagement.request.ProjectAssignmentRequest;
import com.manage.employeemanagement.request.ProjectRegistrationRequest;
import com.manage.employeemanagement.services.interfaces.ProjectService;
import com.manage.employeemanagement.utils.ConverterUtils;
import com.manage.employeemanagement.utils.CustomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import javax.ws.rs.BadRequestException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final AssignedProjectRepository assignedProjectRepository;

    @Autowired
    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, AssignedProjectRepository assignedProjectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.assignedProjectRepository = assignedProjectRepository;
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

    @Override
    public AssignedProject assignProjectToEmployee(ProjectAssignmentRequest projectAssignmentRequest) throws ProjectAssignmentException {
        Optional<User> employeeOptional = userRepository.findUserByUserId(projectAssignmentRequest.getEmployeeId());
        Optional<Project> projectOptional = projectRepository.findProjectByName(projectAssignmentRequest.getProjectName());
        validateAssignmentRequest(projectAssignmentRequest,employeeOptional, projectOptional);
        User employee = employeeOptional.get();
        Project project = projectOptional.get();
        Date date = ConverterUtils.workdayToDate(projectAssignmentRequest.getDay());
        AssignedProject assignedProject = ConverterUtils.convertProjectAssignmentRequestToAssignedProject(projectAssignmentRequest, employee, project, date);
        log.info("Date is {}", new SimpleDateFormat("yyyy-MM-dd").format(assignedProject.getDate()));
        assignedProjectRepository.save(assignedProject);
        return assignedProject;

    }

    private void validateAssignmentRequest(ProjectAssignmentRequest projectAssignmentRequest, Optional<User> employeeOptional, Optional<Project> projectOptional) throws ProjectAssignmentException {
        if(employeeOptional.isEmpty()) {
            log.info("Employee with employeeId {} doesn't exist.", projectAssignmentRequest.getEmployeeId());
            throw new BadRequestException("Employee with employeeId " + projectAssignmentRequest.getEmployeeId() + " doesn't exist");
        }
        if(projectOptional.isEmpty()) {
            log.info("Project with name {} doesn't exist.", projectAssignmentRequest.getProjectName());
            throw new BadRequestException("Project with name " + projectAssignmentRequest.getProjectName() + " doesn't exist");
        }
        User employee = employeeOptional.get();
        Project project = projectOptional.get();
        Date date = ConverterUtils.workdayToDate(projectAssignmentRequest.getDay());
        Optional<AssignedProject> existingProject = assignedProjectRepository.findAssignedProjectByUserAndDate(employee, date);
        if(existingProject.isPresent()) {
            throw new ProjectAssignmentException("Employee " + employee.getUserId() + " on " + new SimpleDateFormat("yyyy-MM-dd").format(date) + " works on project " + project.getName());
        }
        if(date.before(project.getStartDate()) || date.after(project.getEndDate())) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
            throw new ProjectAssignmentException("Please choose day between project dates. Start date: " +
                    formatter.format(project.getStartDate()) +
                    " End date: " + formatter.format(project.getEndDate()));
        }
    }

}
