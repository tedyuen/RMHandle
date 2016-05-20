package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.cache.MyMarkerItem;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;

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
public class MyMapTabFragment extends BaseToolbarFragment implements BaiduMap.OnMapLoadedCallback {

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "list_type";
    private int listType = AppSpContact.SP_KEY_MAP_1;//默认未完成

    @Bind(R.id.bmapView)
    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus ms;

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

        if (args != null ) {
            listType = args.getInt(LIST_TYPE);
        }
        setUpViewComponent();
        descImg = inflater.inflate(R.layout.map_bit_desc_img, container, false);
        initOverlay();
        return rootView;
    }


    private void setUpViewComponent() {
        // 地图初始化
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMapLoadedCallback(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//
//            }
//        },4000);
        mClusterManager = new ClusterManager<>(getActivity(), mBaiduMap);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    private void initOverlay(){
        LatLng llA = new LatLng(31.216775+0.001, 121.490184+0.001);
        LatLng llB = new LatLng(31.216775-0.002, 121.490184-0.002);
        LatLng llC = new LatLng(31.216775+0.0025, 121.490184-0.0025);
        LatLng llD = new LatLng(31.216775-0.002, 121.490184+0.003);
        LatLng llE = new LatLng(31.216775-0.004, 121.490184-0.004);

        List<MyMarkerItem> items = new ArrayList<>();
        items.add(new MyMarkerItem(llA,descImg,"1"));
        items.add(new MyMarkerItem(llB,descImg,"29"));
        items.add(new MyMarkerItem(llC,descImg,"5"));
        items.add(new MyMarkerItem(llD,descImg,"32"));
        items.add(new MyMarkerItem(llE,descImg,"7"));

        mClusterManager.addItems(items);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
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
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LONGITUDE,location.getLongitude()+"");
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LATITUDE,location.getLatitude()+"");
                System.out.println("!!==>   "+location.getLatitude()+":"+location.getLongitude());
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
        super.onResume();
    }

    @Override
    public void onDestroy() {
//         退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        if(mMapView!=null){
            mMapView.onDestroy();
            mMapView = null;
        }

        super.onDestroy();
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
