/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------------
 * SerialDate.java
 * ---------------
 * (C) Copyright 2001-2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SerialDate.java,v 1.9 2011/10/17 20:08:22 mungady Exp $
 *
 * Changes (from 11-Oct-2001)
 * --------------------------
 * 11-Oct-2001 : Re-organised the class and moved it to new package
 *               com.jrefinery.date (DG);
 * 05-Nov-2001 : Added a getDescription() method, and eliminated NotableDate
 *               class (DG);
 * 12-Nov-2001 : IBD requires setDescription() method, now that NotableDate
 *               class is gone (DG);  Changed getPreviousDayOfWeek(),
 *               getFollowingDayOfWeek() and getNearestDayOfWeek() to correct
 *               bugs (DG);
 * 05-Dec-2001 : Fixed bug in SpreadsheetDate class (DG);
 * 29-May-2002 : Moved the month constants into a separate interface
 *               (MonthConstants) (DG);
 * 27-Aug-2002 : Fixed bug in addMonths() method, thanks to N???levka Petr (DG);
 * 03-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Implemented Serializable (DG);
 * 29-May-2003 : Fixed bug in addMonths method (DG);
 * 04-Sep-2003 : Implemented Comparable.  Updated the isInRange javadocs (DG);
 * 05-Jan-2005 : Fixed bug in addYears() method (1096282) (DG);
 *
 */

