package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.model.param.TaskIndexParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午6:25
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskIndexController extends BaseHttpController<TaskIndexModel> {
    
    private TaskIndexParam taskIndexParam;

    public TaskIndexController(){}

    public TaskIndexController(UiDisplayListener<TaskIndexModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getTaskIndex(TaskIndexParam taskIndexParam){
        this.taskIndexParam = taskIndexParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, taskIndexParam.toJson());
        LogUtils.d(TAG, taskIndexParam.toJson());
        App.getAppApiService().getTaskIndex(taskIndexParam.toJson(),
                new HttpBaseCallBack<TaskIndexModel>() {
                    @Override
                    public void success(TaskIndexModel data, Response response) {
                        super.success(data, response);
                        if (uiDisplayListener != null) {
                            uiDisplayListener.onSuccessDisplay(data);
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        super.failure(retrofitError);
                        if (uiDisplayListener != null) {
                            uiDisplayListener.onFailDisplay("");
                        }
                    }
                });

    }
}
