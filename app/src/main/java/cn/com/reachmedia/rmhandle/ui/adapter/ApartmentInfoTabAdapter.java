package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.ui.TaskInfoAllActivity;
import cn.com.reachmedia.rmhandle.ui.fragment.TaskInfoAllFragment;

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
    private List<TaskDetailModel.CrListBean> mLists;

    private Context mContext;

    private boolean mFlag;

    public ApartmentInfoTabAdapter(Context context, List<TaskDetailModel.CrListBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public ApartmentInfoTabAdapter(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }

    /**
     *
     * @param list
     * @param mFlag 是否隐藏 全部
     */
    public void updateData(List<TaskDetailModel.CrListBean> list,boolean mFlag) {
        this.mLists = list;
        this.mFlag = mFlag;
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
        final ViewHolder1 bean;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apart_info, null);
            bean = new ViewHolder1(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder1) convertView.getTag(R.id.tag);
        }

        final TaskDetailModel.CrListBean data = mLists.get(position);
        if (data != null) {
            bean.tvName.setText(data.getCname());
            bean.tvInfo.setText(data.getComcount() + "个楼盘，" + data.getPointcount() + "个点位");

            if(!mFlag){
                bean.rl_all_info.setVisibility(View.VISIBLE);
                bean.rl_all_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TaskInfoAllFragment.data = data;
                        mContext.startActivity(new Intent(mContext, TaskInfoAllActivity.class));

                    }
                });
            }else{
                bean.rl_all_info.setVisibility(View.GONE);
            }


            bean.llInfoFrame.removeAllViews();
            String tempDistrict = "";
            LinearLayout tempLine = null;
            ViewHolder2 tempBean = null;
            int areaCount = 0;
            for (TaskDetailModel.CrListBean.ComListBean comList : data.getComList()) {
                boolean flag = comList.getDistrict().trim().equals(tempDistrict);
                if (!flag) {
                    tempLine = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.line_item_apart_info_frame, null);
                    tempBean = new ViewHolder2(tempLine);
                    tempLine.setTag(R.id.tag, tempBean);
                } else {
                    tempBean = (ViewHolder2) tempLine.getTag(R.id.tag);
                }

                RelativeLayout rlTemp = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.line_item_apart_info, null);
                ViewHolder3 bean3 = new ViewHolder3(rlTemp);
                bean3.tvApName.setText(comList.getCommunityname());
                bean3.tvApInfo.setText("点位数："+comList.getPointing()+"/"+comList.getPointcount());
                tempBean.tvDistrict.setText(comList.getDistrict());
//                if(!mFlag && tempBean.llInnerFrame.getChildCount()<5){
//                    tempBean.llInnerFrame.addView(rlTemp);
//                }else if(mFlag){
//                    tempBean.llInnerFrame.addView(rlTemp);
//                }
                tempBean.llInnerFrame.addView(rlTemp);
                areaCount++;

                if (!flag) {
                    bean.llInfoFrame.addView(tempLine);
                    tempDistrict = comList.getDistrict().trim();
                }
                if(!mFlag && areaCount>=5){
                    break;
                }
            }
        }


        return convertView;
    }


    static class ViewHolder1 {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.rl_name)
        RelativeLayout rlName;
        @Bind(R.id.ll_info_frame)
        LinearLayout llInfoFrame;
        @Bind(R.id.rl_all_info)
        RelativeLayout rl_all_info;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @Bind(R.id.tv_district)
        TextView tvDistrict;
        @Bind(R.id.ll_inner_frame)
        LinearLayout llInnerFrame;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 {
        @Bind(R.id.tv_ap_name)
        TextView tvApName;
        @Bind(R.id.tv_ap_info)
        TextView tvApInfo;
        @Bind(R.id.rl_item)
        RelativeLayout rlItem;

        ViewHolder3(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
