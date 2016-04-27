package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/27 下午4:24
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class DialogApartmentPhoneAdapter extends BaseAdapter {

    private Context mContext;

    public DialogApartmentPhoneAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_phone_dialog, null);
//            bean = new ViewHolder(convertView);
//            convertView.setTag(R.id.tag, bean);
        } else {
//            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }



        return convertView;
    }
}
