package cn.com.reachmedia.rmhandle.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.BaseModel;
import cn.com.reachmedia.rmhandle.model.LoginModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.model.param.TaskDetailParam;
import cn.com.reachmedia.rmhandle.model.param.TaskIndexParam;
import cn.com.reachmedia.rmhandle.model.param.TaskMapParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.LoginController;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.network.controller.TaskDetailController;
import cn.com.reachmedia.rmhandle.network.controller.TaskIndexController;
import cn.com.reachmedia.rmhandle.network.controller.TaskMapController;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:26
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class InterfaceTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void testLogin(){
        LoginController lc = new LoginController(new UiDisplayListener<LoginModel>() {
            @Override
            public void onSuccessDisplay(LoginModel data) {
                if(data!=null){
                    String token = data.getUsertoken();
                    SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_TOKEN,token);
                }
            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        LoginParam param = new LoginParam();
        param.username = "123";
        param.pswd = "a111111";
        lc.onLogin(param);
    }


    @OnClick(R.id.btn_taskindex)
    public void testbtn_taskIndex(){
        TaskIndexController taskIndexController = new TaskIndexController(new UiDisplayListener<TaskIndexModel>() {
            @Override
            public void onSuccessDisplay(TaskIndexModel data) {

            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        TaskIndexParam param = new TaskIndexParam();
        param.startime = "2016-03-10";
        param.endtime = "2016-05-20";
        param.space = "";
        param.lon = "123";
        param.lat = "345";
        param.customer = "";
        taskIndexController.getTaskIndex(param);


    }

    /**
     * 3.3 首页信息总览
     */
    @OnClick(R.id.btn_taskdetail)
    public void testbtn_taskdetail(){
        TaskDetailController taskDetailController = new TaskDetailController(new UiDisplayListener<TaskDetailModel>() {
            @Override
            public void onSuccessDisplay(TaskDetailModel data) {

            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        TaskDetailParam taskDetailParam = new TaskDetailParam();
        taskDetailParam.startime = "2016-03-10";
        taskDetailParam.endtime = "2016-05-20";
        taskDetailParam.space = "";
        taskDetailParam.lon = "1";
        taskDetailParam.lat = "1";
        taskDetailController.getTaskDetail(taskDetailParam);
    }

    @OnClick(R.id.btn_taskmap)
    public void testbtn_taskmap(){
        TaskMapController taskMapController = new TaskMapController(new UiDisplayListener<TaskMapModel>() {
            @Override
            public void onSuccessDisplay(TaskMapModel data) {

            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        TaskMapParam taskMapParam = new TaskMapParam();
        taskMapParam.startime = "2016-03-10";
        taskMapParam.endtime = "2016-05-20";
        taskMapParam.space = "";
        taskMapParam.lon = "1";
        taskMapParam.lat = "1";
        taskMapController.getTaskMap(taskMapParam);
    }

    /**
     * 3.5 进入小区接口
     */
    @OnClick(R.id.btn_point_list)
    public void testbtn_poingList(){
        PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
            @Override
            public void onSuccessDisplay(PointListModel data) {

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



}
