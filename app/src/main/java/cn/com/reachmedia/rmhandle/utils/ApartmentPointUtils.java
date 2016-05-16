package cn.com.reachmedia.rmhandle.utils;

import cn.com.reachmedia.rmhandle.model.PointListModel;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/16 下午8:09
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/16          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointUtils {
    private static ApartmentPointUtils apartmentPointUtils;
    private ApartmentPointUtils(){}

    public static ApartmentPointUtils getIns(){
        if(apartmentPointUtils==null){
            apartmentPointUtils = new ApartmentPointUtils();
        }
        return apartmentPointUtils;
    }

    public PointListModel pointListModel;
    public String workId;
    public String pointId;



}
