package cn.com.reachmedia.rmhandle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.CardEditLineImage1;
import cn.com.reachmedia.rmhandle.ui.view.CardEditLineImage2;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

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
        lineImage1.init(this);
//        lineImage2.init(this);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if(!lineImage1.updateAddPhotosClickState(getActivity(),savedInstanceState)){
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            //这里需要结束activity
        }

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lineImage1.onActivityResult(requestCode,resultCode,data);
//        lineImage2.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        lineImage1.onSaveInstanceState(savedInstanceState);
//        lineImage2.onSaveInstanceState(savedInstanceState);
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
