package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/21 上午10:54
 * Description: 离线地图管理
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/21          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class OfflineMapManageFragment extends BaseFragment {
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";


    /**
     * 已下载的离线地图信息列表
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private LocalMapAdapter lAdapter = null;

    private MKOfflineMap mOffline;

    @Bind(R.id.listview)
    ListView localMapListView;

    private OfflineMapActivity activity;

    int cityid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offline_map_list, container, false);
        ButterKnife.bind(this, rootView);
        activity = (OfflineMapActivity)getActivity();


        Bundle args = getArguments();

        setUpViewComponent();

        return rootView;
    }

    private void setUpViewComponent() {
        mOffline = activity.mOffline;

        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }

        lAdapter = new LocalMapAdapter();
        localMapListView.setAdapter(lAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }


    /**
     * 开始下载
     *
     * @param cityid
     */
    public void start(int cityid) {
        this.cityid = cityid;
        mOffline.start(cityid);
        Toast.makeText(getActivity(), "开始下载离线地图. cityid: " + cityid, Toast.LENGTH_SHORT)
                .show();
        updateView();
    }


    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<>();
        }
        lAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityid);
        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
            mOffline.pause(cityid);
        }
        super.onPause();
    }

    /**
     * 离线地图管理列表适配器
     */
    public class LocalMapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = View.inflate(getActivity(),
                    R.layout.offline_localmap_list, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(View view, final MKOLUpdateElement e) {
            Button display = (Button) view.findViewById(R.id.display);
            Button remove = (Button) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            ratio.setText(e.ratio + "%");
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }
            if (e.ratio != 100) {
                display.setEnabled(false);
            } else {
                display.setEnabled(true);
            }
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mOffline.remove(e.cityID);
                    updateView();
                }
            });
            display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.putExtra("x", e.geoPt.longitude);
//                    intent.putExtra("y", e.geoPt.latitude);
//                    intent.setClass(OfflineDemo.this, BaseMapDemo.class);
//                    startActivity(intent);
                }
            });
        }

    }
}
