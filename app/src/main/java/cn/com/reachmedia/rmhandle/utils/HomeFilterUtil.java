package cn.com.reachmedia.rmhandle.utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
        initCustomer();
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

    public int preWeek = 6;
    public int nextWeek = 20;

    public void initUtil(){
        homeFilterUtil = new HomeFilterUtil();
    }


    public String getNextWednesday(String startTime){
        Calendar wednesday = getCbyStrPlus6(startTime);
        String result = TimeUtils.dateAddByDateForString(wednesday.getTime(),"yyyy-MM-dd",0);
        this.endTime = result;
        return result;
    }

    public String getNextWednesdayNoSet(String startTime){
        Calendar wednesday = getCbyStrPlus6(startTime);
        String result = TimeUtils.dateAddByDateForString(wednesday.getTime(),"yyyy-MM-dd",0);
        return result;
    }


    public String getThursday(){
        Calendar now = TimeUtils.getNow();
        int nowDay = now.get(Calendar.DAY_OF_WEEK);
        if(nowDay>=5){
            now.add(Calendar.DATE,5-nowDay);
        }else{
            now.add(Calendar.DATE,-2-nowDay);
        }
        String result = TimeUtils.dateAddByDateForString(now.getTime(),"yyyy-MM-dd",0);
        setStartTime(result);
        return result;
    }

    public String getCurThursday(){
        Calendar now = TimeUtils.getNow();
        now.add(Calendar.DATE,5-now.get(Calendar.DAY_OF_WEEK));
        String result = TimeUtils.dateAddByDateForString(now.getTime(),"yyyy-MM-dd",0);
        return result;
    }

    public String[] getStartTimes(){
        Calendar start = getCbyStr(getCurThursday());
        String[] result = new String[preWeek+nextWeek+1];
        start.add(Calendar.DATE,-((preWeek+1)*7));
        for(int i=0;i<preWeek+nextWeek+1;i++){
            start.add(Calendar.DATE,7);
            result[i] = TimeUtils.dateAddByDateForString(start.getTime(),"yyyy-MM-dd",0);
        }
        return result;
    }

    public void setStartTime(String str){
        this.startTime = str;
        getNextWednesday(str);
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
    Map<String,String> currentMap2 = new HashMap<>();

    public String defaultArea="全部";

    public String currentArea = defaultArea;
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
        currentMap2.clear();
        for(Object temp:currentProperties2.keySet()){
            currentMap2.put(new String(((String)temp).getBytes("ISO-8859-1"),"utf-8"),currentProperties2.getProperty((String)temp));
        }
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
        return currentMap2.get(key);
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
        String[] all = new String[result2.length+1];
        all[0] = defaultArea;
        System.arraycopy(result2,0,all,1,result2.length);
        return all;
    }

    public String getAreaId(){
        return currentArea.equals(defaultArea)?"":getResultData2(currentArea);
    }
    //    ------------------  区域

    //    ------------------  客户
    public Set<String> customers;
    public Map<String,String> customersMap;

    public Map<String,Set<String>> cacheCustomers;

    public String currentCustomer;
    public String defaultC = "全部";

    public void initCustomer(){
        customers = new HashSet<>();
        customersMap = new HashMap<>();
        customersMap.put(defaultC,"");
//        customers.add(defaultC);
        currentCustomer = defaultC;
        cacheCustomers = new HashMap<>();
    }

    public void clearCustomers(){
        customers.clear();
        customersMap.clear();
        customersMap.put(defaultC,"");
//        customers.add(defaultC);
        currentCustomer = defaultC;

    }


    public String[] getCustomers(){
        String[] all = new String[customers.size()+1];
        all[0] = defaultC;
        String[] result = new String[customers.size()];
        customers.toArray(result);
        System.arraycopy(result,0,all,1,customers.size());
        return all;
    }

    public String getCustomerId(){
        return customersMap.get(currentCustomer);
    }


    public void initCacheCustomer(){
        cacheCustomers.put(startTime,customers);
    }

    public void resetCacheCustomer(){
        if(cacheCustomers.containsKey(startTime)){
            customers = cacheCustomers.get(startTime);
        }
    }

    public void cacheCustomer(String startTime){
        if(cacheCustomers.containsKey(startTime)){
            customers = cacheCustomers.get(startTime);
        }else{
            customers = new HashSet<>();
        }
    }

}
