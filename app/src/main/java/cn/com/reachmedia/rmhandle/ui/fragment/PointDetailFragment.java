package cn.com.reachmedia.rmhandle.ui.fragment;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.app.Constant;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.bean.PointWorkBean;
import cn.com.reachmedia.rmhandle.db.utils.PointBeanDbUtil;
import cn.com.reachmedia.rmhandle.db.utils.PointWorkBeanDbUtil;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.service.ServiceHelper;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.ProportionImageView;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.CropImageUtils;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.PhotoSavePathUtil;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.ViewHelper;
import cn.com.reachmedia.rmhandle.utils.album.FileTraversal;
import cn.com.reachmedia.rmhandle.utils.album.ImgsActivity;
import cn.com.reachmedia.rmhandle.utils.album.Util;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 上午10:56
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PointDetailFragment extends BaseToolbarFragment {


    @Bind(R.id.iv_point_photo_1)
    ProportionImageView ivPointPhoto1;
    @Bind(R.id.iv_point_photo_2)
    ProportionImageView ivPointPhoto2;
    @Bind(R.id.iv_point_photo_3)
    ProportionImageView ivPointPhoto3;
    @Bind(R.id.iv_comm_photo_1)
    ProportionImageView ivCommPhoto1;
    @Bind(R.id.rl_comm_photo_1)
    RelativeLayout rlCommPhoto1;
    @Bind(R.id.iv_comm_photo_2)
    ProportionImageView ivCommPhoto2;
    @Bind(R.id.rl_comm_photo_2)
    RelativeLayout rlCommPhoto2;
    @Bind(R.id.iv_comm_photo_3)
    ProportionImageView ivCommPhoto3;
    @Bind(R.id.rl_comm_photo_3)
    RelativeLayout rlCommPhoto3;
    @Bind(R.id.tv_cname)
    TextView tvCname;
    @Bind(R.id.iv_cust_photo_1)
    ProportionImageView ivCustPhoto1;
    @Bind(R.id.iv_cust_photo_2)
    ProportionImageView ivCustPhoto2;
    @Bind(R.id.iv_cust_photo_3)
    ProportionImageView ivCustPhoto3;

    @Bind(R.id.ll_cust_photo)
    LinearLayout ll_cust_photo;
    ProportionImageView[] custPhotos;
    @Bind(R.id.tv_action_time)
    TextView tvActionTime;
    @Bind(R.id.wb_memo)
    WebView wbMemo;
    @Bind(R.id.bt_done)
    Button btDone;
    @Bind(R.id.bt_cant_enter)
    Button btCantEnter;
    @Bind(R.id.bt_report_question)
    Button btReportQuestion;
    @Bind(R.id.iv_left_text)
    TextView ivLeftText;
    @Bind(R.id.rl_left_text)
    RelativeLayout rlLeftText;

    @Bind(R.id.iv_delete_1)
    ImageView tv_delete_photo1;
    @Bind(R.id.iv_delete_2)
    ImageView tv_delete_photo2;
    @Bind(R.id.iv_delete_3)
    ImageView tv_delete_photo3;

    ImageView[] deleteBtns;

    //增加图片按钮数组
    ProportionImageView[] addPhotos;
    boolean insertOrUpdate;
    //当前图片数量
    private int photoCount ;
    private int photoName;
    private int photoMaxCount = 4;

    public static PointDetailFragment newInstance() {
        PointDetailFragment fragment = new PointDetailFragment();
        Bundle args = new Bundle();
//        args.putParcelable(AppParamContact.PARAM_KEY_MODEL, model);
        fragment.setArguments(args);
        return fragment;
    }

    public PointDetailFragment() {
    }

    private ApartmentPointUtils apartmentPointUtils;
    private PointBeanDbUtil pointBeanDbUtil;
    private PointWorkBeanDbUtil pointWorkBeanDbUtil;
    private PointBean bean;
    private PointListModel pointListModel;
    List<String> commImgList = new ArrayList<>();
    List<String> cusImgList = new ArrayList<>();
    String[] prePhotoIds,prePhotoUrlS,prePhotoUrlB;
    List<String> deletePrePhoto;
    List<String> prePhotoUrlBs = new ArrayList<>();
    int prePhotoSize;//已有图片数量
    PointWorkBean pointWorkBean;
    String[] cacheFileId,cacheFilePath;

    //没有提交图片的信息处理
    String[] remainFileId = null;
    String[] remainFilePath = null;
    int remainLocalIdSize = 0;
    List<String> deleteLocalPrePhoto;//id 未提交的图片删除

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apartmentPointUtils = ApartmentPointUtils.getIns();
        pointBeanDbUtil = PointBeanDbUtil.getIns();
        pointWorkBeanDbUtil = PointWorkBeanDbUtil.getIns();
        bean = pointBeanDbUtil.getPointBeanByWPID(apartmentPointUtils.workId, apartmentPointUtils.pointId);
        pointListModel = apartmentPointUtils.pointListModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_point_detail, container, false);
        ButterKnife.bind(this, rootView);
        custPhotos = new ProportionImageView[]{ivCustPhoto1,ivCustPhoto2,ivCustPhoto3};
        needTitle();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        commImgList.add(pointListModel.getCGatePic());
        commImgList.add(pointListModel.getCPestPic());

        try {
            int tempName = Integer.parseInt(bean.getDoor());
            setTitle(tempName + "号楼" + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        } catch (Exception e) {
            setTitle(bean.getDoor() + (bean.getGround() == 0 ? "地下" : "地上") + "点位");
        }

        tvActionTime.setText("到画时间："+bean.getPictime()+"    上画时间："+bean.getWorktime());
        //客户文字和照片 start
        String cid = bean.getCid().trim();
        for(PointListModel.ComListBean comBean:pointListModel.getComList()){
            if(comBean.getCid().trim().equals(cid)){
                tvCname.setText(comBean.getCname());
                wbMemo.getSettings().setDefaultTextEncodingName("utf-8");
                wbMemo.loadDataWithBaseURL("",comBean.getMemo(),"text/html", "utf-8","");

                if(comBean.getPicList()==null || comBean.getPicList().size()==0){
                    ll_cust_photo.setVisibility(View.GONE);
                }else{
                    for(int i=0;i<comBean.getPicList().size();i++){
                        if(i>=3) break;
                        PointListModel.ComListBean.PicListBean picBean = comBean.getPicList().get(i);
                        cusImgList.add(picBean.getPicurlB());
                        if(!StringUtils.isEmpty(picBean.getPicurlB())){
                            custPhotos[i].setVisibility(View.VISIBLE);
                            Picasso.with(getActivity()).load(picBean.getPicurlB()).placeholder(R.drawable.abc).into(custPhotos[i]);
                        }
                    }
                }
                break;
            }
        }
        //客户文字和照片 end

        if(!StringUtils.isEmpty(pointListModel.getCGatePic())){
            Picasso.with(getActivity()).load(pointListModel.getCGatePic()).placeholder(R.drawable.abc).into(ivCommPhoto1);
        }
        if(!StringUtils.isEmpty(pointListModel.getCPestPic())){
            Picasso.with(getActivity()).load(pointListModel.getCPestPic()).placeholder(R.drawable.abc).into(ivCommPhoto3);
        }
        if(!StringUtils.isEmpty(bean.getCDoorPic())){
            Picasso.with(getActivity()).load(bean.getCDoorPic()).placeholder(R.drawable.abc).into(ivCommPhoto2);
        }

        mergeLocalPhoto();
        initPhoto();

    }
    /**
     * 整合网络和本地图片
     */
    private void mergeLocalPhoto(){
        if(!StringUtils.isEmpty(bean.getFileId())){
            prePhotoIds = bean.getFileId().split(PointWorkBeanDbUtil.FILE_SPLIT);
        }else{
            prePhotoIds = new String[0];
        }
        deletePrePhoto = new ArrayList<>();
        deleteLocalPrePhoto = new ArrayList<>();

        if(!StringUtils.isEmpty(bean.getFileUrlB())){
            prePhotoUrlB = bean.getFileUrlB().split(PointWorkBeanDbUtil.FILE_SPLIT);
            if(prePhotoUrlB.length<prePhotoIds.length){
                prePhotoUrlB = new String[prePhotoIds.length];
            }
        }else{
            prePhotoUrlB = new String[prePhotoIds.length];
        }

        Collections.addAll(prePhotoUrlBs,prePhotoUrlB);

        if(!StringUtils.isEmpty(bean.getFileUrlS())){
            prePhotoUrlS = bean.getFileUrlS().split(PointWorkBeanDbUtil.FILE_SPLIT);
        }else{
            prePhotoUrlS = new String[0];
        }
        prePhotoSize = prePhotoIds.length;
        pointWorkBean = pointWorkBeanDbUtil.getPointWorkBeanByWPIDAll(bean.getWorkId(),bean.getPointId());
        if(pointWorkBean!=null){
            cacheFileId = pointWorkBean.getFileIdData().split(PointWorkBeanDbUtil.FILE_SPLIT);
            cacheFilePath = pointWorkBean.getFilePathData().split(PointWorkBeanDbUtil.FILE_SPLIT);
        }


    }


    @OnClick(R.id.bt_done)
    public void goDone(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_submit_point)
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
                        PointWorkBean pointWorkBean = getPointBean(1,0,"",0,"",mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LONGITUDE),mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LATITUDE));
                        if(insertOrUpdate){
                            PointWorkBeanDbUtil.getIns().insertOneData(pointWorkBean);
                        }else{
                            PointWorkBeanDbUtil.getIns().updateOneData(pointWorkBean);
                        }
                        ServiceHelper.getIns().startPointWorkService(getActivity());
                        ToastHelper.showInfo(getActivity(),"提交成功!");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().finish();
                            }
                        }, 1000);
                    }
                })
                .show();
    }

    @OnClick(R.id.bt_cant_enter)
    public void goCannotEnter(){

    }

    @OnClick(R.id.bt_report_question)
    public void goReportQuestion(){

    }


    /**
     *
     * @param state
     * @param repairType
     * @param repairDesc
     * @param errorType
     * @param errorDesc
     * @param lon
     * @param lat
     * @return
     */
    public PointWorkBean getPointBean(int state,int repairType,String repairDesc,int errorType,String errorDesc,String lon,String lat){
//        PointWorkBean pointWorkBean = pointWorkBeanDbUtil.getPointWorkBeanByWPID(bean.getWorkId(),bean.getPointId());
        insertOrUpdate = pointWorkBean==null;
        if(insertOrUpdate){
            pointWorkBean = new PointWorkBean();
        }
        pointWorkBean.setLastId(bean.getId());
        pointWorkBean.setUserId(bean.getUserId());
        pointWorkBean.setWorkId(bean.getWorkId());
        pointWorkBean.setPointId(bean.getPointId());
        pointWorkBean.setState(state);
        pointWorkBean.setRepairType(repairType);
        pointWorkBean.setRepairDesc(repairDesc);
        pointWorkBean.setErrorType(errorType);
        pointWorkBean.setErrorDesc(errorDesc);
        pointWorkBean.setLon(lon);
        pointWorkBean.setLat(lat);
        pointWorkBean.setWorkTime(TimeUtils.getNowStr());
        pointWorkBean.setOnlineTime(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_ON_LINE_TIME));
        pointWorkBean.setNativeState("0");

        pointWorkBean.setFiledelete(PointWorkBeanDbUtil.getSplitStrWeb(deletePrePhoto,deletePrePhoto.size()));
        pointWorkBean.setStarttime(bean.getStarttime());
        pointWorkBean.setCommunityid(bean.getCommunityid());

        //整合iddata和pathdata
        //
        //未提交id与图片的path处理
        String[] localFileIdDataR = new String[0];
        String[] localFilePathDataR = new String[0];
        if(!insertOrUpdate && !pointWorkBean.getNativeState().equals("2")){
            List<String> localFileIdDataRL = new ArrayList<>();
            List<String> localFilePathDataRL = new ArrayList<>();
            for(int i=0;i<remainFileId.length;i++){
                boolean addFlag = true;
                for(String tempId:deleteLocalPrePhoto){
                    if(tempId.equals(remainFileId[i])){
                        addFlag = false;
                        break;
                    }
                }
                for(String tempId:deletePrePhoto){
                    if(tempId.equals(remainFileId[i])){
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag){
                    localFileIdDataRL.add(remainFileId[i]);
                    localFilePathDataRL.add(remainFilePath[i]);
                }
            }
            localFileIdDataR = localFileIdDataRL.toArray(new String[localFileIdDataRL.size()]);
            localFilePathDataR = localFilePathDataRL.toArray(new String[localFilePathDataRL.size()]);
        }


        String[] resultFileIdData = concat(localFileIdDataR,photo_full_id,prePhotoSize-deletePrePhoto.size(),photoCount);
        String[] resultFilePathData = concat(localFilePathDataR,photo_full_path,prePhotoSize-deletePrePhoto.size(),photoCount);
        System.out.println("====>photo size:"+photoCount+":"+prePhotoSize+":"+deletePrePhoto.size()+":"+remainLocalIdSize+":"+deleteLocalPrePhoto.size());

        pointWorkBean.setFileCount(resultFileIdData.length);

        if(resultFileIdData.length>0){
            pointWorkBean.setFileIdData(PointWorkBeanDbUtil.getSplitStr(resultFileIdData,0,resultFileIdData.length));
            pointWorkBean.setFilePathData(PointWorkBeanDbUtil.getSplitStr(resultFilePathData,0,photoCount));
            pointWorkBean.setFileXY(PointWorkBeanDbUtil.tempGetXY(resultFileIdData.length));
            pointWorkBean.setFileTime(PointWorkBeanDbUtil.tempGetTime(resultFileIdData.length));
        }else{
            if(insertOrUpdate) {
                pointWorkBean.setFileIdData("");
                pointWorkBean.setFilePathData("");
                pointWorkBean.setFileXY("");
                pointWorkBean.setFileTime("");
            }
        }

        if(door_photo_full_id!=null){
            pointWorkBean.setDoorpicid(door_photo_full_id);
            pointWorkBean.setDoorpic(door_photo_full_path);
            pointWorkBean.setDoorpicXY(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LATITUDE)+","+SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LATITUDE));
            pointWorkBean.setDoorpicTime(TimeUtils.getNowStr());
        }else{
            if(insertOrUpdate){
                pointWorkBean.setDoorpicid("");
                pointWorkBean.setDoorpic("");
                pointWorkBean.setDoorpicXY("");
                pointWorkBean.setDoorpicTime("");
            }

        }

        return pointWorkBean;
    }

    public String[] concat(String[] a, String[] b,int bStart,int bCount) {
        String[] c= new String[a.length+bCount];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, bStart, c, a.length, bCount);
        return c;
    }

    //    -------------- 相册 -----------
    /**
     * 请求相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /**
     * 请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;
    /**
     * 漏洞着请求相机
     */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA_DOOR = 9;

    /**
     * 请求我们自己的相册
     */
    public static final int REQUEST_CODE_GETIMAGE_BYALBUM = 5;
    private Uri[] origUri;
    private Uri[] cropUri;
    private File[] photoFile;
    // 保存路径为 RMHandle/人员ID/photo
    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";
    private String photo_path;
    //图片全路径
    private String[] photo_full_path;
    private String[] photo_full_id;
    //楼栋照路径
    private String door_photo_full_path;
    private String door_photo_full_id;
    private File doorPhotoFile;
    private Uri doorOrigUri;
    private Uri doorCropUri;
    private boolean doorFlag = false;

    /**
     * 初始化图片上传功能
     */
    private void initPhoto() {
        addPhotos = new ProportionImageView[]{ivPointPhoto1,ivPointPhoto2,ivPointPhoto3};
        deleteBtns = new ImageView[]{tv_delete_photo1,tv_delete_photo2,tv_delete_photo3};
        photoFile = new File[photoMaxCount];
        origUri = new Uri[photoMaxCount];
        cropUri = new Uri[photoMaxCount];
        photo_full_path = new String[photoMaxCount];
        photo_full_id = new String[photoMaxCount];
        ImageUtils.photoBitmap = new ArrayList<>();
        ImageUtils.cacheBitmap = new ArrayList<>();
        ImageUtils.cacheLoaclBitmap = new ArrayList<>();
        // 保存路径为 WoJiaWang/人员ID/portrait
        photo_path = path + "point/";
        addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooseItem();
            }
        });

