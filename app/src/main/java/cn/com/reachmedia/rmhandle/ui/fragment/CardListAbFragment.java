package cn.com.reachmedia.rmhandle.ui.fragment;

import cn.com.reachmedia.rmhandle.model.CardListModel;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午10:46
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class CardListAbFragment extends BaseFragment {


    public abstract void onDataRefresh(CardListModel data);

}
