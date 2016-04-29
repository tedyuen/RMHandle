package cn.com.reachmedia.rmhandle.network.http;

import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.model.BaseModel;
import cn.com.reachmedia.rmhandle.model.LoginModel;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.TaskDetailModel;
import cn.com.reachmedia.rmhandle.model.TaskIndexModel;
import cn.com.reachmedia.rmhandle.model.TaskMapModel;
import cn.com.reachmedia.rmhandle.model.UploadWorkModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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











}
