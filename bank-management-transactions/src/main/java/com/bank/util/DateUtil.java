package com.bank.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate convertStringToLocalDate(String strDate, String patron) {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(patron);
        return LocalDate.parse(strDate, formateador);
    }

    public static LocalDate firstDayMonthCurrent() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(1);
    }

    public static LocalDate lastDateMonthCurrent() {
        LocalDate today = LocalDate.now();
        return today.withDayOfMonth(today.lengthOfMonth());
    }
}
