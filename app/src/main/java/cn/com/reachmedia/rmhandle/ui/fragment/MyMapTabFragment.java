package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppParamContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.cache.MyMarkerItem;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.param.TaskMapParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.TaskMapController;
import cn.com.reachmedia.rmhandle.ui.ApartmentPointActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/20 上午10:13
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class MyMapTabFragment extends BaseToolbarFragment implements BaiduMap.OnMapLoadedCallback, UiDisplayListener<TaskMapModel> {

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "list_type";
    @Bind(R.id.tv_community)
    TextView tvCommunity;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    private int listType = AppSpContact.SP_KEY_MAP_1;//默认未完成

    @Bind(R.id.bmapView)
    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus ms;


    TaskMapController taskMapController;

    public static MyMapTabFragment newInstance() {
        MyMapTabFragment fragment = new MyMapTabFragment();
        Bundle args = new Bundle();
//        args.putParcelable(AppParamContact.PARAM_KEY_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    public MyMapTabFragment() {
    }

    boolean isFirstLoc = true; // 是否首次定位
    //Marker相关
    View descImg;
    View descGreyImg;
    private ClusterManager<MyMarkerItem> mClusterManager;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        controller = new MyOrderListController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bmap_layout, container, false);
        ButterKnife.bind(this, rootView);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        needTitle();

        Bundle args = getArguments();

        if (args != null) {
            listType = args.getInt(LIST_TYPE);
        }
        setUpViewComponent();
        descImg = inflater.inflate(R.layout.map_bit_desc_img, container, false);
        descGreyImg = inflater.inflate(R.layout.map_bit_desc_img_grey, container, false);
//        initOverlay();
        return rootView;
    }


    private void setUpViewComponent() {
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapLoadedCallback(this);
        mClusterManager = new ClusterManager<>(getActivity(), mBaiduMap);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
    }

    List<MyMarkerItem> items = new ArrayList<>();


    private void initOverlay(List<TaskMapModel.CListBean> list) {
        for(TaskMapModel.CListBean bean:list){
            try {
                LatLng tempL = new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLon()));

                MyMarkerItem item = new MyMarkerItem(tempL, descImg, bean.getPoints()+"",bean);
                if(bean.getStatus()==1){
                    item.setView(descGreyImg);
                }
                items.add(item);

            }catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }

        mClusterManager.addItems(items);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(final MyMarkerItem item:items){
                    if(marker.getPosition().equals(item.getPosition())){
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("已上点位:");
                        buffer.append(item.getBean().getPointe());
                        buffer.append("\t");
                        buffer.append("总点位数:");
                        buffer.append(item.getBean().getPoints());
                        new MaterialDialog.Builder(getActivity())
                                .title(item.getBean().getCommunity())
                                .content(buffer.toString())
                                .positiveText("前往小区")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        System.out.println("====>  "+item.getBean().getCid()+":"+item.getBean().getCommunity());
                                        Intent intent = new Intent(getContext(), ApartmentPointActivity.class);
                                        intent.putExtra(AppParamContact.PARAM_KEY_TITLE,item.getBean().getCommunity());
                                        intent.putExtra(AppParamContact.PARAM_KEY_ID,item.getBean().getCid());
                                        getActivity().startActivity(intent);
                                        getActivity().finish();
                                    }
                                })
                                .show();

                        break;
                    }
                }

                return false;
            }
        });
        mLocClient.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE, location.getLongitude() + "");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE, location.getLatitude() + "");
                System.out.println("!!==>   " + location.getLatitude() + ":" + location.getLongitude());
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(17.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        getData();
        super.onResume();
    }

    @Override
    public void onDestroy() {
//         退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        if (mMapView != null) {
            mMapView.onDestroy();
            mMapView = null;
        }

        super.onDestroy();
    }


    private void getData() {
        HomeFilterUtil homeFilterUtil = HomeFilterUtil.getIns();
        taskMapController = new TaskMapController(this);
        TaskMapParam taskMapParam = new TaskMapParam();
        taskMapParam.startime = homeFilterUtil.startTime;
        taskMapParam.endtime = homeFilterUtil.endTime;
        taskMapParam.space = homeFilterUtil.getAreaId();
        taskMapParam.lon = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE);
        taskMapParam.lat = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE);
        taskMapController.getTaskMap(taskMapParam);
    }

    @Override
    public void onSuccessDisplay(TaskMapModel data) {
        if (data != null) {
            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                tvCommunity.setText("小区:"+data.getComNumber()+"/"+data.getComCount());
                tvPoint.setText("点位:"+data.getPointNumber()+"/"+data.getPointCount());

                initOverlay(data.getCList());
            }
        }
    }

    @Override
    public void onFailDisplay(String errorMsg) {

    }

    @Override
    public void onMapLoaded() {
//        ms = new MapStatus.Builder().target(new LatLng(31.216775, 121.490184)).zoom(8).build();

        ms = new MapStatus.Builder().zoom(9).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
