package com.manage.employeemanagement.services.implementation;

import com.manage.employeemanagement.entity.Attendance;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.services.interfaces.AttendanceReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AttendanceReportServiceImpl implements AttendanceReportService {

    public static final String PREFIX = System.getProperty("user.dir") + "/src/main/resources/static/reports/";
    public static final String SUFFIX = ".xlsx";


    public String[] HEADERS = new String[]{
            "ID",
            "First name",
            "Last name",
            "Work start time",
            "Work end time"
    };

    @Override
    public void saveAttendanceReport(List<Attendance> attendances, Date date) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet attendanceSheet = workbook.createSheet("Attendance Report");
        createHeader(attendanceSheet, HEADERS);
        prepareSheet(attendanceSheet, attendances, workbook);
        File file = new File(PREFIX + new SimpleDateFormat("dd-MM-yyy").format(date) + SUFFIX);
        FileOutputStream output = new FileOutputStream(file);
        workbook.write(output);
        output.close();
    }

    private void createHeader(XSSFSheet attendancesSheet, String[] headers) {
        XSSFRow headersRow = attendancesSheet.createRow(0);
        for(int i = 0; i < headers.length; i++) {
            XSSFCell cell = headersRow.createCell(i, CellType.STRING);
            cell.setCellValue(headers[i]);
        }
    }

    private void prepareSheet(XSSFSheet attendanceSheet, List<Attendance> attendances, XSSFWorkbook workbook) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("h:mm:ss"));
        for(int i = 0; i < attendances.size(); i++) {
            Attendance currAttendance = attendances.get(i);
            User employee = currAttendance.getUser();
            XSSFRow row = attendanceSheet.createRow(i + 1);
            row.createCell(0, CellType.NUMERIC).setCellValue(i + 1);
            row.createCell(1, CellType.STRING).setCellValue(employee.getFirstName());
            row.createCell(2, CellType.STRING).setCellValue(employee.getLastName());
            XSSFCell startTimeCell = row.createCell(3);
            XSSFCell endTimeCell = row.createCell(4);
            startTimeCell.setCellValue(currAttendance.getStartTime());
            startTimeCell.setCellStyle(cellStyle);
            endTimeCell.setCellValue(currAttendance.getEndTime());
            endTimeCell.setCellStyle(cellStyle);
        }
    }
}
