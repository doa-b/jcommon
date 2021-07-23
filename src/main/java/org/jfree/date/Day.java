package org.jfree.date;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by AL22382 on 23-7-2021.
 */
public enum Day {

    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY),
    SATURDAY(Calendar.SATURDAY),
    SUNDAY(Calendar.SUNDAY);

    private final int index;
    private static DateFormatSymbols dateSymbols = new DateFormatSymbols();

    Day(int day) {
        index = day;
    }

    public static Day fromInt(int dayIndex) throws IllegalArgumentException {
        for (Day d : Day.values()) {
            if (d.index == dayIndex)
                return d;
        }
        throw new IllegalArgumentException("Invalid day index " + dayIndex);
    }

    public static Day parse(String s) throws IllegalArgumentException {
        String[] shortWeekdayNames =
                dateSymbols.getShortWeekdays();
        String[] weekdayNames =
                dateSymbols.getWeekdays();

        s = s.trim();
        for (Day day : Day.values()) {
            if (s.equalsIgnoreCase(shortWeekdayNames[day.index]) ||
                    s.equalsIgnoreCase(weekdayNames[day.index])) {
                return day;
            }
        }
        throw new IllegalArgumentException(
                String.format("%s is not a valid weekday string", s));
    }
}
