package com.free4lab.freeRT.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    public static Integer findWeekByDate(Timestamp timestamp) {  //ͨ�������ҵ����ڼ�
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
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; // ָʾһ�������е�ĳ�졣
        if (week < 0)
            week = 0;
        return week;
    }

    public static Timestamp stringToTimestamp (String time){
        Timestamp timestamp=null;
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(time);   //�Ƚ��ַ���ת��Ϊdate
            timestamp=new Timestamp(date.getTime());//Ȼ��dateת��Ϊtimestamp

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    public static boolean jugdeLeapYear(int year){ //�ж��Ƿ�Ϊ����
        if(year%4==0&&year%100!=0||year%400==0){
            return true;
        }else{
           return false;
        }
    }
    //�ҵ�ĳһ���ڵ���һ
    public static Timestamp findMonday( String time1)  {
        SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
        try{
            Date time = simdf.parse(time1);//�ҵ���һ������
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.setTime(time);
            if(1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);//����һ�����ڵĵ�һ�죬���й���ϰ��һ�����ڵĵ�һ��������һ

        int day = cal.get(Calendar.DAY_OF_WEEK);//��õ�ǰ������һ�����ڵĵڼ���
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//���������Ĺ��򣬸���ǰ���ڼ�ȥ���ڼ���һ�����ڵ�һ��Ĳ�ֵ
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        String date= simdf.format(cal.getTime());
        Timestamp timestamp = stringToTimestamp(date);
        return timestamp;

    }

}
