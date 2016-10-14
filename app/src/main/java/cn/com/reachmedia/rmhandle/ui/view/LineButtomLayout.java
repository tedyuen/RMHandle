package cn.com.reachmedia.rmhandle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.ui.fragment.NewPointDetailFragment;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-20.
 */
public class LineButtomLayout extends FrameLayout implements PointDetailLine{

    @Bind(R.id.rb_check_1)
    RadioButton rbCheck1;
    @Bind(R.id.rb_check_2)
    RadioButton rbCheck2;
    @Bind(R.id.ll_checkstate_frame)
    LinearLayout llCheckstateFrame;
    @Bind(R.id.bt_done)
    Button btDone;
    @Bind(R.id.bt_has_done)
    Button bt_has_done;
    @Bind(R.id.ll_done_mode)
    LinearLayout ll_done_mode;
    @Bind(R.id.ll_undone_mode)
    LinearLayout ll_undone_mode;

    NewPointDetailFragment fragment;

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

    public int setBottom(PointBean bean,String backText){
        if (bean.getWorkUp() == 1) {
            llCheckstateFrame.setVisibility(View.GONE);
            btDone.setText("上刊完成");
            bt_has_done.setText("完成".equals(backText) ? "上刊" + backText : backText);
            return 0;
        } else if (bean.getWorkDown() == 1) {
            llCheckstateFrame.setVisibility(View.GONE);
            btDone.setText("下刊完成");
            bt_has_done.setText("完成".equals(backText) ? "下刊" + backText : backText);
            return 1;
        } else {
            llCheckstateFrame.setVisibility(View.VISIBLE);
            if(bean.getCheckState()==2){
                rbCheck2.setChecked(true);
            }else {
                rbCheck1.setChecked(true);
            }
            btDone.setText("巡检完成");
            bt_has_done.setText("完成".equals(backText) ? "巡检" + backText : backText);
            return 2;
        }
    }

    @Override
    public void init(NewPointDetailFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void changeEditMode(boolean flag) {
        if (flag) {
            ll_done_mode.setVisibility(View.GONE);
            ll_undone_mode.setVisibility(View.VISIBLE);
            rbCheck1.setVisibility(View.VISIBLE);
            rbCheck2.setVisibility(View.VISIBLE);
            rbCheck1.setEnabled(true);
            rbCheck2.setEnabled(true);
        } else {
            ll_done_mode.setVisibility(View.VISIBLE);
            ll_undone_mode.setVisibility(View.GONE);
            rbCheck1.setEnabled(false);
            rbCheck2.setEnabled(false);
        }
    }

    /**
     * 获得巡检状态
     * @return
     */
    public int getCheckState(int stateType){
        if(stateType!=2){
            return 0;
        }else{
            if(rbCheck1.isChecked()){
                return 1;
            }else{
                return 2;
            }
        }
    }
}
