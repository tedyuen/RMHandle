package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.UploadWorkModel;
import cn.com.reachmedia.rmhandle.model.param.UploadWorkParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:23
 * Description: 3.7 提交工作工作项接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadWorkController extends BaseHttpController<UploadWorkModel> {
    
    private UploadWorkParam uploadWorkParam;

    public UploadWorkController(){}

    public UploadWorkController(UiDisplayListener<UploadWorkModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getTaskIndex(UploadWorkParam uploadWorkParam){
        this.uploadWorkParam = uploadWorkParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, uploadWorkParam.toJson());
        LogUtils.d(TAG, uploadWorkParam.toJson());
        App.getAppApiService().uploadWork(uploadWorkParam.toJson(),
                new HttpBaseCallBack<UploadWorkModel>() {
                    @Override
                    public void success(UploadWorkModel data, Response response) {
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
