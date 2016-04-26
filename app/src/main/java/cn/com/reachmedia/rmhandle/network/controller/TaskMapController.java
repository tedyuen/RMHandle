package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.param.TaskMapParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午11:14
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskMapController extends BaseHttpController<TaskMapModel> {

    private TaskMapParam taskMapParam;

    public TaskMapController(){}

    public TaskMapController(UiDisplayListener<TaskMapModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getTaskMap(TaskMapParam taskMapParam){
        this.taskMapParam = taskMapParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, taskMapParam.toJson());
        LogUtils.d(TAG, taskMapParam.toJson());
        App.getAppApiService().getTaskMap(taskMapParam.toJson(),
                new HttpBaseCallBack<TaskMapModel>() {
                    @Override
                    public void success(TaskMapModel data, Response response) {
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
