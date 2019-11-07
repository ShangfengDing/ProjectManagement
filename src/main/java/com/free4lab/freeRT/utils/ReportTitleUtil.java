package com.free4lab.freeRT.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yph on 17-4-14.
 */
public class ReportTitleUtil {

    public static List<String> getWeekStringList() {
        List<String> weekStringList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
        }//如果当前为周一至周五，则返回字符串从上一周开始；如果当前为周六周日，则返回字符串从本周开始
        weekStringList.add(toWeekString(calendar));
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekStringList.add(toWeekString(calendar));
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekStringList.add(toWeekString(calendar));
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekStringList.add(toWeekString(calendar));
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekStringList.add(toWeekString(calendar));
        return weekStringList;
    }

    private static String toWeekString(Calendar calendar) {
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String monday = simpleDateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String sunday = simpleDateFormat.format(calendar.getTime());
        return monday + "-" + sunday;
    }

    public static List<String> getMonthStringList() {
        List<String> monthStringList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        monthStringList.add(toMonthString(calendar));
        calendar.add(Calendar.MONTH, -1);
        monthStringList.add(toMonthString(calendar));
        calendar.add(Calendar.MONTH, -1);
        monthStringList.add(toMonthString(calendar));
        calendar.add(Calendar.MONTH, -1);
        monthStringList.add(toMonthString(calendar));
        calendar.add(Calendar.MONTH, -1);
        monthStringList.add(toMonthString(calendar));
        calendar.add(Calendar.MONTH, -1);
        monthStringList.add(toMonthString(calendar));
        return monthStringList;
    }

    private static String toMonthString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static List<String> getYearStringList() {
        List<String> yearStringList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        yearStringList.add(toYearString(calendar));
        calendar.add(Calendar.YEAR, -1);
        yearStringList.add(toYearString(calendar));
        calendar.add(Calendar.YEAR, -1);
        yearStringList.add(toYearString(calendar));
        return yearStringList;
    }

    private static String toYearString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

}
