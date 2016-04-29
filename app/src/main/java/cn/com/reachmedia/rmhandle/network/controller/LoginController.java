package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.LoginModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:39
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LoginController extends BaseHttpController<LoginModel> {

    private LoginParam loginParam;

    public LoginController(){}

    public LoginController(UiDisplayListener<LoginModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void onLogin(LoginParam loginParam){
        this.loginParam = loginParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, loginParam.toJson());
        LogUtils.d(TAG, loginParam.toJson());
        App.getAppApiService().onLogin(loginParam.toJson(),
                new HttpBaseCallBack<LoginModel>() {
                    @Override
                    public void success(LoginModel data, Response response) {
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
