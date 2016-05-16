package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.ui.ApartmentPointActivity;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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

    private Context mContext;


    public HomeTabFragmentAdapter(Context context, List<TaskIndexModel.PListBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public HomeTabFragmentAdapter(Context context) {
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
                    mContext.startActivity(intent);
                }
            });
            bean.tvApartmentName.setText(data.getCommunity());
            bean.tvKanCount.setText(data.getLocA()+"/"+data.getLocS());
            bean.tvDate.setText(data.getWorktime());
            bean.tvAddress.setText(data.getAddress()+"·"+data.getDistance());

            if(StringUtils.isEmpty(data.getTips())){
                bean.llWarning.setVisibility(View.GONE);
                bean.tvTips.setVisibility(View.GONE);
                bean.llWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }else{
                bean.llWarning.setVisibility(View.VISIBLE);
                bean.tvTips.setVisibility(View.VISIBLE);
                bean.llWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bean.tvTips.getVisibility()==View.GONE){
                            bean.tvTips.setVisibility(View.VISIBLE);
                        }else if(bean.tvTips.getVisibility()==View.VISIBLE){
                            bean.tvTips.setVisibility(View.GONE);
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
        @Bind(R.id.iv_pai)
        ImageView ivPai;
        @Bind(R.id.iv_arrow_icon)
        ImageView ivArrowIcon;
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
