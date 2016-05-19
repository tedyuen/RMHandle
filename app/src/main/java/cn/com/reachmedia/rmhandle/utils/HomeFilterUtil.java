package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/19 下午3:18
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/19          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class HomeFilterUtil {
    private static HomeFilterUtil homeFilterUtil;
    private HomeFilterUtil(){
        if(startTime==null)
            getThursday();
        initCityPro(App.getContext());
    }
    public static HomeFilterUtil getIns(){
        if(homeFilterUtil==null){
            homeFilterUtil = new HomeFilterUtil();
        }
        return homeFilterUtil;
    }

    //    ------------------  日期
    public String startTime;
    public String endTime;
    public String space;
    public String customer;

    public int preWeek = 3;
    public int nextWeek = 4;

    public String getNextWednesday(String startTime){
        Calendar wednesday = getCbyStrPlus6(startTime);
        String result = TimeUtils.dateAddByDateForString(wednesday.getTime(),"yyyy-MM-dd",0);
        this.endTime = result;
        return result;
    }


    public String getThursday(){
        Calendar now = TimeUtils.getNow();
        now.add(Calendar.DATE,5-now.get(Calendar.DAY_OF_WEEK));
        String result = TimeUtils.dateAddByDateForString(now.getTime(),"yyyy-MM-dd",0);
        this.startTime = result;
        this.endTime = getNextWednesday(result);
        return result;
    }

    public String[] getStartTimes(){
        Calendar start = getCbyStr(this.startTime);
        String[] result = new String[preWeek+nextWeek+1];
        start.add(Calendar.DATE,-((preWeek+1)*7));
        for(int i=0;i<preWeek+nextWeek;i++){
            start.add(Calendar.DATE,7);
            result[i] = TimeUtils.dateAddByDateForString(start.getTime(),"yyyy-MM-dd",0);
            System.out.println(result[i]);
        }
        return result;
    }


    public Calendar getCbyStr(String str){
        Calendar thursday = Calendar.getInstance();
        thursday.setTime(TimeUtils.simpleDateParse(str,"yyyy-MM-dd"));
        return thursday;
    }

    public Calendar getCbyStrPlus6(String str){
        Calendar thursday = Calendar.getInstance();
        thursday.setTime(TimeUtils.simpleDateParse(str,"yyyy-MM-dd"));
        thursday.add(Calendar.DATE,6);
        return thursday;
    }
    //    ------------------  日期

    //    ------------------  区域
    public Properties cityProperties;
    public Properties currentProperties;
    public Properties currentProperties2;
    public Properties initCityPro(Context context){
        cityProperties = new Properties();
        try {
            cityProperties.load(context.getAssets().open("city.properties"));
            String cityName = new String(cityProperties.getProperty(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_CITY_ID)).getBytes("ISO-8859-1"),"utf-8");

            initCityData(context,cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityProperties;
    }


    public Properties initCityData(Context context,String cityName) throws Exception {
        currentProperties = new Properties();
        currentProperties2 = new Properties();
        System.out.println(cityName+".properties"+"\ttedyuen\t"+cityName+"2.properties");
        currentProperties.load(context.getAssets().open(cityName+".properties"));
        currentProperties2.load(context.getAssets().open(cityName+"2.properties"));
        System.out.println(currentProperties2.keySet().size());
        return currentProperties;
    }

    /**
     * 获取当前城市的result
     * @param key
     * @return
     */
    public String getResultData(String key){
        try {
            return new String(currentProperties.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getResultData2(String key){
        try {
            return new String(currentProperties2.getProperty(key).getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String[] getResultData(){
        String[] result = new String[currentProperties2.keySet().size()];
        String[] result2 = new String[currentProperties2.keySet().size()];
        currentProperties2.keySet().toArray(result);
        for(int i=0;i<result.length;i++){
            try {
                result2[i] = new String(result[i].getBytes("ISO-8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result2;
    }


}
