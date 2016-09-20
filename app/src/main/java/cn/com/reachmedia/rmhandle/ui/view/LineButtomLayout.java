package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;

/**
 * Created by tedyuen on 16-9-20.
 */
public class LineButtomLayout extends FrameLayout {


    public LineButtomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_buttom_layout, this);
        ButterKnife.bind(this);


    }

}
