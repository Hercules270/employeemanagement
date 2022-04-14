package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.enums.Workday;

import javax.ws.rs.BadRequestException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class CustomValidationUtils {

    public static boolean endDateBeforeStartDate(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }

}
