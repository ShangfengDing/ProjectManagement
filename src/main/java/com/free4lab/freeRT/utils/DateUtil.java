package com.free4lab.freeRT.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    public static Integer findWeekByDate(Timestamp timestamp) {  //通过日期找到星期几
        String datetime = timestamp.toString();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (week < 0)
            week = 0;
        return week;
    }

    public static Timestamp stringToTimestamp (String time){
        Timestamp timestamp=null;
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(time);   //先将字符串转化为date
            timestamp=new Timestamp(date.getTime());//然后将date转化为timestamp

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    public static boolean jugdeLeapYear(int year){ //判断是否为闰年
        if(year%4==0&&year%100!=0||year%400==0){
            return true;
        }else{
           return false;
        }
    }
    //找到某一日期的周一
    public static Timestamp findMonday( String time1)  {
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
        try{
            Date time = simdf.parse(time1);//找到周一的日期
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.setTime(time);
            if(1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        String date= simdf.format(cal.getTime());
        Timestamp timestamp = stringToTimestamp(date);
        return timestamp;

    }

}
