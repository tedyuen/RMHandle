package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.ui.SynchronizeActivity;
import cn.com.reachmedia.rmhandle.ui.adapter.SynchronizeTabAdapter;
import cn.com.reachmedia.rmhandle.ui.bean.SynchronzieBean;
import cn.com.reachmedia.rmhandle.ui.view.PageListView;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/13 上午11:16
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/13          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class SynchronizeTabFragment2 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String LIST_TYPE = "LIST_TYPE";

    SynchronizeActivity activity;

    @Bind(R.id.swipe_container)
    protected SwipeRefreshLayout mSwipeContainer;
    @Bind(R.id.page_list_view)
    protected PageListView mPageListView;

    private SynchronizeTabAdapter mAdapter;
    private int type=1;//0:未同步 1:已同步

    List<SynchronzieBean> mLists;

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    List<SynchronzieBean> dataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_customer_photo_list, container, false);
        ButterKnife.bind(this, rootView);
        activity = (SynchronizeActivity)getActivity();
        //设置下拉刷新小球的颜色
        mSwipeContainer.setOnRefreshListener(this);
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置下拉刷新小球的颜色

        initData();

        setUpViewComponent();

        return rootView;
    }


    private void initData(){
        mLists = new ArrayList<>();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        List<PointWorkBean> list = pointWorkBeanDbUtil.getSynchronize(type);
        dataList = new ArrayList<>();

        String tempComName = "";
        StringBuffer tempCNameBuffer=null;
        SynchronzieBean tempBean = null;
        for(PointWorkBean bean:list){
            if(!tempComName.equals(bean.getCommunityname())){
                if(tempBean!=null){
                    dataList.add(tempBean);
                }
                tempBean = new SynchronzieBean();
                tempComName = bean.getCommunityname();
                tempCNameBuffer = new StringBuffer();
                if(!StringUtils.isEmpty(bean.getCname())){
                    tempCNameBuffer.append(bean.getCname());
                }
                tempBean.setPhotoCount(bean.getFileCount());
                tempBean.setPointCount(1);
                tempBean.setCname(tempCNameBuffer.toString());
                tempBean.setCommunityName(tempComName);
                tempBean.setPhotoTime(TimeUtils.dateAddByDateForString(bean.getWorkTime(),"MM-dd HH:mm",0));
                tempBean.setPointTime(TimeUtils.dateAddByDateForString(bean.getWorkTime(),"MM-dd HH:mm",0));
            }else{
                if(!tempBean.getCname().contains(bean.getCname())){
                    if(!StringUtils.isEmpty(bean.getCname())){
                        tempCNameBuffer.append("/"+bean.getCname());
                        tempBean.setCname(tempCNameBuffer.toString());
                    }
                }
                tempBean.setPhotoCount(tempBean.getPhotoCount()+bean.getFileCount());
                tempBean.setPointCount(tempBean.getPointCount()+1);
            }
        }
        //最后一组
        if(tempBean!=null){
            dataList.add(tempBean);
        }



    }

    private void setUpViewComponent() {
        mAdapter = new SynchronizeTabAdapter(getActivity(),dataList,type);
        mPageListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
