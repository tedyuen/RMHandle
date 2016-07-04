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
    public static final String API_HOST_MN = "http://120.26.64.180:8381/app";//API Host 模拟
    public static final String API_HOST_PD = "http://120.26.64.180:8281/app";//API Host 模拟
    public static final String API_ACTION = "/data.api";//api action
    public static final String API_ACTION_FILE = "/file.api";//api action
    public static final String API_ACTION_UPDATE = "/app/getversion.jsp";//api action

    public static String getApiHost(){
//http://120.26.64.180:8281/app/data.api
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

        /**
         * 3.4 首页任务地图
         */
        public static final String TASK_MAP_METHOD = "RM_GCBAPP_taskmap";

        /**
         * 3.5 进入小区接口
         */
        public static final String POINT_LIST_METHOD = "RM_GCBAPP_pointList";


        /**
         * 3.7 提交工作工作项接口
         */
        public static final String UPLOAD_WORK_METHOD = "RM_GCBAPP_uploadWork";

        /**
         * 3.8 提交工作工作项(图片)接口
         */
        public static final String UPLOAD_PIC_METHOD = "RM_GCBAPP_uploadPicFile";


        /**
         * 3.9 提交卡密码接口
         */
        public static final String CARD_SUBMIT = "RM_GCBAPP_cardSubmit";

        /**
         * 3.10 小区图片提交
         */
        public static final String CPIC_SUBMIT = "RM_GCBAPP_CPicSubmit";


        /**
         * 3.12 查询卡密码历史记录
         */
        public static final String CARD_LIST = "RM_GCBAPP_cardList";


        /**
         * 4.1 版本检测接口
         */
        public static final String VERSION_METHOD = "RM_GCBAPP_version";



    }

    /**
     * 错误代码
     */
    public class ErrorCode{

        /**
         * 正确返回
         */
        public static final String SUCCESS = "0000";

        /**
         * 程序异常
         */
        public static final String EXCEPTION = "ER99";

    }
}
