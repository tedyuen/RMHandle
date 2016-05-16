package cn.com.reachmedia.rmhandle.ui.adapter;

import android.widget.BaseAdapter;

import java.util.List;

import cn.com.reachmedia.rmhandle.bean.PointBean;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/5 上午10:55
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/5          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class ApartmentPointTabBaseAdapter extends BaseAdapter {

    public abstract void updateData(List<PointBean> mLists);
}
