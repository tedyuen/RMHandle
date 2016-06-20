package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
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
import cn.com.reachmedia.rmhandle.model.CardListModel;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午11:14
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PswdListAdapter extends BaseAdapter {

    List<CardListModel.PswdListBean> mLists;
    private Context mContext;


    public PswdListAdapter(Context context, List<CardListModel.PswdListBean> mLists) {
        this.mLists = mLists;
        this.mContext = context;
    }

    public PswdListAdapter(Context context) {
        this.mContext = context;
        this.mLists = new ArrayList<>();
    }

    public void updateData(List<CardListModel.PswdListBean> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card_list_tab, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }

        CardListModel.PswdListBean data = mLists.get(position);
        if(data!=null){
            bean.tvTitle.setText(data.getName()+"于"+data.getUTime()+"修改密码备注");
            bean.tvContent.setText(data.getContent());
        }

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
