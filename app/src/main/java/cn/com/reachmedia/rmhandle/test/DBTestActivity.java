package cn.com.reachmedia.rmhandle.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.db.helper.PointBeanDaoHelper;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/11 上午10:13
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/11          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class DBTestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_query)
    public void queryData(){
        PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
            @Override
            public void onSuccessDisplay(PointListModel data) {
                if (data != null) {
                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                        List<PointListModel.NewListBean> newList = data.getNewList();
                        PointBeanDaoHelper pointBeanDaoHelper = PointBeanDaoHelper.getInstance();
                        pointBeanDaoHelper.deleteAll();
                        for(PointListModel.NewListBean tempBean:newList){
                            PointBean bean = tempBean.toBean("abc","","","");
                            pointBeanDaoHelper.addData(bean);
                        }

                    }
                }
            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        PointListParam param = new PointListParam();
        param.communityid = "663";
        param.startime = "2016-05-05";
        param.endtime = "2016-05-11";
        param.space = "";
        param.customer = "";
        pointListController.getTaskIndex(param);

    }

    @OnClick(R.id.bt_select)
    public void selectData(){
        PointBeanDaoHelper pointBeanDaoHelper = PointBeanDaoHelper.getInstance();
        List<PointBean> list = pointBeanDaoHelper.getAllData();
        for(PointBean bean:list){
            System.out.println(bean.toString());
        }

    }
}