//        本地未提交id的图片统计
        List<String> tempRemailFileId = new ArrayList<>();
        List<String> tempRemailFilePath = new ArrayList<>();
        if(pointWorkBean!=null && pointWorkBean.getNativeState()!=null && pointWorkBean.getNativeState().equals("0")){//未提交
            //处理无id无url,全部添加到需要提交的数组
            remainLocalIdSize = remainFileId.length;
            remainFileId = cacheFileId.clone();
            remainFilePath = cacheFilePath.clone();
        }else if(pointWorkBean!=null && pointWorkBean.getNativeState()!=null && pointWorkBean.getNativeState().equals("1")){//提交了id未提交图片,就要考虑是否服务器删除了图片
            for(int i=0;i<cacheFileId.length;i++){//处理无id无url
                boolean tempFlag = true;//是否有ID；true 没有
                for(int j=0;j<prePhotoIds.length;j++){
                    if(cacheFileId[i].equals(prePhotoIds[j])){//有一样的
                        tempFlag = false;
                        break;
                    }
                }
                if(!tempFlag){//添加到需要提交的list
                    tempRemailFileId.add(cacheFileId[i]);
                    tempRemailFilePath.add(cacheFilePath[i]);
                }
            }
            remainFileId = tempRemailFileId.toArray(new String[tempRemailFileId.size()]);
            remainFilePath = tempRemailFilePath.toArray(new String[tempRemailFilePath.size()]);
        }else{
            // 2，图片都提交了,啥都不管
        }
        //        本地未提交id的图片统计


        int preAddPhotoSize = 0;
        for(int i=0;i<prePhotoSize;i++){
            if(i>2) break;
            if(prePhotoUrlS.length>i){//有id有url
                if(!StringUtils.isEmpty(prePhotoUrlS[i])){
                    Picasso.with(getActivity()).load(prePhotoUrlS[i]).placeholder(R.drawable.abc).into(addPhotos[i]);
                }
            }else{//有id无url
                if(pointWorkBean!=null){
                    for(int j=0;j<cacheFileId.length;j++){
                        if(prePhotoIds[i].equals(cacheFileId[j])){
                            Bitmap myBitmap4 = null;
                            String str = cacheFilePath[j];
                            try {
                                byte[] mContent3 = ImageUtils.readStream(new FileInputStream(str));
                                int b = ImageUtils.getExifOrientation(str);
                                if (b != 0) {
                                    myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()), b);
                                } else {
                                    myBitmap4 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                                }
                                Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap4);
                                ImageUtils.cacheBitmap.add(bitmapTemp2);
                                addPhotos[i].setImageBitmap(bitmapTemp2);
                                preAddPhotoSize++;
                                myBitmap4.recycle();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
            initNextPhotoLocal();
        }



        if(preAddPhotoSize<photoMaxCount-1) {//判断图片是否超过最大值
            if (pointWorkBean != null && pointWorkBean.getNativeState().equals("0")) {//处理未提交id的图片的显示
                for (int j = 0; j < remainFileId.length; j++) {
                    Bitmap myBitmap4 = null;
                    String str = remainFilePath[j];
                    try {
                        byte[] mContent3 = ImageUtils.readStream(new FileInputStream(str));
                        int b = ImageUtils.getExifOrientation(str);
                        if (b != 0) {
                            myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()), b);
                        } else {
                            myBitmap4 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                        }
                        Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap4);
                        ImageUtils.cacheLoaclBitmap.add(bitmapTemp2);
                        addPhotos[preAddPhotoSize].setImageBitmap(bitmapTemp2);
                        preAddPhotoSize++;
                        myBitmap4.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    initNextPhotoLocal();
                }
            }
        }
    }

    /**
     * 操作选择
     *
     */
    public void imageChooseItem() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_add_photo)
                .items(R.array.dialog_add_photo)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            startActionCamera();
                        }
                        // 相册选图
                        else if (which == 1) {
//                            startImagePick();
                            startAlbum();
                        }
                    }
                })
                .show();

    }

    private void startAlbum(){
//        Intent intent = new Intent();
//        intent.setClass(getContext(),ImgFileListActivity.class);
//        startActivity(intent);
        Util util=new Util(getContext());
        List<FileTraversal> locallist=util.LocalImgFileList();
        Util.localFile= locallist.get(0);
        Intent intent=new Intent(getContext(),ImgsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("count",photoMaxCount-photoCount-1);
        intent.putExtras(bundle);
        startActivityForResult(intent,REQUEST_CODE_GETIMAGE_BYALBUM);
        try {

//            System.out.println("-=-=-=-=>  =>>  "+locallist.get(0).filecontent.size());
//            Intent intent=new Intent(getContext(),ImgsActivity.class);
//            Bundle bundle=new Bundle();
//            bundle.putParcelable("data", locallist.get(0));
//            bundle.putInt("count",4-photoCount);
//            intent.putExtras(bundle);
//            startActivityForResult(intent,REQUEST_CODE_GETIMAGE_BYALBUM);
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    /**
     * 相机拍照
     */
    private void startActionCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFile());
        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA);
    }

    // 拍照保存的绝对路径
    private Uri getCameraTempFile() {
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(photo_path);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        // 照片命名
        // 裁剪头像的绝对路径
        photo_full_id[photoCount] = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,String.valueOf(photoName),bean.getUserId());
        photo_full_path[photoCount] = ImageUtils.getPointPicPath(photo_full_id[photoCount],photo_path);
        photoFile[photoCount] = new File(photo_full_path[photoCount]);
        cropUri[photoCount] = Uri.fromFile(photoFile[photoCount]);
        this.origUri[photoCount] = this.cropUri[photoCount];
        return this.cropUri[photoCount];
    }
    private Uri getCameraTempFileDoor() {
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(photo_path);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.toast_sdcard_error),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        // 照片命名
        // 裁剪头像的绝对路径
        door_photo_full_id = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,"door",bean.getUserId());
        door_photo_full_path = ImageUtils.getPointPicPath(door_photo_full_id,photo_path);

        doorPhotoFile = new File(door_photo_full_path);
        doorCropUri = Uri.fromFile(doorPhotoFile);
        this.doorOrigUri = this.doorCropUri;
        return this.doorCropUri;
    }

    /**
     * 删除图片
     * */
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initCancelPhoto(final int index){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_del_photo)
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
                        for(int i=index;i<photoCount;i++){
                            if(i<photoMaxCount -2){
                                final int tempIndex = i;
                                addPhotos[i].setImageDrawable(addPhotos[i + 1].getDrawable());
                                addPhotos[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ViewHelper.getImagePagerLocal(getActivity(), tempIndex);
                                    }
                                });
                                photo_full_path[i] = photo_full_path[i+1];
                            }
                        }
                        if(photoCount<photoMaxCount -1){
                            addPhotos[photoCount].setVisibility(View.INVISIBLE);
                        }
                        photo_full_path[photoCount] = null;

                        photoCount--;
                        System.out.println("initCancelPhoto: "+prePhotoSize+":"+deletePrePhoto.size()+":"+remainLocalIdSize+":"+deleteLocalPrePhoto.size());
                        if(index>=prePhotoSize-deletePrePhoto.size()+remainLocalIdSize-deleteLocalPrePhoto.size()){//如果删除的本地图片,提交了id没有提交图片
                            ImageUtils.photoBitmap.remove(index-prePhotoSize+deletePrePhoto.size()-remainLocalIdSize+deleteLocalPrePhoto.size());

                        }else if(index<(prePhotoSize-deletePrePhoto.size()+remainLocalIdSize-deleteLocalPrePhoto.size()) && index >=(prePhotoSize-deletePrePhoto.size())){//id和图片都没提交
                            ImageUtils.cacheLoaclBitmap.remove(index-prePhotoSize+deletePrePhoto.size());

                        }else{//如果删除的是网络图片
                            int tempRemoteIndex = index;
                            while(validateRemoteIndex(tempRemoteIndex)){
                                tempRemoteIndex++;
                            }
                            deletePrePhoto.add(prePhotoIds[tempRemoteIndex]);
                        }

                        deleteBtns[photoCount].setVisibility(View.INVISIBLE);
                        addPhotos[photoCount].setImageResource(R.mipmap.picture_add_icon);
                        addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                imageChooseItem();
                            }
                        });
                    }
                })
                .show();
    }

    private boolean validateRemoteIndex(int index){
        for(String deleteId:deletePrePhoto){
            if(prePhotoIds[index].equals(deleteId)){
                return true;
            }
        }
        return false;
    }

    private void initNextPhotoLocal(){
        final int tempIndex = photoCount;
        addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastHelper.showAlert(getActivity(), "咩哈哈");
//                ViewHelper.getImagePagerLocal(getActivity(), tempIndex);
                ViewHelper.getImagePager(getActivity(), prePhotoUrlBs, tempIndex,true,remainLocalIdSize);

            }
        });
        deleteBtns[photoCount].setVisibility(View.VISIBLE);
        System.out.println("====>photoCount: "+photoCount);
        if(photoCount<photoMaxCount-1){
            photoCount++;
            if(photoCount<addPhotos.length){
                addPhotos[photoCount].setVisibility(View.VISIBLE);
                addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageChooseItem();
                    }
                });
            }
        }
    }

    private void initNextPhoto(){
        final int tempIndex = photoCount;
        addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastHelper.showAlert(getActivity(), "咩哈哈");
//                ViewHelper.getImagePagerLocal(getActivity(), tempIndex);
                ViewHelper.getImagePager(getActivity(), prePhotoUrlBs, tempIndex,true,remainLocalIdSize);

            }
        });
        deleteBtns[photoCount].setVisibility(View.VISIBLE);
        if(photoCount<photoMaxCount-1){
            photoCount++;
            addPhotos[photoCount].setVisibility(View.VISIBLE);
            addPhotos[photoCount].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageChooseItem();
                }
            });

        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver resolver = getActivity().getContentResolver();
        if (resultCode != getActivity().RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_GETIMAGE_BYALBUM:
                if(resultCode==-1){
                    for(String str:Util.filelist){
                        Bitmap myBitmap4 = null;
                        photo_full_path[photoCount] = str;

                        try {
                            byte[] mContent3 = ImageUtils.readStream(new FileInputStream(str));
                            //将字节数组转换为ImageView可调用的Bitmap对象
                            int b = ImageUtils.getExifOrientation(str);
                            if(b!=0){
                                myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()),b);
                            }else {
                                myBitmap4 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                            }
                            //把得到的图片绑定在控件上显示
                            Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap4);
                            photo_full_id[photoCount] = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,String.valueOf(photoName),bean.getUserId());
                            photo_full_path[photoCount] = ImageUtils.saveCompressPicPath(bitmapTemp2,ImageUtils.getPointPicPath(photo_full_id[photoCount],photo_path),photo_path);
                            photoName++;
                            ImageUtils.photoBitmap.add(bitmapTemp2);
                            addPhotos[photoCount].setImageBitmap(ImageUtils.photoBitmap.get(ImageUtils.photoBitmap.size() - 1));
                            myBitmap4.recycle();
                            initNextPhoto();
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
                break;
            case REQUEST_CODE_GETIMAGE_BYCAMERA:
                Bitmap myBitmap = null;
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    byte[] mContent=ImageUtils.readStream(resolver.openInputStream(origUri[photoCount]));
                    //图片旋转
                    int a = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), origUri[photoCount]));
                    if(a!=0){
                        myBitmap = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption()),a);
                    }else{
                        myBitmap = ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption());
                    }
                    //将字节数组转换为ImageView可调用的Bitmap对象

                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp = ImageUtils.comp(myBitmap);
                    photo_full_id[photoCount] = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,String.valueOf(photoName),bean.getUserId());
                    photo_full_path[photoCount] = ImageUtils.saveCompressPicPath(bitmapTemp,ImageUtils.getPointPicPath(photo_full_id[photoCount],photo_path),photo_path);
                    photoName++;
                    ImageUtils.photoBitmap.add(bitmapTemp);
                    addPhotos[photoCount].setImageBitmap(ImageUtils.photoBitmap.get(ImageUtils.photoBitmap.size()-1));
