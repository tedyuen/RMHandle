package cn.com.reachmedia.rmhandle.service;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/20 下午6:42
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LocationWork extends TimerTask {

    @Override
    public void run() {
        Message message = new Message();
        message.what=1;
        handler.sendMessage(message);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what==1)
            {
                new UpdateLocation().location();
            }
        }
    };
}
