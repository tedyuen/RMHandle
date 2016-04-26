package cn.com.reachmedia.rmhandle.app;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/18 上午11:50
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/18          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class AppApiContact {
    public static final String API_HOST_ST = "http://120.26.65.65:8281/app";//API Host 测试
    public static final String API_ACTION = "/data.api";//api action

    public static String getApiHost(){

        return API_HOST_ST;
    }


    /**
     * 接口method定义
     */
    public class InterfaceMethod{
        /**
         * 3.1 用户登录
         */
        public static final String LOGIN_METHOD = "RM_GCBAPP_login";


        /**
         * 3.2 任务首页接口
         */
        public static final String TASK_INDEX_METHOD = "RM_GCBAPP_taskindex";


        /**
         * 3.3 首页信息总览
         */
        public static final String TASK_DETAIL_METHOD = "RM_GCBAPP_taskdetail";
    }
}
