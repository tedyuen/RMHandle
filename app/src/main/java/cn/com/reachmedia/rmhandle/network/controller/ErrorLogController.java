package cn.com.reachmedia.rmhandle.network.controller;

import java.io.File;

import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.model.ErrorLogModel;
import cn.com.reachmedia.rmhandle.model.param.ErrorLogParam;
import cn.com.reachmedia.rmhandle.network.callback.HttpBaseCallBack;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by tedyuen on 16-10-26.
 */
public class ErrorLogController extends BaseHttpController<ErrorLogModel> {

    private ErrorLogParam errorLogParam;
    private File file;
    public String fileId;

    public ErrorLogController(){}

    public ErrorLogController(UiDisplayListener<ErrorLogModel> uiDisplayListener){
        super(uiDisplayListener);

    }

    public void uploadErrorLog(ErrorLogParam errorLogParam,File file,String fileId){
        this.errorLogParam = errorLogParam;
        this.file = file;
        this.fileId = fileId;
        getNetData();
    }

    @Override
    protected void getNetData() {
        TypedFile tfile=null;
        if(file!=null){
            tfile = new TypedFile("text/plain",file);
        }

        if(tfile!=null){
            TypedString tfileId = new TypedString(fileId);
            TypedString jsons = new TypedString(errorLogParam.toJson());
            LogUtils.d(TAG, errorLogParam.toJson());
            App.getAppApiService().uploadErrorLog(jsons,tfile,tfileId,callBack);
        }
    }

    public HttpBaseCallBack<ErrorLogModel> callBack = new HttpBaseCallBack<ErrorLogModel>() {
        @Override
        public void success(ErrorLogModel data, Response response) {
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
