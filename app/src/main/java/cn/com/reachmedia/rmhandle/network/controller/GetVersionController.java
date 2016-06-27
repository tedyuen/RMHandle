package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.GetVersionModel;
import cn.com.reachmedia.rmhandle.model.param.GetVersionParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/27 下午2:07
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class GetVersionController extends BaseHttpController<GetVersionModel> {

    private GetVersionParam getVersionParam;

    public GetVersionController(){}

    public GetVersionController(UiDisplayListener<GetVersionModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getVersion(GetVersionParam getVersionParam){
        this.getVersionParam = getVersionParam;
        loadData();
    }

//    public void checkUpdate() {
//        loadData();
//    }

    @Override
    protected void getNetData() {
//        this.getVersionParam = new GetVersionParam();
        LogUtils.json(TAG, getVersionParam.toJson());
        App.getAppApiService().getVersion(getVersionParam.toJson(),
                new HttpBaseCallBack<GetVersionModel>() {
                    @Override
                    public void success(GetVersionModel data, Response response) {
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
