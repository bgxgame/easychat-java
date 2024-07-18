package com.easyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author brace
 * @Date 2024/7/18 14:57
 * @PackageName:com.easyjava.utils
 * @ClassName: DateUtils
 * @Description: TODO
 * @Version 1.0
 */
public class DateUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String _YYYYMMDD = "yyyy/MM/dd";

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String date, String pattern) {
        Date sdate = null;
        try {
            sdate = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdate;
    }


}
