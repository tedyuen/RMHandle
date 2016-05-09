package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.ui.view.SquareImageView;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 下午1:55
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CustomerPhotoTabAdapter extends BaseAdapter {

    private List<TaskDetailModel.ClListBean> mLists;

    private Context mContext;


    public CustomerPhotoTabAdapter(Context context, List<TaskDetailModel.ClListBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public CustomerPhotoTabAdapter(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }

    public void updateData(List<TaskDetailModel.ClListBean> list) {
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
        final ViewHolder1 bean;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_customer_photo_tab, null);
            bean = new ViewHolder1(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder1) convertView.getTag(R.id.tag);
        }

        TaskDetailModel.ClListBean data = mLists.get(position);
        if (data != null) {
            bean.tvCname.setText(data.getCname());
            bean.tvShowtime.setText(data.getShowtime());
            bean.tvDaohuaTime.setText("到画时间：" + data.getPictime());
            bean.tvShanghuaTime.setText("上画时间：" + data.getWorktime());
            bean.tvYaoqiu.setText(data.getDescs());

            bean.llPhotoFrame.removeAllViews();
            LinearLayout tempLine = null;
            ViewHolder2 tempBean = null;
            final List<String> imgList = new ArrayList<String>();
            for(int i=0;i<data.getPicList().size();i++){
                if(i>=6) break;
                imgList.add(data.getPicList().get(i).getPicurlB());
            }
            for (int i = 0; i < data.getPicList().size(); i++) {
                if(i>=6) break;
                TaskDetailModel.ClListBean.PicListBean bean2 = data.getPicList().get(i);
                int tempMod = i % 3;
                if (tempMod == 0) {
                    tempLine = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.line_item_customer_photo, null);
                    tempBean = new ViewHolder2(tempLine);
                    tempLine.setTag(R.id.tag, tempBean);
                    bean.llPhotoFrame.addView(tempLine);
                } else {
                    tempBean = (ViewHolder2) tempLine.getTag(R.id.tag);
                }
                final int tempIndex = i;
                switch (tempMod){
                    case 0:
                        if (StringUtils.notEmpty(bean2.getPicurlS())) {
                            Picasso.with(this.mContext).load(bean2.getPicurlS()).placeholder(R.drawable.abc).into(tempBean.ivPhoto1);
                            tempBean.ivPhoto1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ViewHelper.getImagePager(mContext, imgList, tempIndex);
                                }
                            });
                        }
                        break;
                    case 1:
                        if (StringUtils.notEmpty(bean2.getPicurlS())) {
                            tempBean.ivPhoto2.setVisibility(View.VISIBLE);
                            Picasso.with(this.mContext).load(bean2.getPicurlS()).placeholder(R.drawable.abc).into(tempBean.ivPhoto2);
                            tempBean.ivPhoto2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ViewHelper.getImagePager(mContext, imgList, tempIndex);
                                }
                            });
                        }
                        break;
                    case 2:
                        if (StringUtils.notEmpty(bean2.getPicurlS())) {
                            tempBean.ivPhoto3.setVisibility(View.VISIBLE);
                            Picasso.with(this.mContext).load(bean2.getPicurlS()).placeholder(R.drawable.abc).into(tempBean.ivPhoto3);
                            tempBean.ivPhoto3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ViewHelper.getImagePager(mContext, imgList, tempIndex);
                                }
                            });
                        }
                        break;
                }

            }

        }

        return convertView;
    }

    static class ViewHolder1 {
        @Bind(R.id.tv_cname)
        TextView tvCname;
        @Bind(R.id.tv_daohua_time)
        TextView tvDaohuaTime;
        @Bind(R.id.tv_shanghua_time)
        TextView tvShanghuaTime;
        @Bind(R.id.tv_yaoqiu)
        TextView tvYaoqiu;
        @Bind(R.id.ll_photo_frame)
        LinearLayout llPhotoFrame;
        @Bind(R.id.tv_showtime)
        TextView tvShowtime;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @Bind(R.id.iv_photo_1)
        SquareImageView ivPhoto1;
        @Bind(R.id.iv_photo_2)
        SquareImageView ivPhoto2;
        @Bind(R.id.iv_photo_3)
        SquareImageView ivPhoto3;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
