package cn.com.reachmedia.rmhandle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.test.InterfaceTestActivity;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_home)
    public void goHome(){
        startActivity(new Intent(this,HomeActivity.class));
    }


    @OnClick(R.id.btn_interface)
    public void goInterface(){
        startActivity(new Intent(this, InterfaceTestActivity.class));
    }
}
