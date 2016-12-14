package cn.com.reachmedia.rmhandle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;

/**
 * Created by tedyuen on 16-12-14.
 */
public class UnUploadPointListAdapter extends BaseAdapter {

    private List<PointWorkBean> mLists;
    private Context mContext;

    public UnUploadPointListAdapter(Context context, List<PointWorkBean> list) {
        this.mContext = context;
        this.mLists = list;
    }

    @Override
    public int getCount() {
        return mLists != null ? mLists.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder bean;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_un_upload_point, null);
            bean = new ViewHolder(convertView);
            convertView.setTag(R.id.tag, bean);
        } else {
            bean = (ViewHolder) convertView.getTag(R.id.tag);
        }

        PointWorkBean data = mLists.get(position);

        bean.tvInfo.setText("pointid:"+data.getPointId());

        bean.tvDetail.setText(data.getNativeState().equals("0")?"信息未传*"+data.getFileCount():"图片未传*"+data.getFileCount());
//        try{
//            int tempName = Integer.parseInt(data.getDoor());
//            bean.tvApartmentName.setText(tempName+"号楼 "+(data.getGround()==0?"地下":"地上"));
//        }catch (Exception e){
//            bean.tvApartmentName.setText(data.getDoor()+" "+(data.getGround()==0?"地下":"地上"));
//        }

        return convertView;
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    static class ViewHolder {
        @Bind(R.id.tv_info)
        TextView tvInfo;
        @Bind(R.id.tv_detail)
        TextView tvDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
