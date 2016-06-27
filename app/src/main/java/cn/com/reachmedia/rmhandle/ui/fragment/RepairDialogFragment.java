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
public class RepairDialogFragment extends SupportBlurDialogFragment {


    OnDialogEnterListener mListener;
    public final static int REPAIR_TYPE_0 = 0;//无需补刊
    public final static int REPAIR_TYPE_1 = 1;//需补刊
    @Bind(R.id.rb_error_0)
    RadioButton rbError0;
    @Bind(R.id.rb_error_1)
    RadioButton rbError1;
    @Bind(R.id.rg_error_type)
    RadioGroup rgErrorType;
    @Bind(R.id.et_error_desc)
    EditText etErrorDesc;

    private int repair_type;

    private int stateType;//0:上刊 1:下刊 2:巡检

    public RepairDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public RepairDialogFragment(OnDialogEnterListener listener,int stateType) {
        this.mListener = listener;
        repair_type = REPAIR_TYPE_0;
        this.stateType = stateType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_repair, container);
        ButterKnife.bind(this, view);
        switch (stateType){
            case 1:
            case 2:
                rgErrorType.setVisibility(View.GONE);
                break;
            case 0:
                rgErrorType.setVisibility(View.VISIBLE);
                break;
        }
        rbError0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_type = REPAIR_TYPE_0;
            }
        });
        rbError1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repair_type = REPAIR_TYPE_1;
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnDialogEnterListener {
        void doSubmit(int type, String content);
    }

    @OnClick(R.id.bt_submit)
    public void doSubmit(){
        if(mListener!=null){
            mListener.doSubmit(repair_type,etErrorDesc.getText().toString());
        }
        this.dismiss();
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
