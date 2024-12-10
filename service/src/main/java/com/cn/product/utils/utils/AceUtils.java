package com.cn.product.utils.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 工具类
 * @version 1.0
 */
public class AceUtils {


    /**
     * 返回当前日期时间字符串<br>
     * 默认格式:yyyy-mm-dd hh:mm:ss
     *
     *
     * @return String 返回当前字符串型日期时间
     */
    public static String getCurrentTime() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }


    public static String getYear() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    public static int getDateOfMonth(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static String getThisMonth() {
        String returnStr = null;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        returnStr = f.format(date);
        return returnStr;
    }

    /**
     * 获取指定日期月份的天数
     * @return
     */
    public static int getYesterday(String strDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDateOfDay(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
