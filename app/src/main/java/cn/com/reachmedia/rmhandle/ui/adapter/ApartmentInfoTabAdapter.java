package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 下午2:02
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentInfoTabAdapter extends BaseAdapter {
    private List<String> mLists;

    private Context mContext;


    public ApartmentInfoTabAdapter(Context context, List<String> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public ApartmentInfoTabAdapter(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }




    @Override
    public int getCount() {
//        return mLists != null ? mLists.size() : 0;
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder bean;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_point_tab_fragment, null);
//            bean = new ViewHolder(convertView);
//            convertView.setTag(R.id.tag, bean);
        } else {
//            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }



        return convertView;
    }
}
