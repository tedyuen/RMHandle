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
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.helper.PointWorkBeanDaoHelper;
import cn.com.reachmedia.rmhandle.ui.NewPointDetailActivity;
import cn.com.reachmedia.rmhandle.ui.PointDetailActivity;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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

    private int listType;

    private PointWorkBeanDaoHelper pointWorkBeanDaoHelper;


    public ApartmentPointTabFragmentAdapter3(Context context, List<PointBean> mLists,int listType) {
        this.mLists = mLists;
        this.mContext = context;
        this.listType = listType;
        this.pointWorkBeanDaoHelper = PointWorkBeanDaoHelper.getInstance();
    }

    public ApartmentPointTabFragmentAdapter3(Context context,int listType) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
        this.listType = listType;
        this.pointWorkBeanDaoHelper = PointWorkBeanDaoHelper.getInstance();
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
                    ApartmentPointUtils.getIns().pointId = data.getPointId();
                    ApartmentPointUtils.getIns().workId = data.getWorkId();
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_WORK_ID,data.getWorkId());
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_POINT_ID,data.getPointId());
                    mContext.startActivity(new Intent(mContext, NewPointDetailActivity.class));
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
                if(data.getWorkDown()==0){
                    bean.ivXia.setVisibility(View.GONE);
                    bean.ivXun.setVisibility(View.VISIBLE);
                }else{
                    bean.ivXun.setVisibility(View.GONE);
                    bean.ivXia.setVisibility(View.VISIBLE);
                }
            }else{
                bean.ivXun.setVisibility(View.GONE);
                bean.ivXia.setVisibility(View.GONE);
                bean.ivShang.setVisibility(View.VISIBLE);
            }
            if(data.getWorkUpPhone()==1 || data.getWorkDownPhone()==1){
                bean.ivPai.setVisibility(View.VISIBLE);
            }else{
                bean.ivPai.setVisibility(View.GONE);
            }

//            switch (data.)
            if(!StringUtils.isEmpty(data.getStateTypeDesc())){
                bean.btLogout.setText(data.getStateTypeDesc());

            }else{
                PointWorkBean workBean = pointWorkBeanDaoHelper.getDataByWPID(data.getWorkId(),data.getPointId(),2,"2");
                if(workBean!=null){
                    bean.btLogout.setText("保修");
                }else{
                    workBean = pointWorkBeanDaoHelper.getDataByWPID(data.getWorkId(),data.getPointId(),3,"2");
                    if(workBean!=null){
                        switch (workBean.getErrorType()){
                            case 0:
                                bean.btLogout.setText("门卡无法打开");
                                break;
                            case 1:
                                bean.btLogout.setText("拿不到开门密码");
                                break;
                            case 9:
                                bean.btLogout.setText("其他");
                                break;
                        }
                    }
                }
            }



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
        @Bind(R.id.iv_xun)
        ImageView ivXun;
        @Bind(R.id.iv_pai)
        ImageView ivPai;
        @Bind(R.id.bt_logout)
        TextView btLogout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
