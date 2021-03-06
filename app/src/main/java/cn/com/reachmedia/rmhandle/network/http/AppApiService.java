package cn.com.reachmedia.rmhandle.network.http;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.model.BaseModel;
import cn.com.reachmedia.rmhandle.model.CardListModel;
import cn.com.reachmedia.rmhandle.model.CardSubmitModel;
import cn.com.reachmedia.rmhandle.model.ErrorLogModel;
import cn.com.reachmedia.rmhandle.model.GetVersionModel;
import cn.com.reachmedia.rmhandle.model.LoginModel;
import cn.com.reachmedia.rmhandle.model.PicSubmitModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.UploadPicModel;
import cn.com.reachmedia.rmhandle.model.UploadWorkModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 上午11:22
 * Description: Retrofit 注解API接口
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public interface AppApiService {

    /**
     * 3.1 用户登录接口
     *
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void onLogin(@Field("json") String json,
                 Callback<LoginModel> cb);

    /**
     * 3.2 任务首页接口
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void getTaskIndex(@Field("json") String json,
                 Callback<TaskIndexModel> cb);

    /**
     * 3.3 首页信息总览
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void getTaskDetail(@Field("json") String json,
                       Callback<TaskDetailModel> cb);

    /**
     * 3.4 首页任务地图
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void getTaskMap(@Field("json") String json,
                       Callback<TaskMapModel> cb);

    /**
     * 3.5 进入小区接口
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void getPointList(@Field("json") String json,
                       Callback<PointListModel> cb);


    /**
     * 3.7 提交工作工作项接口
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void uploadWork(@Field("json") String json,
                       Callback<UploadWorkModel> cb);

    /**
     * 3.9 提交卡密码
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void cardSubmit(@Field("json") String json,
                       Callback<CardSubmitModel> cb);

    /**
     * 3.12 查询卡密码历史记录接口
     * @param json
     * @param cb
     */
    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION)
    void getCardList(@Field("json") String json,
                       Callback<CardListModel> cb);

    @FormUrlEncoded
    @POST(AppApiContact.API_ACTION_UPDATE)
    void getVersion(@Field("json") String json,
                    Callback<GetVersionModel> cb);


    /**
     * 3.8 提交工作图片
     * @param json
     * @param file1
     * @param file2
     * @param file3
     * @param communityDoor
     * @param file1Id
     * @param file2Id
     * @param file3Id
     * @param communityDoorId
     * @param communityDoorXY
     * @param communityDoorTime
     * @param cb
     */
    @Multipart
    @POST(AppApiContact.API_ACTION_FILE)
    void uploadPic(@Part("json") TypedString json,
                   @Part("file1") TypedFile file1,
                   @Part("file2") TypedFile file2,
                   @Part("file3") TypedFile file3,
                   @Part("communityDoor") TypedFile communityDoor,
                   @Part("file1Id") TypedString file1Id,
                   @Part("file2Id") TypedString file2Id,
                   @Part("file3Id") TypedString file3Id,
                   @Part("communityDoorId") TypedString communityDoorId,
                   @Part("communityDoorXY") TypedString communityDoorXY,
                   @Part("communityDoorTime") TypedString communityDoorTime,
                       Callback<UploadPicModel> cb);

    @Multipart
    @POST(AppApiContact.API_UPLOAD_ERROR_LOG)
    void uploadErrorLog(@Part("json") TypedString json,
                        @Part("logFile") TypedFile file,
                        @Part("fileId") TypedString file1Id,
                        Callback<ErrorLogModel> cb);


    @Multipart
    @POST(AppApiContact.API_ACTION_FILE)
    void picSubmit(@Part("json") TypedString json,
                   @Part("communityFile1") TypedFile file1,
                   @Part("communitySpace1") TypedFile file2,
                   @Part("communityFile2") TypedFile file3,
                   @Part("communitySpace2") TypedFile file4,
                   @Part("communityFileId1") TypedString file1Id,
                   @Part("communitySpaceId1") TypedString file2Id,
                   @Part("communityFileId2") TypedString file3Id,
                   @Part("communitySpaceId2") TypedString communityDoorId,
                       Callback<PicSubmitModel> cb);


    @GET(AppApiContact.API_ONLINE_TIME)
    void getOnlineTime(Callback<String> callback);









}
