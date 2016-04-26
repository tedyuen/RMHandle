package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.param.TaskDetailParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/26 上午10:46
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TaskDetailController extends BaseHttpController<TaskDetailModel> {
    
    private TaskDetailParam taskDetailParam;

    public TaskDetailController(){}

    public TaskDetailController(UiDisplayListener<TaskDetailModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getTaskDetail(TaskDetailParam taskDetailParam){
        this.taskDetailParam = taskDetailParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, taskDetailParam.toJson());
        LogUtils.d(TAG, taskDetailParam.toJson());
        App.getAppApiService().getTaskDetail(taskDetailParam.toJson(),
                new HttpBaseCallBack<TaskDetailModel>() {
                    @Override
                    public void success(TaskDetailModel data, Response response) {
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
