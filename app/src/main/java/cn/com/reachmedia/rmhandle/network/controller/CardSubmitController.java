package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.CardSubmitModel;
import cn.com.reachmedia.rmhandle.model.param.CardSubmitParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/26 上午11:21
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/26          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardSubmitController extends BaseHttpController<CardSubmitModel> {

    private CardSubmitParam cardSubmitParam;

    public CardSubmitController(){}

    public CardSubmitController(UiDisplayListener<CardSubmitModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void cardSubmit(CardSubmitParam cardSubmitParam){
        this.cardSubmitParam = cardSubmitParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, cardSubmitParam.toJson());
        LogUtils.d(TAG, cardSubmitParam.toJson());
        App.getAppApiService().cardSubmit(cardSubmitParam.toJson(),
                new HttpBaseCallBack<CardSubmitModel>() {
                    @Override
                    public void success(CardSubmitModel data, Response response) {
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
