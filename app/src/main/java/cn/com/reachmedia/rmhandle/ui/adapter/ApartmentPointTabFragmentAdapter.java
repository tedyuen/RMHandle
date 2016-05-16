package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.ui.PointDetailActivity;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/19 下午1:32
 * Description: 小区点位adapter
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/19          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointTabFragmentAdapter extends ApartmentPointTabBaseAdapter {

    private List<PointBean> mLists;

    private Context mContext;


    public ApartmentPointTabFragmentAdapter(Context context, List<PointBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public ApartmentPointTabFragmentAdapter(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }

    @Override
    public void updateData(List<PointBean> mLists) {
        this.mLists = mLists;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_point_tab_fragment, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //empty
            }
        });

        final PointBean data = mLists.get(position);
        if(data!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApartmentPointUtils.getIns().pointId = data.getPointId();
                    ApartmentPointUtils.getIns().workId = data.getWorkId();
                    mContext.startActivity(new Intent(mContext, PointDetailActivity.class));
                }
            });

            try{
                int tempName = Integer.parseInt(data.getDoor());
                bean.tvApartmentName.setText(tempName+"号楼 "+(data.getGround()==0?"地下":"地上"));
            }catch (Exception e){
                bean.tvApartmentName.setText(data.getDoor()+" "+(data.getGround()==0?"地下":"地上"));
            }
            bean.tvTarget.setText(data.getCname());

            if(data.getWorkUp()==0){
                bean.ivShang.setVisibility(View.GONE);
            }else{
                bean.ivShang.setVisibility(View.VISIBLE);
            }
            if(data.getWorkDown()==0){
                bean.ivXia.setVisibility(View.GONE);
            }else{
                bean.ivXia.setVisibility(View.VISIBLE);
            }
            if(data.getWorkUpPhone()==1 || data.getWorkDownPhone()==1){
                bean.ivPai.setVisibility(View.VISIBLE);
            }else{
                bean.ivPai.setVisibility(View.GONE);
            }


        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_apartment_name)
        TextView tvApartmentName;
        @Bind(R.id.iv_shang)
        ImageView ivShang;
        @Bind(R.id.iv_xia)
        ImageView ivXia;
        @Bind(R.id.iv_pai)
        ImageView ivPai;
        @Bind(R.id.tv_target)
        TextView tvTarget;
        @Bind(R.id.ll_item_frame)
        LinearLayout llItemFrame;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
