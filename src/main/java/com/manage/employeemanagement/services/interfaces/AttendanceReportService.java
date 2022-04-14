package com.manage.employeemanagement.services.interfaces;

import com.manage.employeemanagement.entity.Attendance;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface AttendanceReportService {

    void saveAttendanceReport(List<Attendance> attendances, Date date) throws IOException;
}
