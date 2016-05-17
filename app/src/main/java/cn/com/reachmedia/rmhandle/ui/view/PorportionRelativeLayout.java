package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.com.reachmedia.rmhandle.R;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 上午10:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PorportionRelativeLayout extends RelativeLayout {
    private float proportion = 1f;//默认宽高比


    public PorportionRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PorportionRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProportionImageView);
            proportion = a.getFloat(R.styleable.ProportionImageView_proportion,proportion);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);   //获取ViewGroup宽度
        setMeasuredDimension(width,(int)(width*proportion));
    }
}
