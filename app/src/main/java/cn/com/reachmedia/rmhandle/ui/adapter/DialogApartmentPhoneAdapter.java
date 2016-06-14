package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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

    private String[] data;

    public DialogApartmentPhoneAdapter(Context context, String phoneName) {
        this.mContext = context;
        if (!StringUtils.isEmpty(phoneName)) {
            data = phoneName.split("_");
        } else {
            data = new String[0];
        }


    }

    @Override
    public int getCount() {
        return data.length==0?1:data.length;
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
        final ViewHolder bean;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_phone_dialog, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }

        if(data.length==0){
            bean.tvName.setText("没有联系人");
            bean.btCall.setVisibility(View.INVISIBLE);
        }else{
            String phoneName = data[position];
            if(!StringUtils.isEmpty(phoneName)){
                final String[] result = phoneName.split("@");
                if(result.length>0){
                    bean.tvName.setText(result[0]);
                    if(!StringUtils.isEmpty(result[1])) {
                        bean.btCall.setVisibility(View.VISIBLE);
                        bean.btCall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!StringUtils.isEmpty(result[1])) {
                                    Intent phoneIntent = new Intent(
                                            "android.intent.action.CALL",
                                            Uri.parse("tel:"
                                                    + result[1]));
                                    mContext.startActivity(phoneIntent);
                                }

                            }
                        });
                    }else{
                        bean.btCall.setVisibility(View.INVISIBLE);
                    }
                }else{
                    bean.btCall.setVisibility(View.INVISIBLE);
                }


            }
        }

        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.bt_call)
        Button btCall;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