//                    saveCompressPic(myBitmap);
                    myBitmap.recycle();
                    initNextPhoto();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
            case REQUEST_CODE_GETIMAGE_BYCAMERA_DOOR://门洞照
                Bitmap myBitmapDoor = null;
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    byte[] mContent=ImageUtils.readStream(resolver.openInputStream(doorOrigUri));
                    //图片旋转
                    int a = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), doorOrigUri));
                    if(a!=0){
                        myBitmapDoor = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption()),a);
                    }else{
                        myBitmapDoor = ImageUtils.getPicFromBytes(mContent, ImageUtils.getBitmapOption());
                    }
                    //将字节数组转换为ImageView可调用的Bitmap对象

                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp = ImageUtils.comp(myBitmapDoor);
                    door_photo_full_id = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,"door",bean.getUserId());
                    door_photo_full_path = ImageUtils.saveCompressPicPath(bitmapTemp,ImageUtils.getPointPicPath(door_photo_full_id,photo_path),photo_path);
                    doorFlag = true;
                    ImageUtils.doorPhotoBitmap = bitmapTemp;
                    ivCommPhoto2.setImageBitmap(ImageUtils.doorPhotoBitmap);
//                    saveCompressPic(myBitmap);
                    myBitmapDoor.recycle();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;


            case REQUEST_CODE_GETIMAGE_BYSDCARD:
                // 将照片显示在头像上
                Bundle extras = data.getExtras();
                Bitmap bmp = null;
                if (extras != null) {
                    bmp = data.getParcelableExtra("data");
                    ivPointPhoto1.setImageBitmap(bmp);
                }
                break;
            case Constant.KITKAT_LESS://门洞照
                Bitmap myBitmap3 = null;
                Uri uri = data.getData();
                String thePath = CropImageUtils.getInstance().getPath(getActivity(), uri);
