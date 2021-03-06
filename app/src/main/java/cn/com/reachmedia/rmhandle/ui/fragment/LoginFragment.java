package cn.com.reachmedia.rmhandle.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.model.LoginModel;
import cn.com.reachmedia.rmhandle.model.param.LoginParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.LoginController;
import cn.com.reachmedia.rmhandle.ui.HomeActivity;
import cn.com.reachmedia.rmhandle.utils.HomeFilterUtil;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 上午9:53
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class LoginFragment extends BaseFragment {


    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_submit)
    Button btSubmit;

    public boolean USERNAME   = false;
    public boolean PASSWORD   = false;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {



    }


    private void getDeviceId(){
        String deviceId = ((TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_DEVICE_ID, deviceId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, rootView);
        String userToken = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_TOKEN,null);
        if(null!=userToken){
            startActivity(new Intent(getActivity(),HomeActivity.class));
            getActivity().finish();
        }
        etUsername.setText(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_NAME,""));
        etPassword.setText("");




        return rootView;
    }

    @OnClick(R.id.bt_submit)
    public void doLogin() {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        getDeviceId();

                        if (StringUtils.isEmpty(etUsername.getText().toString())) {
                            ToastHelper.showAlert(getActivity(), "请输入手机号");
                        } else {
                            if (StringUtils.isEmpty(etPassword.getText().toString())) {
                                ToastHelper.showAlert(getActivity(), "请输入密码");
                            } else {
                                USERNAME = UserName();
                                if (USERNAME) {
                                    PASSWORD = PassWord();
                                    if (PASSWORD) {
                                        final LoginParam param = new LoginParam();
                                        param.pswd = etPassword.getText().toString().trim();
                                        param.username = etUsername.getText().toString().trim();
                                        showProgressDialog();
                                        LoginController loginController = new LoginController(new UiDisplayListener<LoginModel>() {
                                            @Override
                                            public void onSuccessDisplay(LoginModel data) {
                                                closeProgressDialog();
                                                if (data != null) {
                                                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                                        String token = data.getUsertoken();
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_NAME, etUsername.getText().toString());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_TOKEN,token);
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID,data.getUserId());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_CITY_ID,data.getCityId());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_CITY,data.getCity());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_SPACE,data.getSpace());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_NAME,data.getName());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_TITLE,data.getTitle());
                                                        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_PIC_URL,data.getPicUrl());
                                                        HomeFilterUtil homeFilterUtil = HomeFilterUtil.getIns();
                                                        homeFilterUtil.initUtil();
                                                        homeFilterUtil.getThursday();
                                                        startActivity(new Intent(getActivity(),HomeActivity.class));
                                                        getActivity().finish();
                                                        return;
                                                    }
                                                    ToastHelper.showAlert(getActivity(), data.resdesc);
                                                }

                                            }

                                            @Override
                                            public void onFailDisplay(String errorMsg) {
                                                closeProgressDialog();
                                                ToastHelper.showAlert(getActivity(), getResources().getString(R.string.no_network));
                                            }
                                        });
                                        loginController.onLogin(param);

                                    }
                                }
                            }
                        }



                    }

                    @Override
                    public void onDenied(String permission) {
                        ToastHelper.showAlert(getActivity(),"没有读取权限，请前往设置");

                    }
                }
        );




    }



    public boolean UserName(){
        if(!StringUtils.isValidCellPhone(etUsername.getText().toString().trim())){
            ToastHelper.showAlert(getActivity(), "手机号有误");
        }else{
            return true;
        }
        return false;
    }
    public boolean PassWord(){
        if(!StringUtils.isValidPassword(etPassword.getText().toString())){
            ToastHelper.showAlert(getActivity(), "密码必须6到12位的数字，字母");
        }else{
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void requestReadPhonePermission(){
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        Log.i(TAG, "onGranted: Write Storage");
                        getDeviceId();
                    }

                    @Override
                    public void onDenied(String permission) {
                        Log.i(TAG, "onDenied: Write Storage: " + permission);
//                        String message = String.format(Locale.getDefault(), getString(R.string.camera_denied), permission);
                        getActivity().finish();

                    }
                }
        );
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
