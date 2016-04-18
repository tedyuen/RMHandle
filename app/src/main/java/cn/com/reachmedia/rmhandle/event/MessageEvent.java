package cn.com.reachmedia.rmhandle.event;


import cn.com.reachmedia.rmhandle.utils.LogUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      15/10/23 下午3:09
 * Description: 广播事件
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/10/23          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class MessageEvent {
    public final String message;

    public MessageEvent(String message){
        this.message = message;
        LogUtils.d("TAG", message + "...............发送通知");
    }
}
