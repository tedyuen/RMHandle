package cn.com.reachmedia.rmhandle.service.onlinetime;

import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * Created by tedyuen on 16-10-17.
 */
public class OnlineTimeWork extends TimerTask {

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
                new OnlineTimeAction().getOnlineTime();
            }
        }
    };
}
