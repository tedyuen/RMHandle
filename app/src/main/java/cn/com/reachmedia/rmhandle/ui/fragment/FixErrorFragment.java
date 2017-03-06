package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.model.param.TaskIndexParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.network.controller.TaskIndexController;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.bean.ImageCacheResBean;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 06/03/2017.
 */

public class FixErrorFragment extends BaseToolbarFragment {

    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    List<PointWorkBean> dataList;
    List<PointWorkBean> needFixDataList;
    Map<String,List<PointWorkBean>> needFixDataMap;

    TaskIndexModel taskIndexModel;
    List<TaskIndexModel.PListBean> pList;

    @Bind(R.id.tv_log)
    TextView tv_log;

    Handler handler;

    String fixTime = "2017-03-06";

    public static FixErrorFragment newInstance() {
        FixErrorFragment fragment = new FixErrorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FixErrorFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fix_error_fragment, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        needFixDataList = new ArrayList<>();
        needFixDataMap = new HashMap<>();

        handler = new Handler(){
            public void handleMessage(Message msg) {
                tv_log.setText(msg.obj+"\n"+tv_log.getText());
                super.handleMessage(msg);
            }
        };
        tv_log.setText("开始修复...\n");

        getTaskIndex();
        return rootView;
    }

    // 获取点位数据
    private void getPointList(){
        if(pList!=null && pList.size()>0){
            TaskIndexModel.PListBean bean = pList.get(0);
            if(needFixDataMap.containsKey(bean.getCommunityid())){//有这个小区
                StringBuffer topSb = new StringBuffer();
                topSb.append("====================\n");
                topSb.append("扫描小区:"+bean.getCommunity()+"\n");
                tv_log.append(topSb.toString());
                final List<PointWorkBean> errorList = needFixDataMap.get(bean.getCommunityid());
                PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
                    @Override
                    public void onSuccessDisplay(PointListModel data) {
                        if (data != null) {
                            if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                try{
                                    for(PointWorkBean temp1:errorList){
                                        for(PointListModel.NewListBean temp2:data.getNewList()){
                                            if(temp1.getPointId().equals(temp2.getPointId()) &&
                                                    !temp1.getWorkId().equals(temp2.getWorkId()) &&
                                                    temp2.getWorkUp()==1){
                                                StringBuffer sb1 = new StringBuffer();
                                                sb1.append("\t----------------------------\n");
                                                sb1.append("\t发现一个需要修复的点位:"+temp1.getPointId()+"\n");
                                                PointWorkBean checked = pointWorkBeanDbUtil.getPointWorkBeanByWPIDAll(temp2.getWorkId(),temp2.getPointId());
                                                if(checked==null){
                                                    try{
                                                        int tempName = Integer.parseInt(temp2.getDoor());
                                                        sb1.append("\t"+tempName+"号楼 "+(temp2.getGround()==0?"地下":"地上")+"\n");
                                                    }catch (Exception e){
                                                        sb1.append("\t"+temp2.getDoor()+" "+(temp2.getGround()==0?"地下":"地上")+"\n");
                                                    }
                                                    // 这里要入库
                                                    temp1.setWorkId(temp2.getWorkId());
                                                    temp1.setNativeState("0");
                                                    sb1.append("\t修复成功!\n");
                                                }else{
                                                    temp1.setNativeState("2");
                                                    sb1.append("\t已经重复完成!\n");
                                                }
                                                pointWorkBeanDbUtil.updateOneData(temp1);
                                                tv_log.append(sb1.toString());
                                            }
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    if(pList!=null && pList.size()>0){
                                        pList.remove(0);
                                    }
                                    getPointList();
                                }

                            }
                        }
                        if(pList!=null && pList.size()>0){
                            pList.remove(0);
                        }
                        getPointList();
                    }

                    @Override
                    public void onFailDisplay(String errorMsg) {
                        if(pList!=null && pList.size()>0){
                            pList.remove(0);
                        }
                        getPointList();
                    }
                });
                PointListParam param = new PointListParam();
                param.communityid = bean.getCommunityid();
                param.startime = fixTime;
                param.endtime = "2017-03-12";
                param.space = "";
                param.customer = "";
                pointListController.getTaskIndex(param);
            }else{
                if(pList!=null && pList.size()>0){
                    pList.remove(0);
                }
                getPointList();
            }
        }else{
            tv_log.append("修复完毕!");
        }
    }

    // 获取本地出错数据
    private void getErrorData(){
        String userId = SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_USER_ID);
        if(userId!=null){
            dataList = pointWorkBeanDbUtil.getUploadUn(userId,"2");
            for(PointWorkBean bean:dataList){
//                System.out.println("小区id: "+bean.getCommunityid());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String str=sdf.format(bean.getWorkTime());
//                System.out.println("时间: "+str);
                if(fixTime.equals(str)){
//                    System.out.println("需要修复的点位:"+bean.getCommunityid()+":"+bean.getWorkId()+":"+bean.getPointId());
                    if(needFixDataMap.containsKey(bean.getCommunityid())){
                        needFixDataMap.get(bean.getCommunityid()).add(bean);
                    }else{
                        List<PointWorkBean> tempList = new ArrayList<>();
                        tempList.add(bean);
                        needFixDataMap.put(bean.getCommunityid(),tempList);
                    }
                    needFixDataList.add(bean);
                }
            }
            getPointList();
        }
    }

    // 获取小区数据
    private void getTaskIndex(){
        TaskIndexParam param = new TaskIndexParam();
        TaskIndexController taskIndexController = new TaskIndexController(new UiDisplayListener<TaskIndexModel>() {
            @Override
            public void onSuccessDisplay(TaskIndexModel data) {
                if (data != null) {
                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                        taskIndexModel = data;
                        pList = data.getPList();
                        getErrorData();
                    }
                }
            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        param.state = AppSpContact.SP_KEY_UNDONE;
        param.startime = fixTime;
        param.endtime = "2017-03-12";
        param.space = "";
        param.lon = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE);
        param.lat = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE);
        param.customer = "";
        taskIndexController.getTaskIndex(param);
//        System.out.println("state:"+AppSpContact.SP_KEY_UNDONE);
//        System.out.println("starttime:"+param.startime);
//        System.out.println("endtime:"+param.endtime);
//        System.out.println("space:"+param.space);
//        System.out.println("customer:"+param.customer);
    }



    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
