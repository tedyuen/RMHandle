package cn.com.reachmedia.rmhandle.network.controller;

import java.io.File;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.PicSubmitModel;
import cn.com.reachmedia.rmhandle.model.param.PicSubmitParam;
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
 * Date:      16/6/12 下午6:39
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class PicSubmitController extends BaseHttpController<PicSubmitModel> {

    private PicSubmitParam picSubmitParam;
    private File file1;
    private File file2;
    private File file3;
    private File file4;
    public String file1Id;
    public String file2Id;
    public String file3Id;
    public String file4Id;

    public PicSubmitController(){}

    public PicSubmitController(UiDisplayListener<PicSubmitModel> uiDisplayListener){
        super(uiDisplayListener);
    }

    public void picSubmit(PicSubmitParam picSubmitParam, File file1, File file2, File file3, File file4,
                          String file1Id, String file2Id, String file3Id,
                          String file4Id){
        this.picSubmitParam = picSubmitParam;
        this.file1 = file1;
        this.file2 = file2;
        this.file3 = file3;
        this.file4 = file4;
        this.file1Id = file1Id;
        this.file2Id = file2Id;
        this.file3Id = file3Id;
        this.file4Id = file4Id;
        getNetData();
    }


    @Override
    protected void getNetData() {
        TypedFile tfile1,tfile2,tfile3,tfile4;
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
        if(file4!=null){
            tfile4 = new TypedFile("image/jpeg",file4);
        }else{
            tfile4 = null;
        }
        TypedString jsons = new TypedString(picSubmitParam.toJson());
        TypedString tfile1Id = new TypedString(file1Id);
        TypedString tfile2Id = new TypedString(file2Id);
        TypedString tfile3Id = new TypedString(file3Id);
        TypedString tfile4Id = new TypedString(file4Id);

        LogUtils.d(TAG, picSubmitParam.toJson());
        App.getAppApiService().picSubmit(jsons,tfile1,tfile2,tfile3,tfile4,
                tfile1Id,tfile2Id,tfile3Id,
                tfile4Id,
                callBack);
    }

    public HttpBaseCallBack<PicSubmitModel> callBack = new HttpBaseCallBack<PicSubmitModel>() {
        @Override
        public void success(PicSubmitModel data, Response response) {
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
