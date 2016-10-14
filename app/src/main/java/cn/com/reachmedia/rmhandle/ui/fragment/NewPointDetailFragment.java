package cn.com.reachmedia.rmhandle.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.Line2ImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.Line3ImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.LineButtomLayout;
import cn.com.reachmedia.rmhandle.ui.view.LineImageLayout;
import cn.com.reachmedia.rmhandle.ui.view.imagepager.ImageAllBean;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Created by tedyuen on 16-9-19.
 */
public class NewPointDetailFragment extends BaseToolbarFragment {


    @Bind(R.id.line_image_1)
    LineImageLayout lineImage1;
    @Bind(R.id.line_image_2)
    Line2ImageLayout lineImage2;
    @Bind(R.id.ll_cust_photo)
    Line3ImageLayout ll_cust_photo;
    @Bind(R.id.ll_bottom_frame)
    LineButtomLayout lineButtom;

    @Bind(R.id.tv_action_time)
    TextView tvActionTime;
    @Bind(R.id.tv_cname)
    TextView tvCname;
    @Bind(R.id.wb_memo)
    WebView wbMemo;

    @Bind(R.id.bt_show_webview)
    Button btShowWebview;

    @Bind(R.id.tv_error_text)
    TextView tvErrorText;
    @Bind(R.id.tv_error_desc)
    TextView tvErrorDesc;
    @Bind(R.id.ll_error_frame)
    LinearLayout llErrorFrame;

    @Bind(R.id.rl_right_text)
    RelativeLayout rl_right_text;

    int stateType;//0:上刊 1:下刊  2:巡查
    int stateFinish;//0:未完成 1:无法进入 2:报错



    public static NewPointDetailFragment newInstance() {
        NewPointDetailFragment fragment = new NewPointDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewPointDetailFragment() {
    }


    private PointBeanDbUtil pointBeanDbUtil;
    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    public PointListModel pointListModel;//缓存列表数据
    public PointBean bean;//网络数据
    public PointWorkBean pointWorkBean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointBeanDbUtil = PointBeanDbUtil.getIns();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        String communityId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_COMMUNITID);
        String tempStartTime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME);
        String dataJson = mSharedPreferencesHelper.getString(communityId+"_"+tempStartTime);
        String workId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_WORK_ID);
        String pointId = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_POINT_ID);
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
        bean = pointBeanDbUtil.getPointBeanByWPID(workId, pointId);
        if(bean!=null){
            pointWorkBean = pointWorkBeanDbUtil.getPointWorkBeanByWPIDAll(bean.getWorkId(), bean.getPointId());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_fragment_point_detail, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        lineImage1.init(this);
        lineImage2.init(this);
        lineButtom.init(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化小区门口及环境照


        //设置页面标题
        try {
            int tempName = Integer.parseInt(bean.getDoor());
            setTitle(tempName + "号楼" + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        } catch (Exception e) {
            setTitle(bean.getDoor() + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        }
        tvActionTime.setText("到画时间：" + bean.getPictime() + "    上画时间：" + bean.getWorktime());
        //客户文字和照片
        String cid = bean.getCid().trim();
        for (PointListModel.ComListBean comBean : pointListModel.getComList()) {
            if (comBean.getCid().trim().equals(cid)) {
                tvCname.setText(comBean.getCname());
//                wbMemo.getSettings().setDefaultTextEncodingName("utf-8");
//                wbMemo.loadDataWithBaseURL("", comBean.getMemo(), "text/html", "utf-8", "");
                webViewStr = comBean.getMemo();
                if(StringUtils.isEmpty(webViewStr)){
                    btShowWebview.setVisibility(View.GONE);
                }else{
                    btShowWebview.setVisibility(View.VISIBLE);
                }
                if (comBean.getPicList() == null || comBean.getPicList().size() == 0) {
                    ll_cust_photo.setVisibility(View.GONE);
                } else {
                    if(comBean.getPicList()!=null){
                        ll_cust_photo.setCusImg(comBean.getPicList());
                    }
                }
                break;
            }
        }

        //巡检状态
        lineButtom.changeXunjianState(bean.getCheckState());

        //门洞照显示
        if (!StringUtils.isEmpty(bean.getCDoorPic())) {
            ImageAllBean doorPicBean = new ImageAllBean(bean.getCDoorPic(),ImageAllBean.URL_IMG);
            lineImage2.setDoorPic(doorPicBean);
        }

        if(pointListModel!=null){
            lineImage2.setCommunityPhoto(pointListModel);
        }

        lineImage1.updateAddPhotosClickState(getActivity(),savedInstanceState);
        lineImage2.updateAddPhotosClickState(getActivity(),savedInstanceState);

        mergeLocalPhoto();
        initStateType();
    }

    /**
     * 整合网络和本地图片
     */
    private void mergeLocalPhoto(){


    }

    /**
     * 初始化上下刊状态
     */
    private void initStateType(){
        if (pointWorkBean != null) {
            stateFinish = pointWorkBean.getState();
        } else {
            if (bean != null) {
                stateFinish = bean.getStateType();
            }
        }
        String backText = "完成";

        switch (stateFinish) {
            case 0://未完成
                changeEditMode(true);
                llErrorFrame.setVisibility(View.GONE);

                break;
            case 1://完成
                changeEditMode(false);
                llErrorFrame.setVisibility(View.GONE);

                break;
            case 2://保修
                changeEditMode(false);
                llErrorFrame.setVisibility(View.VISIBLE);
                backText = "报修";
                tvErrorText.setText(backText);
                String tempDesc1 = bean.getStateTypeDesc()+":"+(StringUtils.isEmpty(bean.getErrorDesc()) ? "无备注" : bean.getErrorDesc());
                tvErrorDesc.setText(tempDesc1);
                break;
            case 3://无法进入
                changeEditMode(false);
                llErrorFrame.setVisibility(View.VISIBLE);
                backText = "无法进入";
                tvErrorText.setText(backText);
                String tempDesc2 = bean.getStateTypeDesc()+":"+(StringUtils.isEmpty(bean.getErrorDesc()) ? "无备注" : bean.getErrorDesc());
                tvErrorDesc.setText(tempDesc2);
                break;
        }

        if (bean != null) {
            stateType = lineButtom.setBottom(bean,backText);
        }

    }

    /**
     * 改变编辑模式
     *
     * @param flag true:可编辑，false:不可编辑
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void changeEditMode(boolean flag){
        if(flag){
            rl_right_text.setVisibility(View.GONE);
        }else{
            rl_right_text.setVisibility(View.VISIBLE);
        }
        lineImage1.changeEditMode(flag);
        lineImage2.changeEditMode(flag);
        lineButtom.changeEditMode(flag);

    }

    @OnClick(R.id.rl_right_text)
    public void editMode() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_change_edit_mode)
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
                        changeEditMode(true);
                    }
                })
                .show();
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
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private String webViewStr;

    @OnClick(R.id.bt_show_webview)
    public void clickShowWebView(){
        showWebview();
    }

    private void showWebview(){
        if(!StringUtils.isEmpty(webViewStr)){
            wbMemo.getSettings().setDefaultTextEncodingName("utf-8");
            wbMemo.loadDataWithBaseURL("", webViewStr, "text/html", "utf-8", "");
            btShowWebview.setVisibility(View.GONE);
        }

    }
}
