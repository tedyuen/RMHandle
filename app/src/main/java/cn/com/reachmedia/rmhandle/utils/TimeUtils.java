package cn.com.reachmedia.rmhandle.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/16 下午3:40
 * Description: 时间帮助工具
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/16          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TimeUtils {

    /**
     * 获取当前时间
     * @return
     */
    public static Calendar getNow(){
        Calendar date = Calendar.getInstance();
        return date;
    }

    public static String getYear(){
        Calendar now = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        buffer.append(now.get(Calendar.YEAR));
        buffer.append(now.get(Calendar.MONTH));
        buffer.append(now.get(Calendar.DAY_OF_MONTH));
        return buffer.toString();
    }

    public static String getYearText(){
        Calendar now = Calendar.getInstance();
        StringBuffer buffer = new StringBuffer();
        buffer.append(now.get(Calendar.YEAR));
        buffer.append("年");
        int month = now.get(Calendar.MONTH)+1;
        if(month<10){
            buffer.append("0"+month);
        }else{
            buffer.append(month);
        }
        buffer.append("月");
        return buffer.toString();
    }




    /**
     * 根据时间文字获得多久以前
     * 格式：yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public static String getHowLongAgo(String time){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(time, pos);
        long longTime = strtodate.getTime();
        long aa = System.currentTimeMillis() - longTime;
        if (aa <= 1000) {
            return "1秒前";
        }
        long bb = aa / 1000;// 秒
        if (bb < 60) {
            return bb + "秒前";
        }
        long cc = bb / 60;// minitue
        if (cc < 60) {
            return cc + "分钟前";
        }
        long dd = cc / 60;
        if (dd < 24) {
            return dd + "小时前";
        }
        long ee = dd / 24;
        if (ee < 4) {
            return ee + "天前";
        }
        SimpleDateFormat formatter2=new SimpleDateFormat("MM月dd日");
        time = formatter2.format(strtodate);
        return time;
    }


    /**
     * 获得日期显示格式,按照全站规范定义
     * @param time
     * @return
     */
    public static String standardDateFormat(Date time) {
        StringBuilder pattern = new StringBuilder();
        Calendar thisCalendar = Calendar.getInstance();
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        if (thisCalendar.get(Calendar.YEAR) == timeCalendar.get(Calendar.YEAR)) {
            // 同一年
            if ((thisCalendar.get(Calendar.MONTH) == timeCalendar
                    .get(Calendar.MONTH))
                    && (thisCalendar.get(Calendar.DATE) == timeCalendar
                    .get(Calendar.DATE))) {
                // 同一天
                pattern.append("HH:mm");
            } else {
                // 不同天
                pattern.append("MM-dd HH:mm");
            }
        } else {
            pattern.append("yyyy-MM-dd HH:mm");
        }
        return new SimpleDateFormat(pattern.toString()).format(time);
    }

    /**
     * 标准的时间格式化，是否带毫秒值
     *
     * @param time
     * @param hasSecond
     * @return
     */
    public static String standardDateFormat(Date time, boolean hasSecond) {
        StringBuilder pattern = new StringBuilder();
        Calendar thisCalendar = Calendar.getInstance();
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        if (thisCalendar.get(Calendar.YEAR) == timeCalendar.get(Calendar.YEAR)) {
            // 同一年
            if ((thisCalendar.get(Calendar.MONTH) == timeCalendar
                    .get(Calendar.MONTH))
                    && (thisCalendar.get(Calendar.DATE) == timeCalendar
                    .get(Calendar.DATE))) {
                // 同一天
                pattern.append("HH:mm");
            } else {
                // 不同天
                pattern.append("MM-dd HH:mm");
            }
        } else {
            pattern.append("yyyy-MM-dd HH:mm");
        }
        if (hasSecond) {
            pattern.append(":ss");
        }
        return new SimpleDateFormat(pattern.toString()).format(time);
    }

    /**
     * 获得当前时间和参数时间的时间差值,以xx秒 xx分钟 xx小时 xx天显示
     *
     * @param date
     * @return
     */
    public static String viewDateStandardFormat(Date date) {

        long longTime = date.getTime();
        long aa = System.currentTimeMillis() - longTime;
        if (aa <= 1000) {
            return "1秒前";
        }
        long bb = aa / 1000;// 秒
        if (bb < 60) {
            return bb + "秒前";
        }
        long cc = bb / 60;// minitue
        if (cc < 60) {
            return cc + "分钟前";
        }
        long dd = cc / 60;
        if (dd < 24) {
            return dd + "小时前";
        }
        long ee = dd / 24;
        if (ee < 4) {
            return ee + "天前";
        }
//		long ff = ee / 7;
//		if (ff <= 4) {
//			return ff + "周前";
//		}
        return simpleDateFormat(date,"yyyy-MM-dd");
    }
    /**
     * 获得当前时间和参数时间的时间差值,以xx秒 xx分钟 xx小时 xx天显示
     *
     * @param date
     * @return
     */
    public static String viewDateDiff(Date date) {

        long longTime = date.getTime();
        long aa = System.currentTimeMillis() - longTime;
        if (aa <= 1000) {
            return "1秒";
        }
        long bb = aa / 1000;// 秒
        if (bb < 60) {
            return bb + "秒";
        }
        long cc = bb / 60;// minitue
        if (cc < 60) {
            return cc + "分钟";
        }
        long dd = cc / 60;
        if (dd < 24) {
            return dd + "小时";
        }
        long ee = dd / 24;
        if (ee < 7) {
            return ee + "天";
        }
        long ff = ee / 7;
        if (ff <= 4) {
            return ff + "周";
        }
        return standardDateFormat(date);
    }
    /**
     * 时间加减
     *
     * @param date
     * @param num
     * @return
     */
    public static Date dateAdd(Date date, int num) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, num);
            date = cal.getTime();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间加减，输出format格式字符串
     *
     * @param date
     * @param format
     * @param num
     * @return
     */
    public static String dateAddByDateForString(Date date, String format,
                                                int num) {
        String ret = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, num);
            date = cal.getTime();
            ret = sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     * @param date
     * @param format
     * @param num
     * @return
     */
    public static String dateAddByStringForString(String date, String format,
                                                  int num) {
        String ret = null;
        SimpleDateFormat sdf;
        Date dateTime;
        try {
            sdf = new SimpleDateFormat(format);
            dateTime = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateTime);
            cal.add(Calendar.DATE, num);
            dateTime = cal.getTime();
            ret = sdf.format(dateTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date simpleDateParse(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param dateString
     * @param format
     * @return
     */
    public static String simpleDateParseStr(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(sdf.parse(dateString));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param dateString
     * @param format
     * @return
     */
    public static String simpleDateParseStr(String dateString,String nowformat, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(nowformat);
        try {
            Date date = sdf.parse(dateString);
            sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String simpleTimeStampParseStr(Timestamp timestamp, String format){
        String tsStr;
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            //方法一
            tsStr = sdf.format(timestamp);
//            System.out.println(tsStr);
            return tsStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }

    /**
     *
     * @param date
     * @param format
     * @return
     */
    public static String simpleDateFormat(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String todayDateFormat(String format) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据时间段，返回给定格式的日期list<String>
     *
     * @param startDate
     * @param endDate
     * @param format
     * @return
     */
    public static List<String> getDateList(Date startDate, Date endDate,
                                           String format) {
        try {
            List<String> list = new ArrayList<String>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            Calendar calend = Calendar.getInstance();
            calend.setTime(endDate);
            while (cal.before(calend)) {
                list.add(simpleDateFormat(cal.getTime(), format));
                cal.add(Calendar.DATE, 1);
            }
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化时间
     * @param time
     * @return
     */
    public static String formatTimeString(String time){
        return time.substring(0,time.length()-2);
    }

    public static String formatStringe(String strDates)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat aaa = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
        String timeStr = null;
        try
        {
            Date date = aaa.parse(strDates);
            timeStr = sdf.format(date);
        }
        catch(ParseException e)
        {
            timeStr = "";
        }
        return timeStr;
    }

    public static String formatStringeForTime(String strDates)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat aaa = new SimpleDateFormat("yyyyMMddHHmm", Locale.SIMPLIFIED_CHINESE);
        String timeStr = null;
        try
        {
            Date date = aaa.parse(strDates);
            timeStr = sdf.format(date);
        }
        catch(ParseException e)
        {
            timeStr = "";
        }
        return timeStr;
    }

    /**
     * 时间相减，返回两时间的天数
     * @param date1
     * @param date2
     * @return
     */
    public static long formatStringDataToDay(String date1,String date2){
        Date time1,time2;   //定义时间类型
        long day = -1;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            time1 = inputFormat.parse(date1); //将字符型转换成日期型
            time2 = inputFormat.parse(date2); //将字符型转换成日期型
            long temp = time2.getTime() - time1.getTime();

            day = temp / (1000 * 60 * 60 * 24);                //相差天数
            long hours = temp / (1000 * 60 * 60);                //相差小时数
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }
}
