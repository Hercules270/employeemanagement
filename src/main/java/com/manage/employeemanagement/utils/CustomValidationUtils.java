package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.enums.Workday;

import java.time.LocalDate;
import java.util.Date;

public class CustomValidationUtils {

    public static boolean endDateBeforeStartDate(Date startDate, Date endDate) {
        return endDate.after(startDate);
    }


}
