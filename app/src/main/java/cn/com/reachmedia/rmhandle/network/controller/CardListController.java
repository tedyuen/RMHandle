package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.CardListModel;
import cn.com.reachmedia.rmhandle.model.param.CardListParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 上午11:23
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardListController extends BaseHttpController<CardListModel> {

    private CardListParam cardListParam;

    public CardListController(){}

    public CardListController(UiDisplayListener<CardListModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void getCardList(CardListParam cardListParam){
        this.cardListParam = cardListParam;
        getNetData();
    }

    @Override
    protected void getNetData() {
        LogUtils.json(TAG, cardListParam.toJson());
        LogUtils.d(TAG, cardListParam.toJson());
        App.getAppApiService().getCardList(cardListParam.toJson(),
                new HttpBaseCallBack<CardListModel>() {
                    @Override
                    public void success(CardListModel data, Response response) {
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
