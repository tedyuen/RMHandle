package cn.com.reachmedia.rmhandle.cache;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.clusterutil.clustering.ClusterItem;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;

import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/20 下午4:32
 * Description: 每个Marker点，包含Marker点坐标以及图标
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class MyMarkerItem implements ClusterItem {

    private final LatLng mPosition;
    private View view;
    private String text;

    public MyMarkerItem(LatLng latLng,View view,String text) {
        mPosition = latLng;
        this.view = view;
        this.text = text;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        TextView descText = ((TextView)view.findViewById(R.id.tv_count));
        descText.setText(text);
        return BitmapDescriptorFactory.fromView(view);
    }
}
