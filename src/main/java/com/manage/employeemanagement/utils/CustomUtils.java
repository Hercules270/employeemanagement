package com.manage.employeemanagement.utils;

import com.manage.employeemanagement.entity.Attendance;
import com.manage.employeemanagement.entity.User;
import com.manage.employeemanagement.enums.Workday;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.passay.DigestDictionaryRule.ERROR_CODE;

public class CustomUtils {

    public static String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    public static Pageable getPageAndSort(int page, int size, String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if(sort[0].contains(",")) {
            for (String sortOrder : sort) {
                orders.add(ConverterUtils.convertStringToOrder(sortOrder));
            }
        } else {
            orders.add(new Sort.Order(sort[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sort[0]));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

    public static Attendance getStartTimeAttendance(User employee) {
        Attendance attendance = new Attendance();
        attendance.setStartTime(new Date());
        attendance.setUser(employee);
        attendance.setDate(new Date());
        return attendance;
    }

}
