package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.ui.UnUploadPointListActivity;
import cn.com.reachmedia.rmhandle.ui.bean.SynchronzieBean;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/13 上午11:33
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/13          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class SynchronizeTabAdapter extends BaseAdapter {

    private List<SynchronzieBean> mLists;

    private Context mContext;

    private int type;//0：未同步  1:已同步

    public SynchronizeTabAdapter(Context context, List<SynchronzieBean> mLists,int type) {
        this.mLists = mLists;
        this.mContext = context;
        this.type = type;
    }

    public SynchronizeTabAdapter(Context context,int type) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
        this.type = type;
    }

    public void updateData(List<SynchronzieBean> list) {
        this.mLists = list;
    }


    @Override
    public int getCount() {
        return mLists != null ? mLists.size() : 0;
//        return 10;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_synchronize_tab, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }
        final SynchronzieBean data = mLists.get(position);
        if(data!=null){
            bean.tvCommunityName.setText(data.getCommunityName());
            bean.tvCustomer.setText(data.getCname());
            bean.tvPointTime.setText(data.getPointTime());
            bean.tvPhotoTime.setText(data.getPhotoTime());
            switch (type){
                case 0:
                    bean.tvPointCount.setText("未上传点位："+data.getPointCount());
                    bean.tvPhotoCount.setText("未上传图片："+data.getPhotoCount());
                    break;
                case 1:
                    bean.tvPointCount.setText("已上传点位："+data.getPointCount());
                    bean.tvPhotoCount.setText("已上传图片："+data.getPhotoCount());
                    break;
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_UNUP_COMID,data.getCommunityId());
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_UNUP_COMNAME,data.getCommunityName());
                    mContext.startActivity(new Intent(mContext, UnUploadPointListActivity.class));
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_community_name)
        TextView tvCommunityName;
        @Bind(R.id.tv_customer)
        TextView tvCustomer;
        @Bind(R.id.tv_point_time)
        TextView tvPointTime;
        @Bind(R.id.tv_point_count)
        TextView tvPointCount;
        @Bind(R.id.tv_photo_time)
        TextView tvPhotoTime;
        @Bind(R.id.tv_photo_count)
        TextView tvPhotoCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
