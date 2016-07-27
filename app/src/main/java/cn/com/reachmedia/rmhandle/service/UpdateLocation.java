package cn.com.reachmedia.rmhandle.service;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/20 下午6:45
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UpdateLocation {

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private SharedPreferencesHelper mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();

    public void location(){
        // 定位初始化
        mLocClient = new LocationClient(App.getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }



    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
            System.out.println("timer==>   "+location.getLatitude()+":"+location.getLongitude());

            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
//                sb.append("gps定位成功");
                LogUtils.d("UpdateLocation","gps定位成功");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE,location.getLongitude()+"");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE,location.getLatitude()+"");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE,location.getLongitude()+"");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE,location.getLatitude()+"");
                LogUtils.d("UpdateLocation","网络定位成功");
//                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE,location.getLongitude()+"");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE,location.getLatitude()+"");
                LogUtils.d("UpdateLocation","离线定位成功，离线定位结果也是有效的");
//                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
//                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            mLocClient.stop();

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }

    }
}
