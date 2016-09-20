package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;

/**
 * Created by tedyuen on 16-9-20.
 */
public class Line2ImageLayout extends FrameLayout {


    public Line2ImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line2_image_layout, this);
        ButterKnife.bind(this);


    }

}
