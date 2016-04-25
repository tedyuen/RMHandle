package cn.com.reachmedia.rmhandle.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.BaseModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.LoginController;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/25 下午5:26
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/25          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class InterfaceTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void testLogin(){
        LoginController lc = new LoginController(new UiDisplayListener<BaseModel>() {
            @Override
            public void onSuccessDisplay(BaseModel data) {

            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        LoginParam param = new LoginParam();
        param.username = "18616990040";
        param.password = "111111";
        lc.onLogin(param);
    }

}
