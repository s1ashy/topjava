package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        return LocalDateTime.parse(text);
    }

    @Override
    public String print(LocalDateTime dateTime, Locale locale) {
        return dateTime.toString();
    }
}
