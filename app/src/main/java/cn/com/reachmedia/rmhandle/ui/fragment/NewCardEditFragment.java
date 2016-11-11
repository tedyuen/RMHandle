package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anthonycr.grant.PermissionsManager;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.CommDoorPicBean;
import cn.com.reachmedia.rmhandle.db.utils.CommPoorPicDbUtil;
import cn.com.reachmedia.rmhandle.model.CardSubmitModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.CardSubmitParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.CardSubmitController;
import cn.com.reachmedia.rmhandle.ui.CardListActivity;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.CardEditLineImage1;
import cn.com.reachmedia.rmhandle.ui.view.CardEditLineImage2;
import cn.com.reachmedia.rmhandle.utils.FileUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Created by tedyuen on 16-11-9.
 */
public class NewCardEditFragment extends BaseToolbarFragment {


    @Bind(R.id.line_image_1)
    CardEditLineImage1 lineImage1;
    @Bind(R.id.line_image_2)
    CardEditLineImage2 lineImage2;
    @Bind(R.id.bt_edit_save_photo)
    Button btEditSavePhoto;
    @Bind(R.id.et_gate_card)
    EditText etGateCard;
    @Bind(R.id.bt_edit_save_text_1)
    Button btEditSaveText1;
    @Bind(R.id.bt_edit_history_text_1)
    Button btEditHistoryText1;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_edit_save_text_2)
    Button btEditSaveText2;
    @Bind(R.id.bt_edit_history_text_2)
    Button btEditHistoryText2;

    public String workId;
    public String pointId;
    public PointListModel pointListModel;//缓存列表数据
    public String userId;
    CommPoorPicDbUtil commPoorPicDbUtil = CommPoorPicDbUtil.getIns();
    public CommDoorPicBean commBean=null;
    public boolean insertOrUpdate;


    public static NewCardEditFragment newInstance() {
        NewCardEditFragment fragment = new NewCardEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewCardEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_fragment_card_edit, container, false);
        ButterKnife.bind(this, rootView);
        initData();
        needTitle();
//        lineImage2.init(this);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (pointListModel != null) {
            if (!StringUtils.isEmpty(pointListModel.getCommunity())) {
                setTitle(pointListModel.getCommunity());
            }

            etGateCard.setText(pointListModel.getCarddesc());
            etPassword.setText(pointListModel.getDoordesc());
            initBean();

        }
        lineImage1.init(this);
        lineImage2.init(this);




        if(!lineImage1.updateAddPhotosClickState(getActivity(),savedInstanceState)){
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            //这里需要结束activity
        }

        if(!lineImage2.updateAddPhotosClickState(getActivity(),savedInstanceState)){
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            //这里需要结束activity
        }



    }

    public void initBean(){
        if (!StringUtils.isEmpty(pointListModel.getCommunityid())) {
            commBean = commPoorPicDbUtil.getBeanByCommId(pointListModel.getCommunityid(),"0");
        }
        insertOrUpdate = commBean==null;
    }

    public void initData(){
        String communityId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_COMMUNITID);
        String tempStartTime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME);
        String dataJson = mSharedPreferencesHelper.getString(communityId+"_"+tempStartTime);
        workId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_WORK_ID);
        pointId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_POINT_ID);
        userId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_ID);
        if(!StringUtils.isEmpty(dataJson)) {
            Gson gson = new Gson();
            try {
                pointListModel = gson.fromJson(dataJson, PointListModel.class);
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().finish();
            }
        }else {
            getActivity().finish();
        }

    }

    @OnClick({R.id.bt_edit_save_text_1,R.id.bt_edit_save_text_2})
    public void submitCard(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_submit_card)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        CardSubmitController cardSubmitController = new CardSubmitController(new UiDisplayListener<CardSubmitModel>() {
                            @Override
                            public void onSuccessDisplay(CardSubmitModel data) {
                                closeProgressDialog();
                                if (data != null) {
                                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                                        ToastHelper.showInfo(getActivity(),"修改成功!");

                                    }
                                }
                            }

                            @Override
                            public void onFailDisplay(String errorMsg) {
                                closeProgressDialog();

                            }
                        });
                        CardSubmitParam cardSubmitParam = new CardSubmitParam();
                        cardSubmitParam.communityId = pointListModel.getCommunityid();
                        cardSubmitParam.doordesc = etPassword.getText().toString();
                        cardSubmitParam.carddesc = etGateCard.getText().toString();
                        cardSubmitController.cardSubmit(cardSubmitParam);
                        showProgressDialog();
                    }
                })
                .show();
    }


    @OnClick({R.id.bt_edit_history_text_1,R.id.bt_edit_history_text_2})
    public void goCardList(){
        Intent intent = new Intent(getActivity(),CardListActivity.class);
        intent.putExtra("communityId",pointListModel.getCommunityid());
        intent.putExtra("communityName",pointListModel.getCommunity());
        startActivity(intent);
    }
    private static final String COMMIT_SUCCESS = "提交成功!";

    @OnClick(R.id.bt_edit_save_photo)
    public void picSubmit(){

        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_submit_gate_photo)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        CommDoorPicBean bean = getCommBean();
                        if (bean == null) {
                            dialog.dismiss();
                            ToastHelper.showInfo(getActivity(), "请添加图片!");
                        } else {
                            if(insertOrUpdate){
                                commPoorPicDbUtil.insertOneData(bean);
                            }else{
                                commPoorPicDbUtil.updateOneData(bean);
                            }
                            ToastHelper.showInfo(getActivity(), COMMIT_SUCCESS);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().finish();
                                }
                            }, 500);
                        }
                    }
                })
                .show();

    }

    public void showCommitProgressDialog(){
        showProgressDialog();
        setCancelable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setCancelable(true);
            }
        }, 10000);
    }

    public CommDoorPicBean getCommBean(){
        if(insertOrUpdate){
            commBean = new CommDoorPicBean();
            if (!StringUtils.isEmpty(pointListModel.getCommunityid())) {
                commBean.setCommunityId(pointListModel.getCommunityid());
            }else{
                return null;//没有小区id，异常
            }
        }
        commBean.setNativeState("0");

        boolean picFlag = false;

        if(lineImage1.hasChange()){
            commBean = lineImage1.getDooBean(commBean);
            picFlag = true;
        }
        if(lineImage2.hasChange()){
            commBean = lineImage2.getDooBean(commBean);
            picFlag = true;
        }

        if(!picFlag){
            return  null;
        }else{
            return commBean;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lineImage1.onActivityResult(requestCode,resultCode,data);
        lineImage2.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        lineImage1.onSaveInstanceState(savedInstanceState);
        lineImage2.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
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
}
