package cn.com.reachmedia.rmhandle.network.controller;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.model.UploadPicModel;
import cn.com.reachmedia.rmhandle.model.param.UploadPicParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