package org.jfree.date;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DayDate implements Comparable,
        Serializable,
        MonthConstants {

    public static enum Month {
        JANUARY(1),
        FEBRUARY(2),
        MARCH(3),
        APRIL(4),
        MAY(5),
        JUNE(6),
        JULY(7),
        AUGUST(8),
        SEPTEMBER(9),
        OCTOBER(10),
        NOVEMBER(11),
        DECEMBER(12);

        Month(int index) {
            this.index = index;
        }

        public static Month make(int monthIndex) {
            for (Month m : Month.values()) {
                if (m.index == monthIndex)
                    return m;
            }
            throw new IllegalArgumentException("Invalid month index " + monthIndex);
        }

        public final int index;
    }

    /**
     * For serialization.
     */
    private static final long serialVersionUID = -293716040467423637L;

    public static final DateFormatSymbols
            DATE_FORMAT_SYMBOLS = new SimpleDateFormat().getDateFormatSymbols();

    public static final int MONDAY = Calendar.MONDAY;
    public static final int TUESDAY = Calendar.TUESDAY;
    public static final int WEDNESDAY = Calendar.WEDNESDAY;
    public static final int THURSDAY = Calendar.THURSDAY;
    public static final int FRIDAY = Calendar.FRIDAY;
    public static final int SATURDAY = Calendar.SATURDAY;

    /**
     * Useful constant for Sunday. Equivalent to java.util.Calendar.SUNDAY.
     */
    public static final int SUNDAY = Calendar.SUNDAY;

    /**
     * The number of days in each month in non leap years.
     */
    static final int[] LAST_DAY_OF_MONTH =
            {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public enum WeekInMonth {
        FIRST(1), SECOND(2), THIRD(3), FOURTH(4), LAST(0);
        public final int index;

        WeekInMonth(int index) {
            this.index = index;
        }
    }
    // TODO replace these
    public static final int FIRST_WEEK_IN_MONTH = 1;
    public static final int SECOND_WEEK_IN_MONTH = 2;
    public static final int THIRD_WEEK_IN_MONTH = 3;
    public static final int FOURTH_WEEK_IN_MONTH = 4;

    public static final int LAST_WEEK_IN_MONTH = 0;

    // TODO remove these
    public static final int INCLUDE_NONE = 0;
     public static final int INCLUDE_FIRST = 1;
     public static final int INCLUDE_SECOND = 2;
    public static final int INCLUDE_BOTH = 3;

    public enum WeekdayRange {
        LAST(-1), NEAREST(0), NEXT(1);
        public final int index;

        WeekdayRange(int index) {
            this.index = index;
        }
    }

    // TODO remove these
    public static final int PRECEDING = -1;
    public static final int NEAREST = 0;
    public static final int FOLLOWING = 1;

    /**
     * Returns <code>true</code> if the supplied integer code represents a
     * valid day-of-the-week, and <code>false</code> otherwise.
     *
     * @param code the code being checked for validity.
     * @return <code>true</code> if the supplied integer code represents a
     * valid day-of-the-week, and <code>false</code> otherwise.
     */
    public static boolean isValidWeekdayCode(final int code) {

        switch (code) {
            case SUNDAY:
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
            case SATURDAY:
                return true;
            default:
                return false;
        }
    }


    /**
     * Returns an array of month names.
     *
     * @return an array of month names.
     */
    public static String[] getMonthNames() {
        return DATE_FORMAT_SYMBOLS.getMonths();
    }



    /**
     * Returns true if the supplied integer code represents a valid month.
     *
     * @param code the code being checked for validity.
     * @return <code>true</code> if the supplied integer code represents a
     * valid month.
     */
    // TODO should be deleted after Month ENUM is fully integrated
    public static boolean isValidMonthCode(final int code) {

        switch (code) {
            case JANUARY:
            case FEBRUARY:
            case MARCH:
            case APRIL:
            case MAY:
            case JUNE:
            case JULY:
            case AUGUST:
            case SEPTEMBER:
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                return true;
            default:
                return false;
        }

    }

    /**
     * Returns the quarter for the specified month.
     *
     * @param code the month code (1-12).
     * @return the quarter that the month belongs to.
     */
    // TODO Remove when Month is integrated
    public static int monthCodeToQuarter(final int code) {

        switch (code) {
            case JANUARY:
            case FEBRUARY:
            case MARCH:
                return 1;
            case APRIL:
            case MAY:
            case JUNE:
                return 2;
            case JULY:
            case AUGUST:
            case SEPTEMBER:
                return 3;
            case OCTOBER:
            case NOVEMBER:
            case DECEMBER:
                return 4;
            default:
                throw new IllegalArgumentException(
                        "SerialDate.monthCodeToQuarter: invalid month code." );
        }

    }

    /**
     * Returns a string representing the supplied month.
     * <p>
     * The string returned is the long form of the month name taken from the
     * default locale.
     *
     * @param month the month.
     * @return a string representing the supplied month.
     */
    // TODO Remove
    public static String monthCodeToString(final int month) {

        return monthCodeToString(month, false);

    }

    /**
     * Returns a string representing the supplied month.
     * <p>
     * The string returned is the long or short form of the month name taken
     * from the default locale.
     *
     * @param month     the month.
     * @param shortened if <code>true</code> return the abbreviation of the
     *                  month.
     * @return a string representing the supplied month.
     */
    // TODO Remove
    public static String monthCodeToString(final int month,
                                           final boolean shortened) {

        // check arguments...
        if (!isValidMonthCode(month)) {
            throw new IllegalArgumentException(
                    "SerialDate.monthCodeToString: month outside valid range." );
        }

        final String[] months;

        if (shortened) {
            months = DATE_FORMAT_SYMBOLS.getShortMonths();
        } else {
            months = DATE_FORMAT_SYMBOLS.getMonths();
        }

        return months[month - 1];

    }

    /**
     * Converts a string to a month code.
     * <p>
     * This method will return one of the constants JANUARY, FEBRUARY, ...,
     * DECEMBER that corresponds to the string.  If the string is not
     * recognised, this method returns -1.
     *
     * @param s the string to parse.
     * @return <code>-1</code> if the string is not parseable, the month of the
     * year otherwise.
     */
    // TODO REMOVE
    public static int stringToMonthCode(String s) {

        final String[] shortMonthNames = DATE_FORMAT_SYMBOLS.getShortMonths();
        final String[] monthNames = DATE_FORMAT_SYMBOLS.getMonths();

        int result = -1;
        s = s.trim();

        // first try parsing the string as an integer (1-12)...
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            // suppress
        }

        // now search through the month names...
        if ((result < 1) || (result > 12)) {
            result = -1;
            for (int i = 0; i < monthNames.length; i++) {
                if (s.equalsIgnoreCase(shortMonthNames[i])) {
                    result = i + 1;
                    break;
                }
                if (s.equalsIgnoreCase(monthNames[i])) {
                    result = i + 1;
                    break;
                }
            }
        }

        return result;

    }

    /**
     * Returns true if the supplied integer code represents a valid
     * week-in-the-month, and false otherwise.
     *
     * @param code the code being checked for validity.
     * @return <code>true</code> if the supplied integer code represents a
     * valid week-in-the-month.
     */
    public static boolean isValidWeekInMonthCode(final int code) {

        switch (code) {
            case FIRST_WEEK_IN_MONTH:
            case SECOND_WEEK_IN_MONTH:
            case THIRD_WEEK_IN_MONTH:
            case FOURTH_WEEK_IN_MONTH:
            case LAST_WEEK_IN_MONTH:
                return true;
            default:
                return false;
        }

    }

    /**
     * Determines whether or not the specified year is a leap year.
     *
     * @param year the year (in the range 1900 to 9999).
     * @return <code>true</code> if the specified year is a leap year.
     */
    public static boolean isLeapYear(final int year) {
        boolean fourth = year % 4 == 0;
        boolean hundredth = year % 100 == 0;
        boolean fourHundredth = year % 400 == 0;
        return fourth && (!hundredth || fourHundredth);
    }

    /**
     * Returns the number of the last day of the month, taking into account
     * leap years.
     *
     * @param month the month.
     * @param yyyy  the year (in the range 1900 to 9999).
     * @return the number of the last day of the month.
     */

    public static int lastDayOfMonth(final int month, final int yyyy) {

        final int result = LAST_DAY_OF_MONTH[month];
        if (month != FEBRUARY) {
            return result;
        } else if (isLeapYear(yyyy)) {
            return result + 1;
        } else {
            return result;
        }

    }

    /**
     * Creates a new date by adding the specified number of days to the base
     * date.
     *
     * @param days the number of days to add (can be negative).
     * @param base the base date.
     * @return a new date.
     */
    public static DayDate plusDays(final int days, final DayDate base) {
        return DayDateFactory.makeDate(base.getOrdinalDay() + days);

    }

    /**
     * Creates a new date by adding the specified number of months to the base
     * date.
     * <p>
     * If the base date is close to the end of the month, the day on the result
     * may be adjusted slightly:  31 May + 1 month = 30 June.
     *
     * @param months the number of months to add (can be negative).
     * @param base   the base date.
     * @return a new date.
     */
    public static DayDate plusMonths(final int months,
                                     final DayDate base) {

        final int yy = (12 * base.getYear() + base.getMonth() + months - 1)
                / 12;
        final int mm = (12 * base.getYear() + base.getMonth() + months - 1)
                % 12 + 1;
        final int dd = Math.min(
                base.getDayOfMonth(), DayDate.lastDayOfMonth(mm, yy)
        );
        return DayDateFactory.makeDate(dd, mm, yy);

    }

    /**
     * Creates a new date by adding the specified number of years to the base
     * date.
     *
     * @param years the number of years to add (can be negative).
     * @param base  the base date.
     * @return A new date.
     */
    public static DayDate plusYears(final int years, final DayDate base) {

        final int baseY = base.getYear();
        final int baseM = base.getMonth();
        final int baseD = base.getDayOfMonth();

        final int targetY = baseY + years;
        final int targetD = Math.min(
                baseD, DayDate.lastDayOfMonth(baseM, targetY)
        );

        return DayDateFactory.makeDate(targetD, baseM, targetY);
    }

    /**
     * Returns the latest date that falls on the specified day-of-the-week and
     * is BEFORE the base date.
     *
     * @param targetWeekday a code for the target day-of-the-week.
     * @param base          the base date.
     * @return the latest date that falls on the specified day-of-the-week and
     * is BEFORE the base date.
     */
    public static DayDate getPreviousDayOfWeek(final int targetWeekday,
                                               final DayDate base) {

        // check arguments...
        if (!DayDate.isValidWeekdayCode(targetWeekday)) {
            throw new IllegalArgumentException(
                    "Invalid day-of-the-week code."
            );
        }

        // find the date...
        final int adjust;
        final int baseDOW = base.getDayOfWeek().ordinal();
        if (baseDOW > targetWeekday) {
            adjust = Math.min(0, targetWeekday - baseDOW);
        } else {
            adjust = -7 + Math.max(0, targetWeekday - baseDOW);
        }

        return DayDate.plusDays(adjust, base);

    }

    /**
     * Returns the earliest date that falls on the specified day-of-the-week
     * and is AFTER the base date.
     *
     * @param targetWeekday a code for the target day-of-the-week.
     * @param base          the base date.
     * @return the earliest date that falls on the specified day-of-the-week
     * and is AFTER the base date.
     */
    public static DayDate getFollowingDayOfWeek(final int targetWeekday,
                                                final DayDate base) {

        // check arguments...
        if (!DayDate.isValidWeekdayCode(targetWeekday)) {
            throw new IllegalArgumentException(
                    "Invalid day-of-the-week code."
            );
        }

        // find the date...
        final int adjust;
        final int baseDOW = base.getDayOfWeek().ordinal();
        if (baseDOW >= targetWeekday) {
            adjust = 7 + Math.min(0, targetWeekday - baseDOW);
        } else {
            adjust = Math.max(0, targetWeekday - baseDOW);
        }

        return DayDate.plusDays(adjust, base);
    }

    /**
     * Returns the date that falls on the specified day-of-the-week and is
     * CLOSEST to the base date.
     *
     * @param targetDOW a code for the target day-of-the-week.
     * @param base      the base date.
     * @return the date that falls on the specified day-of-the-week and is
     * CLOSEST to the base date.
     */
    public static DayDate getNearestDayOfWeek(final int targetDOW,
                                              final DayDate base) {

        // check arguments...
        if (!DayDate.isValidWeekdayCode(targetDOW)) {
            throw new IllegalArgumentException(
                    "Invalid day-of-the-week code."
            );
        }

        // find the date...
        int delta = targetDOW - base.getDayOfWeek().ordinal();
        int positveDelta = delta + 7;
        int adjust = positveDelta % 7;
        if (adjust > 3) {
            adjust -= 7;
        }

        return DayDate.plusDays(adjust, base);

    }

    /**
     * Rolls the date forward to the last day of the month.
     *
     * @param base the base date.
     * @return a new serial date.
     */
    public DayDate getEndOfCurrentMonth(final DayDate base) {
        final int last = DayDate.lastDayOfMonth(
                base.getMonth(), base.getYear()
        );
        return DayDateFactory.makeDate(last, base.getMonth(), base.getYear());
    }

    /**
     * Returns a <code>java.util.Date</code> equivalent to this date.
     *
     * @return The date.
     */
    public Date toDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth() - 1, getDayOfMonth(), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * Returns the serial number for the date, where 1 January 1900 = 2 (this
     * corresponds, almost, to the numbering system used in Microsoft Excel for
     * Windows and Lotus 1-2-3).
     *
     * @return the serial number for the date.
     */
    public abstract int getOrdinalDay();

    public String toString() {
        return getDayOfMonth() + "-" + DayDate.monthCodeToString(getMonth())
                + "-" + getYear();
    }

    public abstract Day getDayOfWeekForOrdinalZero();

    /**
     * Returns the year (assume a valid range of 1900 to 9999).
     *
     * @return the year.
     */
    public abstract int getYear();

    /**
     * Returns the month (January = 1, February = 2, March = 3).
     *
     * @return the month of the year.
     */
    public abstract int getMonth();

    /**
     * Returns the day of the month.
     *
     * @return the day of the month.
     */
    public abstract int getDayOfMonth();

    /**
     * Returns a code representing the day of the week.
     * <P>
     * The codes are defined in the {@link DayDate} class as:
     * <code>SUNDAY</code>, <code>MONDAY</code>, <code>TUESDAY</code>,
     * <code>WEDNESDAY</code>, <code>THURSDAY</code>, <code>FRIDAY</code>, and
     * <code>SATURDAY</code>.
     *
     * @return A code representing the day of the week.
     */
    public Day getDayOfWeek() {
        Day startingDay = getDayOfWeekForOrdinalZero();
        int startingOffset = startingDay.ordinal() - Day.SUNDAY.ordinal();
        return Day.make((getOrdinalDay() + startingOffset)  % 7 + 1);
    }

    /**
     * Returns the difference (in days) between this date and the specified
     * 'other' date.
     * <p>
     * The result is positive if this date is after the 'other' date and
     * negative if it is before the 'other' date.
     *
     * @param other the date being compared to.
     * @return the difference between this and the other date.
     */
    public abstract int compare(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date as
     * the specified SerialDate.
     */
    public abstract boolean isOn(DayDate other);

    /**
     * Returns true if this SerialDate represents an earlier date compared to
     * the specified SerialDate.
     *
     * @param other The date being compared to.
     * @return <code>true</code> if this SerialDate represents an earlier date
     * compared to the specified SerialDate.
     */
    public abstract boolean isBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isOnOrBefore(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isAfter(DayDate other);

    /**
     * Returns true if this SerialDate represents the same date as the
     * specified SerialDate.
     *
     * @param other the date being compared to.
     * @return <code>true</code> if this SerialDate represents the same date
     * as the specified SerialDate.
     */
    public abstract boolean isOnOrAfter(DayDate other);


    /**
     * Returns <code>true</code> if this {@link DayDate} is within the
     * specified range (caller specifies whether or not the end-points are
     * included).  The date order of d1 and d2 is not important.
     *
     * @param d1      a boundary date for the range.
     * @param d2      the other boundary date for the range.
     * @param interval a code that controls whether or not the start and end
     *                dates are included in the range.
     * @return A boolean.
     */
    public boolean isInRange(DayDate d1, DayDate d2, DateInterval interval) {
        int left = Math.min(d1.getOrdinalDay(), d2.getOrdinalDay());
        int right = Math.max(d1.getOrdinalDay(), d2.getOrdinalDay());
        return interval.isIn(getOrdinalDay(), left, right);
    }

    /**
     * Returns the latest date that falls on the specified day-of-the-week and
     * is BEFORE this date.
     *
     * @param targetDOW a code for the target day-of-the-week.
     * @return the latest date that falls on the specified day-of-the-week and
     * is BEFORE this date.
     */
    public DayDate getPreviousDayOfWeek(final int targetDOW) {
        return getPreviousDayOfWeek(targetDOW, this);
    }

    /**
     * Returns the earliest date that falls on the specified day-of-the-week
     * and is AFTER this date.
     *
     * @param targetDOW a code for the target day-of-the-week.
     * @return the earliest date that falls on the specified day-of-the-week
     * and is AFTER this date.
     */
    public DayDate getFollowingDayOfWeek(final int targetDOW) {
        return getFollowingDayOfWeek(targetDOW, this);
    }

    /**
     * Returns the nearest date that falls on the specified day-of-the-week.
     *
     * @param targetDOW a code for the target day-of-the-week.
     * @return the nearest date that falls on the specified day-of-the-week.
     */
    public DayDate getNearestDayOfWeek(final int targetDOW) {
        return getNearestDayOfWeek(targetDOW, this);
    }

}