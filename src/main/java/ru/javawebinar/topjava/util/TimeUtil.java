package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    private static String dtFormat = ResourceBundle.getBundle("application").getString("dateTimeFormat");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dtFormat);

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? "": localDateTime.format(formatter);
    }

}
