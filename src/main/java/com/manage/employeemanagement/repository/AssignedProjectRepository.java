package com.manage.employeemanagement.repository;


import com.manage.employeemanagement.entity.AssignedProject;
import com.manage.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface AssignedProjectRepository extends JpaRepository<AssignedProject, Long> {

    Optional<AssignedProject> findAssignedProjectByUserAndDate(User user, Date date);

}
