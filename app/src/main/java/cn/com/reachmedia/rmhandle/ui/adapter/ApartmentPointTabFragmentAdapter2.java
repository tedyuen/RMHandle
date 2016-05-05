package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.PointDetailActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/5 上午10:55
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/5          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointTabFragmentAdapter2 extends ApartmentPointTabBaseAdapter {

    private List<String> mLists;

    private Context mContext;


    public ApartmentPointTabFragmentAdapter2(Context context, List<String> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public ApartmentPointTabFragmentAdapter2(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return 20;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_point_tab_fragment_2, null);
//            bean = new ViewHolder(convertView);
//            convertView.setTag(R.id.tag, bean);
        } else {
//            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PointDetailActivity.class));
            }
        });


        return convertView;
    }
}