//                photo_full_path[photoCount] = thePath;

//                System.out.println("4.4以下，选择好图片了:  " + uri);
                try {
                    byte[] mContent3 = ImageUtils.readStream(resolver.openInputStream(uri));
                    //将字节数组转换为ImageView可调用的Bitmap对象
                    int b = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), uri));
                    if(b!=0){
                        myBitmap3 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption()),b);
                    }else {
                        myBitmap3 = ImageUtils.getPicFromBytes(mContent3, ImageUtils.getBitmapOption());
                    }
                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp2 = ImageUtils.comp(myBitmap3);
                    door_photo_full_id = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,"door",bean.getUserId());
                    door_photo_full_path = ImageUtils.saveCompressPicPath(bitmapTemp2,ImageUtils.getPointPicPath(door_photo_full_id,photo_path),photo_path);
                    doorFlag = true;
                    ImageUtils.doorPhotoBitmap = bitmapTemp2;
                    ivCommPhoto2.setImageBitmap(ImageUtils.doorPhotoBitmap);
                    myBitmap3.recycle();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case Constant.KITKAT_ABOVE://门洞照
                Bitmap myBitmap4 = null;
                Uri uri3 = data.getData();
                // 先将这个uri转换为path，然后再转换为uri
//                System.out.println("4.4以上，选择好图片了");
                String thePath2 = CropImageUtils.getInstance().getPath(getActivity(), uri3);
