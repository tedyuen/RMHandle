package cn.com.reachmedia.rmhandle.ui.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.fragment.BaseFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午4:03
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class BaseToolbarFragment extends BaseFragment {

    @Bind(R.id.iv_back_img)
    ImageView ivBackImg;
    @Bind(R.id.iv_back)
    RelativeLayout ivBack;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 需要label作title时调用
     */
    public void needTitle(){
        toolbarTitle.setText(getActivity().getTitle());
    }
    public void setTitle(String title){
        if(toolbarTitle!=null){
            toolbarTitle.setText(title != null ? title : "");

        }
    }

    @OnClick(R.id.iv_back)
    public void back(){
        if(backListener==null){
            getActivity().finish();//默认的返回按钮

        }else{
            backListener.goBack();
        }
    }

    public void hideBackBtn(){
        ivBack.setVisibility(View.INVISIBLE);
    }

    public void showBackBtn(){
        ivBack.setVisibility(View.VISIBLE);
    }

    public interface BackListener{
        void goBack();
    }
    private BackListener backListener;

    public void setBackListener(BackListener backListener){
        this.backListener = backListener;
    }
}
