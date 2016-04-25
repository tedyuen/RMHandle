package cn.com.reachmedia.rmhandle.network.callback;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:38
 * Description: HTTP 请求刷新UI回调接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public interface UiDisplayListener<T> {
    /**
     * HTTP请求成功回调
     *
     * @param data GSON解析之后的数据model
     */
    public void onSuccessDisplay(T data);

    /**
     * HTTP请求失败回调
     */
    public void onFailDisplay(String errorMsg);
}