//                photo_full_path[photoCount] = thePath2;
                try {
                    byte[] mContent4 = ImageUtils.readStream(resolver.openInputStream(uri3));
                    //将字节数组转换为ImageView可调用的Bitmap对象
                    int c = ImageUtils.getExifOrientation(ImageUtils.getPath(getActivity(), uri3));
                    if(c!=0){
                        myBitmap4 = ImageUtils.rotateBitMap(ImageUtils.getPicFromBytes(mContent4, ImageUtils.getBitmapOption()),c);
                    }else {
                        myBitmap4 = ImageUtils.getPicFromBytes(mContent4, ImageUtils.getBitmapOption());
                    }
                    //把得到的图片绑定在控件上显示
                    Bitmap bitmapTemp3 = ImageUtils.comp(myBitmap4);
                    door_photo_full_id = ImageUtils.getPointPicId(apartmentPointUtils.workId, apartmentPointUtils.pointId,"door",bean.getUserId());
                    door_photo_full_path = ImageUtils.saveCompressPicPath(bitmapTemp3,ImageUtils.getPointPicPath(door_photo_full_id,photo_path),photo_path);
                    doorFlag = true;
                    ImageUtils.doorPhotoBitmap = bitmapTemp3;
                    ivCommPhoto2.setImageBitmap(ImageUtils.doorPhotoBitmap);
                    myBitmap4.recycle();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @OnClick(R.id.iv_delete_1)
    public void deletePhoto1(){
        initCancelPhoto(0);
    }
    @OnClick(R.id.iv_delete_2)
    public void deletePhoto2(){
        initCancelPhoto(1);
    }
    @OnClick(R.id.iv_delete_3)
    public void deletePhoto3(){
        initCancelPhoto(2);
    }

    //    -------------- 相册 -----------

    //楼栋照 start
    @OnClick(R.id.iv_comm_photo_2)
    public void changeDoorPhoto(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_title_add_photo)
                .items(R.array.dialog_add_photo)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            startActionCameraDoor();

                        }
                        // 相册选图
                        else if (which == 1) {
                            startImagePick();
                        }
                    }
                })
                .show();


    }

    /**
     * 相机拍照
     */
    private void startActionCameraDoor() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFileDoor());
        startActivityForResult(intent, REQUEST_CODE_GETIMAGE_BYCAMERA_DOOR);
    }

    //    -------------- 选择本地图片
    private void startImagePick(){

        if (!ImageUtils.isKitKat) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //由于startActivityForResult()的第二个参数"requestCode"为常量，
            //个人喜好把常量用一个类全部装起来，不知道各位大神对这种做法有异议没？
//            System.out.println("<4.4");
            startActivityForResult(Intent.createChooser(intent, "选择图片"), Constant.KITKAT_LESS);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            //由于Intent.ACTION_OPEN_DOCUMENT的版本是4.4以上的内容
            //所以注意这个方法的最上面添加了@SuppressLint("InlinedApi")
            //如果客户使用的不是4.4以上的版本，因为前面有判断，所以根本不会走else，
            //也就不会出现任何因为这句代码引发的错误
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            System.out.println(">=4.4");

            startActivityForResult(Intent.createChooser(intent, "选择图片"), Constant.KITKAT_ABOVE);
        }

    }



    //楼栋照 end

    //放大图片
    @OnClick(R.id.iv_comm_photo_1)
    public void goViewCommunityPhoto1(){
        ViewHelper.getImagePager(getActivity(), commImgList, 0,false,remainLocalIdSize);
    }

    @OnClick(R.id.iv_comm_photo_3)
    public void goViewCommunityPhoto2(){
        ViewHelper.getImagePager(getActivity(), commImgList, 1,false,remainLocalIdSize);
    }

    @OnClick(R.id.iv_cust_photo_1)
    public void goViewCustomerPhoto1(){
        ViewHelper.getImagePager(getActivity(), cusImgList, 0,false,remainLocalIdSize);
    }
    @OnClick(R.id.iv_cust_photo_2)
    public void goViewCustomerPhoto2(){
        ViewHelper.getImagePager(getActivity(), cusImgList, 1,false,remainLocalIdSize);
    }
    @OnClick(R.id.iv_cust_photo_3)
    public void goViewCustomerPhoto3(){
        ViewHelper.getImagePager(getActivity(), cusImgList, 2,false,remainLocalIdSize);
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
