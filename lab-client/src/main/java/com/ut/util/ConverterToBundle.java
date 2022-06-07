package com.ut.util;



import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public final class ConverterToBundle {
    private ConverterToBundle() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static LocalDateTime parseDateTime(String date) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
        System.out.println(date);
        return LocalDateTime.parse(date, parser);
    }

    public static String formatDateTime(TemporalAccessor dateToFormat, Locale locale) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.MEDIUM, FormatStyle.MEDIUM,
        Chronology.ofLocale(locale), locale));
        return dateTimeFormatter.format(dateToFormat);
    }
    public static String localeNumber(Number number, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return numberFormat.format(number);
    }
}
