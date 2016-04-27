package cn.com.reachmedia.rmhandle.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.BaseModel;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.model.param.TaskDetailParam;
import cn.com.reachmedia.rmhandle.model.param.TaskIndexParam;
import cn.com.reachmedia.rmhandle.model.param.TaskMapParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.LoginController;
import cn.com.reachmedia.rmhandle.network.controller.TaskDetailController;
import cn.com.reachmedia.rmhandle.network.controller.TaskIndexController;
import cn.com.reachmedia.rmhandle.network.controller.TaskMapController;

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
        LoginController lc = new LoginController(new UiDisplayListener<BaseModel>() {
            @Override
            public void onSuccessDisplay(BaseModel data) {

            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        LoginParam param = new LoginParam();
        param.username = "18616990040";
        param.password = "111111";
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
        taskDetailParam.endtime = "2016-03-20";
        taskDetailParam.space = "黄浦区";
        taskDetailParam.lon = "黄浦区";
        taskDetailParam.lat = "黄浦区";
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
        taskMapParam.endtime = "2016-03-20";
        taskMapParam.space = "黄浦区";
        taskMapParam.lon = "黄浦区";
        taskMapParam.lat = "黄浦区";
        taskMapController.getTaskMap(taskMapParam);
    }
}
