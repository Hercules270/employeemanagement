package com.manage.employeemanagement.repository;


import com.manage.employeemanagement.entity.Attendance;
import com.manage.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findAttendanceByUserAndDate(User user, Date date);

    List<Attendance> findAttendancesByDate(Date date);
}
