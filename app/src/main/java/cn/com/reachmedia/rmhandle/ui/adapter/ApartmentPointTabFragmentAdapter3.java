package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.ui.PointDetailActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/5 上午11:44
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/5          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPointTabFragmentAdapter3 extends ApartmentPointTabBaseAdapter {

    private List<PointBean> mLists;

    private Context mContext;


    public ApartmentPointTabFragmentAdapter3(Context context, List<PointBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public ApartmentPointTabFragmentAdapter3(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apartment_point_tab_fragment_3, null);
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

            bean.btLogout.setText(data.getErrorDesc());


        }


        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_apartment_name)
        TextView tvApartmentName;
        @Bind(R.id.tv_target)
        TextView tvTarget;
        @Bind(R.id.iv_shang)
        ImageView ivShang;
        @Bind(R.id.iv_xia)
        ImageView ivXia;
        @Bind(R.id.iv_pai)
        ImageView ivPai;
        @Bind(R.id.bt_logout)
        TextView btLogout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
