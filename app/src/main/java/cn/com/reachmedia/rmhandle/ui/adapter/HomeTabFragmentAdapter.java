package cn.com.reachmedia.rmhandle.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppParamContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.ui.ApartmentPointActivity;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.GoMapAppDialogFragment;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;
import cn.com.reachmedia.rmhandle.utils.IntentUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/19 下午1:32
 * Description: 首页adapter
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/19          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class HomeTabFragmentAdapter extends BaseAdapter {

    private List<TaskIndexModel.PListBean> mLists;

    private HomeActivity mContext;


    public HomeTabFragmentAdapter(HomeActivity context, List<TaskIndexModel.PListBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public HomeTabFragmentAdapter(HomeActivity context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }

    public void updateData(List<TaskIndexModel.PListBean> list){
        this.mLists = list;
    }


    @Override
    public int getCount() {
//        return mLists != null ? mLists.size() : 0;
        return mLists.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_tab_fragment, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }


        final TaskIndexModel.PListBean data = mLists.get(position);
        if(data!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ApartmentPointActivity.class);
                    intent.putExtra(AppParamContact.PARAM_KEY_TITLE,data.getCommunity());
                    intent.putExtra(AppParamContact.PARAM_KEY_ID,data.getCommunityid());
                    SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance();
                    helper.putString(AppSpContact.SP_KEY_INDEX_COMMUNITID,data.getCommunityid());
                    helper.putString(AppSpContact.SP_KEY_INDEX_COMMUNITNAME,data.getCommunity());
                    helper.putString(AppSpContact.SP_KEY_INDEX_STARTTIME, HomeFilterUtil.getIns().startTime);
                    helper.putString(AppSpContact.SP_KEY_INDEX_ENDTIME, HomeFilterUtil.getIns().endTime);
                    mContext.startActivity(intent);
                }
            });

            bean.llAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(IntentUtils.isAvilible(mContext, "com.baidu.BaiduMap") || IntentUtils.isAvilible(mContext,"com.autonavi.minimap")) {
                        SharedPreferencesHelper mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();
                        GoMapAppDialogFragment dialog1 = new GoMapAppDialogFragment(mContext,
                                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE),
                                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE),
                                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE),
                                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE),
//                                "", "",
                                data.getLon(), data.getLat(),
                                data.getLon(), data.getLat(),
//                                "", "",
                                data.getAddress());
                        dialog1.show(mContext.getSupportFragmentManager(),null);
//                        ToastHelper.showInfo(mContext, "安装百度地图");

                    }else {
                        ToastHelper.showAlert(mContext, "手机没有安装百度或高德地图");
                    }
                }
            });

            bean.tvApartmentName.setText(data.getCommunity());
            bean.tvKanCount.setText(data.getLocA()+"/"+data.getLocS());
            bean.tvDate.setText(data.getWorktime());
            bean.tvAddress.setText(data.getAddress()+"·"+data.getDistance());

            StringBuffer targetBuffer = new StringBuffer();
            for(int i=0;i<data.getCList().size();i++){
                TaskIndexModel.PListBean.CListBean cBean = data.getCList().get(i);
                if(i!=0){
                    targetBuffer.append("/");
                }
                targetBuffer.append(cBean.getCname());
            }
            bean.tvTarget.setText(targetBuffer.toString());


            bean.tvTips.setVisibility(View.GONE);
            bean.ivArrowIconUp.setVisibility(View.GONE);
            bean.ivArrowIconDown.setVisibility(View.VISIBLE);

            if(StringUtils.isEmpty(data.getTips())){
                bean.llWarning.setVisibility(View.GONE);
                bean.llWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }else{
                bean.llWarning.setVisibility(View.VISIBLE);
                bean.tvTips.setText(data.getTips());
                bean.llWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bean.tvTips.getVisibility()==View.GONE){
                            bean.ivArrowIconUp.setVisibility(View.VISIBLE);
                            bean.ivArrowIconDown.setVisibility(View.GONE);
                            bean.tvTips.setVisibility(View.VISIBLE);
                        }else if(bean.tvTips.getVisibility()==View.VISIBLE){
                            bean.tvTips.setVisibility(View.GONE);
                            bean.ivArrowIconUp.setVisibility(View.GONE);
                            bean.ivArrowIconDown.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

            if(data.getIsCard()==0){
                bean.ivCard.setVisibility(View.GONE);
            }else{
                bean.ivCard.setVisibility(View.VISIBLE);
            }
            if(data.getIsPswd()==0){
                bean.ivPassword.setVisibility(View.GONE);
            }else{
                bean.ivPassword.setVisibility(View.VISIBLE);
            }
            if(data.getWorkUp()==0){
                bean.ivShang.setVisibility(View.GONE);
            }else{
                bean.ivShang.setVisibility(View.VISIBLE);
            }

            if(data.getWorkCheck()==0){
                bean.ivXun.setVisibility(View.GONE);
            }else{
                bean.ivXun.setVisibility(View.VISIBLE);
            }

            if(data.getWorkDown()==0){
                bean.ivXia.setVisibility(View.GONE);
            }else{
                bean.ivXia.setVisibility(View.VISIBLE);
            }
//            if(data.getWorkUpPhone()==1 || data.getWorkDownPhone()==1){
//                bean.ivPai.setVisibility(View.VISIBLE);
//            }else{
//                bean.ivPai.setVisibility(View.GONE);
//            }
            bean.ivPai.setVisibility(View.GONE);


        }



        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_apartment_name)
        TextView tvApartmentName;
        @Bind(R.id.iv_card)
        ImageView ivCard;
        @Bind(R.id.iv_password)
        ImageView ivPassword;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_kan_count)
        TextView tvKanCount;
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
        @Bind(R.id.iv_arrow_icon_up)
        ImageView ivArrowIconUp;
        @Bind(R.id.iv_arrow_icon_down)
        ImageView ivArrowIconDown;

        @Bind(R.id.ll_warning)
        LinearLayout llWarning;
        @Bind(R.id.tv_tips)
        TextView tvTips;
        @Bind(R.id.tv_address)
        TextView tvAddress;
        @Bind(R.id.ll_address)
        LinearLayout llAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
