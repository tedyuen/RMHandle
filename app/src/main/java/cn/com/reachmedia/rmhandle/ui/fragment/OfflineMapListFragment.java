package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOfflineMap;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/21 上午11:04
 * Description: 离线地图 列表
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/21          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class OfflineMapListFragment extends BaseFragment {
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    @Bind(R.id.listview)
    ListView hotCityList;

    private MKOfflineMap mOffline;
    private OfflineMapActivity activity;

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
        // 获取热闹城市列表
        final ArrayList<MKOLSearchRecord> records1 = mOffline.getHotCityList();
        ArrayList<String> hotCities = new ArrayList<>();
//        hotCities.add("全国基础包");
        if (records1 != null) {
            for (MKOLSearchRecord r : records1) {
                hotCities.add(r.cityName + "       --"
                        + activity.formatDataSize(r.size));
            }
        }
        ListAdapter hAdapter =  new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, hotCities);
        hotCityList.setAdapter(hAdapter);
        hotCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int temp = position-1;
//                if(temp==-1){
//                    activity.startDownloadMap(1);
//                }else{
                    MKOLSearchRecord record = records1.get(position);
                    if(record!=null){
                        activity.startDownloadMap(record.cityID);
                    }
//                }

            }
        });

    }




    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
