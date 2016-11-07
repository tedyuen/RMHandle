package cn.com.reachmedia.rmhandle.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 下午3:29
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();
    protected SharedPreferencesHelper mSharedPreferencesHelper;

    public ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferencesHelper = SharedPreferencesHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayoutResId();

    public void showProgressDialog(){
        if(getActivity()!=null){
            mProgressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading_message));
            mProgressDialog.setCanceledOnTouchOutside(true);
        }
    }

    public void setCancelable(boolean flag){
        if(getActivity()!=null && mProgressDialog!=null){
//            mProgressDialog.setCanceledOnTouchOutside(flag);
            mProgressDialog.setCancelable(flag);
        }
    }


    public void closeProgressDialog(){
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
