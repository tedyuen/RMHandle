package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午9:59
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointListController extends BaseHttpController<PointListModel> {
    
    private PointListParam pointListParam;

    public PointListController(){}

    public PointListController(UiDisplayListener<PointListModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getTaskIndex(PointListParam pointListParam){
        this.pointListParam = pointListParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, pointListParam.toJson());
        LogUtils.d(TAG, pointListParam.toJson());
        App.getAppApiService().getPointList(pointListParam.toJson(),
                new HttpBaseCallBack<PointListModel>() {
                    @Override
                    public void success(PointListModel data, Response response) {
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
