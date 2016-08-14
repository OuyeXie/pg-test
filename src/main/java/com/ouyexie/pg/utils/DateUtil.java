package com.ouyexie.pg.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final DateFormat DATE_FORMAT_YEAR_MONTH_DAY = new SimpleDateFormat("yyyyMMdd");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("dd MM yyyy");
    private static final DateFormat COMPARABLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // TODO: check date like 20150201
    public static Date getFromDateWithNDaysBack(Date currentDate, int back_days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - back_days);
        return calendar.getTime();
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String generateDefaultTo() {
        return String.valueOf(getCurrentYear());
    }

    public static String generateDefaultFrom() {
        return String.valueOf(getCurrentYear() - 4);
    }

    public static String getCurrentYearString() {
        return String.valueOf(getCurrentYear());
    }

    public static String getYearsBack(String year, int yearsBack) {
        return String.valueOf(Integer.valueOf(year) - yearsBack);
    }

    public static String generateDefaultToDate() {
        return String.format("%d1231", getCurrentYear());
    }

    public static String getFromDateWithNYearsBack(String toDate, int years) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_FORMAT_YEAR_MONTH_DAY.parse(toDate));
            return String.format("%d0101", calendar.get(Calendar.YEAR) - years);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateWithNHoursBack(Date date, int hours) {
        return new Date(date.getTime() - hours * 60 * 60 * 1000);
    }

    public static boolean isValidYearMonthDay(String dateString) {
        try {
            DATE_FORMAT_YEAR_MONTH_DAY.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String formatReadableDate(Date date) {
        if (date == null) {
            return "";
        } else {
            return READABLE_DATE_FORMAT.format(date);
        }
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return DATE_FORMAT.format(date);
        }
    }

    public synchronized static String formatComparableDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return COMPARABLE_DATE_FORMAT.format(date);
        }
    }

    public synchronized static Date parseComparableDate(String dateString) {
        try {
            return COMPARABLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseYearMonthDay(String dateString) {
        try {
            return DATE_FORMAT_YEAR_MONTH_DAY.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(String yearMonthDay) {
        Calendar calendar = Calendar.getInstance();
        // noinspection ConstantConditions
        calendar.setTime(parseYearMonthDay(yearMonthDay));
        return calendar.get(Calendar.YEAR);
    }

    public static boolean isDateBetween(Date date, Date from, Date to) {
        return !from.after(date) && !to.before(date);
    }

    public static boolean isYearBetween(int year, int startYear, int endYear) {
        return year >= startYear && year <= endYear;
    }

    public static long getDaysBetweenTwoDate(Date fromDate, Date toDate) {
        long fromTime = fromDate.getTime();
        long toTime = toDate.getTime();
        long diff = toTime - fromTime;
        return diff / (1000L * 60L * 60L * 24L);
    }

    public static void main(String[] args) {
        System.out.println(generateDefaultTo());
    }
}
