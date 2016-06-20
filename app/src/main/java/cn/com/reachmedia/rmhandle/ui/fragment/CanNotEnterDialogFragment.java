package cn.com.reachmedia.rmhandle.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/20 下午4:04
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/20          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CanNotEnterDialogFragment extends SupportBlurDialogFragment {

    public final static int ERROR_TYPE_0 = 0;//门卡无法打开
    public final static int ERROR_TYPE_1 = 1;//拿不到门卡
    public final static int ERROR_TYPE_9 = 9;//其他

    OnDialogEnterListener mListener;
    @Bind(R.id.rb_error_0)
    RadioButton rbError0;
    @Bind(R.id.rb_error_1)
    RadioButton rbError1;
    @Bind(R.id.rb_error_9)
    RadioButton rbError9;
    @Bind(R.id.rg_error_type)
    RadioGroup rgErrorType;
    @Bind(R.id.et_error_desc)
    EditText etErrorDesc;
    @Bind(R.id.bt_submit)
    Button btSubmit;

    private int errorType;

    public CanNotEnterDialogFragment(){}

    @SuppressLint("ValidFragment")
    public CanNotEnterDialogFragment(OnDialogEnterListener listener) {
        this.mListener = listener;
        errorType = ERROR_TYPE_0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_can_not_enter, container);
        ButterKnife.bind(this, view);
        rbError0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorType = ERROR_TYPE_0;
            }
        });
        rbError1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorType = ERROR_TYPE_1;
            }
        });
        rbError9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorType = ERROR_TYPE_9;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.bt_submit)
    public void doSubmit(){
        if(mListener!=null){
            mListener.doSubmit(errorType,etErrorDesc.getText().toString());
        }
        this.dismiss();
    }

    public interface OnDialogEnterListener {
        void doSubmit(int type, String content);
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 8.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 8;
    }
}
