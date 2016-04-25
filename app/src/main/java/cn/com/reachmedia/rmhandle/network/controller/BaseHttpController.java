package cn.com.reachmedia.rmhandle.network.controller;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.network.AppNetworkInfo;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:40
 * Description: 网络请求控制类父类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class BaseHttpController <T> {
    protected static final String TAG = BaseHttpController.class.getSimpleName();
    protected UiDisplayListener<T> uiDisplayListener;

    public BaseHttpController() {
    }

    public BaseHttpController(UiDisplayListener<T> uiDisplayListener) {
        this.uiDisplayListener = uiDisplayListener;
    }

    public void setUiDisplayListener(UiDisplayListener<T> uiDisplayListener) {
        this.uiDisplayListener = uiDisplayListener;
    }

    protected void loadData() {
        if (AppNetworkInfo.isNetworkAvailable(App.getContext())) {//没有网络时直接调用失败接口
            getNetData();
        } else {
            if (uiDisplayListener != null) {
                uiDisplayListener.onFailDisplay("没有网络");
            }
        }
    }

    /**
     * 获取网络数据
     */
    protected abstract void getNetData();
}
