package cn.com.reachmedia.rmhandle.network.controller;

import java.io.File;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.UploadPicModel;
import cn.com.reachmedia.rmhandle.model.param.UploadPicParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/23 下午5:53
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/23          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class UploadPicController extends BaseHttpController<UploadPicModel> {
    private UploadPicParam uploadPicParam;
    private File file1;
    private File file2;
    private File file3;
    private File communityDoorFile;

    public UploadPicController(){}

    public UploadPicController(UiDisplayListener<UploadPicModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void uploadPic(UploadPicParam uploadPicParam,File file1,File file2,File file3,File communityDoorFile){
        this.uploadPicParam = uploadPicParam;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.communityDoorFile = communityDoorFile;
        getNetData();
    }

    @Override
    protected void getNetData() {
        TypedFile tfile1,tfile2,tfile3,tcommunityDoorFile;
        if(file1!=null){
            tfile1 = new TypedFile("image/jpeg",file1);
        }else{
            tfile1 = null;
        }
        if(file2!=null){
            tfile2 = new TypedFile("image/jpeg",file2);
        }else{
            tfile2 = null;
        }
        if(file3!=null){
            tfile3 = new TypedFile("image/jpeg",file3);
        }else{
            tfile3 = null;
        }
        if(communityDoorFile!=null){
            tcommunityDoorFile = new TypedFile("image/jpeg",communityDoorFile);
        }else{
            tcommunityDoorFile = null;
        }
        TypedString jsons = new TypedString(uploadPicParam.toJson());
        LogUtils.d(TAG, uploadPicParam.toJson());
        App.getAppApiService().uploadPic(jsons,tfile1,tfile2,tfile3,tcommunityDoorFile,callBack);
    }

    public HttpBaseCallBack<UploadPicModel> callBack = new HttpBaseCallBack<UploadPicModel>() {
        @Override
        public void success(UploadPicModel data, Response response) {
            super.success(data, response);
            if (uiDisplayListener != null) {
                uiDisplayListener.onSuccessDisplay(data);
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            super.failure(retrofitError);
            if (uiDisplayListener != null) {
                uiDisplayListener.onFailDisplay("");
            }
        }
    };
}
