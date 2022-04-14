package com.manage.employeemanagement.services.implementation;


import com.manage.employeemanagement.entity.Attendance;
import com.manage.employeemanagement.repository.AttendanceRepository;
import com.manage.employeemanagement.services.interfaces.AttendanceReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceReportService attendanceReportService;

    @Autowired
    public SchedulerService(AttendanceRepository attendanceRepository, AttendanceReportService attendanceReportService) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceReportService = attendanceReportService;
    }


    @Scheduled(cron = "@midnight")
    public void logAttendance() {
        log.info("----------------------------------------------------");
        log.info("--------------SCHEDULER STARTED---------------------");
        log.info("----------------------------------------------------");
        Date date = new Date(Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli());
        List<Attendance> attendances = attendanceRepository.findAttendancesByDate(date);
        try {
            attendanceReportService.saveAttendanceReport(attendances, date);
        } catch (IOException ex) {
            log.info("Error during report creation.", ex);
        } finally {
            log.info("----------------------------------------------------");
            log.info("--------------SCHEDULER ENDED-----------------------");
            log.info("----------------------------------------------------");
        }
    }

}
