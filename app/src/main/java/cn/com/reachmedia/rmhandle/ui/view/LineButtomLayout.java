package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;

/**
 * Created by tedyuen on 16-9-20.
 */
public class LineButtomLayout extends FrameLayout {

    @Bind(R.id.rb_check_1)
    RadioButton rbCheck1;
    @Bind(R.id.rb_check_2)
    RadioButton rbCheck2;

    public LineButtomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_buttom_layout, this);
        ButterKnife.bind(this);


    }

    public void changeXunjianState(int state){
        if(state==1){
            rbCheck1.setChecked(true);
            rbCheck1.setVisibility(View.VISIBLE);
            rbCheck2.setVisibility(View.GONE);
        }else {
            rbCheck2.setChecked(true);
            rbCheck2.setVisibility(View.VISIBLE);
            rbCheck1.setVisibility(View.GONE);
        }
    }

}
