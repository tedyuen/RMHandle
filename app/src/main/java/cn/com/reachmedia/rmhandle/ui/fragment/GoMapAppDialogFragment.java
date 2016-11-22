package cn.com.reachmedia.rmhandle.ui.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import java.net.URISyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.IntentUtils;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/21 上午9:52
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/21          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class GoMapAppDialogFragment extends SupportBlurDialogFragment{

    @Bind(R.id.baidu_map)
    LinearLayout baiduMap;
    @Bind(R.id.gaode_map)
    LinearLayout gaodeMap;

    Context context;

    String baiDuSLon;
    String baiDuSLat;
    String gaoDeSLon;
    String gaoDeSLat;
    String baiDuLon;
    String baiDuLat;
    String gaoDeLon;
    String gaoDeLat;
    String address;

    public GoMapAppDialogFragment() {
    }
    @SuppressLint("ValidFragment")
    public GoMapAppDialogFragment(Context context, String baiDu_s_lon, String baiDu_s_lat, String gaoDe_s_lon, String gaoDe_s_lat, String baiDu_lon, String baiDu_lat, String gaoDe_lon, String gaoDe_lat, String address) {
        this.context = context;
        this.baiDuSLon = baiDu_s_lon;
        this.baiDuSLat = baiDu_s_lat;
        this.gaoDeSLon = gaoDe_s_lon;
        this.gaoDeSLat = gaoDe_s_lat;
        this.baiDuLon = baiDu_lon;
        this.baiDuLat = baiDu_lat;
        this.gaoDeLon = gaoDe_lon;
        this.gaoDeLat = gaoDe_lat;
        this.address = address;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(GoMapAppDialogFragment., R.style.GoMapAppDiaLog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.go_map_app_dialog_fragment, container);
        ButterKnife.bind(this, view);
        if (IntentUtils.isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名
            baiduMap.setVisibility(View.VISIBLE);
        }else {
            baiduMap.setVisibility(View.GONE);
        }
        if (IntentUtils.isAvilible(context, "com.autonavi.minimap")) {//传入指定应用包名
            gaodeMap.setVisibility(View.VISIBLE);
        }else {
            gaodeMap.setVisibility(View.GONE);
        }

        return view;
    }
    @OnClick(R.id.baidu_map)
    public void goBaiDuMapApp(){
        System.out.println("sLat"+baiDuSLat+"sLon"+baiDuSLon);
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=" + baiDuSLat + "," + baiDuSLon +
                    "&destination=" + baiDuLat + "," + baiDuLon  +
                    "&mode=driving&region=上海&src=" + getResources().getString(R.string.app_name) + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            context.startActivity(intent);
            dismiss();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            dismiss();
        }
    }
    @OnClick(R.id.gaode_map)
    public void goGaoDeMapApp(){
        Uri uri = Uri.parse("androidamap://route?sourceApplication="+getResources().getString(R.string.app_name)+
                "&slat="+gaoDeSLat+"&slon="+gaoDeSLon+"&sname="+"" +
                "&dlat="+gaoDeLat+"&dlon="+gaoDeLon+ "&dname="+address+
                "&dev=0&m=0&t=2");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setPackage("com.autonavi.minimap");
        context.startActivity(it);
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 8.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 8;
    }
}
