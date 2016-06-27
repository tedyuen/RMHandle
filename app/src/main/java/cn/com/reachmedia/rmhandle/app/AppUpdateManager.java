package cn.com.reachmedia.rmhandle.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.GetVersionModel;
import cn.com.reachmedia.rmhandle.model.param.GetVersionParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.GetVersionController;
import cn.com.reachmedia.rmhandle.utils.AppVersionHelper;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import cn.com.reachmedia.rmhandle.utils.SharedPreferencesHelper;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/27 下午2:04
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class AppUpdateManager {

    private final String TAG = AppUpdateManager.class.getSimpleName();
    /* 下载包安装路径 */
    private static final String savePath = Environment.getExternalStorageDirectory().getPath() + "/RMHandle/";
    private static final String saveFileName = savePath + "UpdateRelease.apk";
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private GetVersionController mUpdateVersionController;

    private Context mContext;
    private ProgressBar mProgress;
    private String apkUrl = "";
    private int progress;
    private boolean interceptFlag = false;
    private boolean isShowMessage = false;
    private AlertDialog downloadDialog;
    private AlertDialog mVersionInfoDialog;
    private boolean isUpdate = false;//是否强制更新

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
        }
    };

    public AppUpdateManager(Context context) {
        this.mContext = context;
        mUpdateVersionController = new GetVersionController(updateVersionDisplayListener);
    }

    public void setShowMessage(boolean isShowMessage) {
        this.isShowMessage = isShowMessage;
    }

    // 外部接口让主Activity调用
    public void checkUpdateInfo(Context context) {
        GetVersionParam getVersionParam = new GetVersionParam();
        try {
            getVersionParam.version = AppVersionHelper.getVersionName(context);
        } catch (Exception e) {
            e.printStackTrace();
            getVersionParam.version = "";
        }
        getVersionParam.build = AppVersionHelper.getVersionCode(mContext);
        mUpdateVersionController.getVersion(getVersionParam);
    }

    public void onDestroy() {
        if (mUpdateVersionController != null) {
            mUpdateVersionController.setUiDisplayListener(null);
        }
    }

    protected UiDisplayListener<GetVersionModel> updateVersionDisplayListener = new UiDisplayListener<GetVersionModel>() {
        @Override
        public void onSuccessDisplay(GetVersionModel data) {
            if (mContext != null) {
                if (data != null && AppApiContact.ErrorCode.SUCCESS.equals(data.rescode) && !StringUtils.isEmpty(data.newversion)) {
                    try {
                        LogUtils.d(TAG, "checkUpdateInfo--> newVersion = "+data.versioncode);
                        if(data.isalert==1){//有更新
                            isUpdate = data.isupdate==1;
                            apkUrl = data.downUrl;

                            showNoticeDialog(data.content, isUpdate);
                        }else if(data.isalert==0){//无更新
                            if(isShowMessage) {
                                ToastHelper.showInfo((Activity) mContext, R.string.no_update_version);
                            }
                        }


//                        int oldVersionCold = AppVersionHelper.getVersionCode(mContext);
//                        if (data.versioncode > oldVersionCold && StringHelper.notEmpty(data.downUrl)) {
//                            apkUrl = data.downUrl;
//                            isUpdate = data.isupdate==1;
//                            if(isShowMessage){
//                                showNoticeDialog(data.newversion, isUpdate);
//                            }else{
////                                if(isUpdate){
//                                    showNoticeDialog(data.newversion, isUpdate);
////                                }
//                            }
//                        } else {
//                            if (isShowMessage) {
//                                ToastHelper.showInfo((Activity) mContext, R.string.no_update_version);
//                            }
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (isShowMessage) {
                        ToastHelper.showInfo((Activity) mContext, R.string.no_update_version);
                    }
                }
            }
        }

        @Override
        public void onFailDisplay(String errorMsg) {
            if (isShowMessage) {
                ToastHelper.showInfo((Activity) mContext, R.string.no_update_version);
            }
        }
    };

    private void showNoticeDialog(String version, final boolean isUpdate) {
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.update_version);
//            builder.setMessage(String.format(mContext.getResources().getString(R.string.update_message), version));//old
            builder.setMessage(version);
            builder.setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showDownloadDialog(isUpdate);
                }
            });
            if (isUpdate) {
                SharedPreferencesHelper.getInstance().putString(AppSpContact.SP_KEY_CHECK_UPDATE_TIME, "");
                builder.setCancelable(false);
                builder.setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            downloadDialog.dismiss();
                        }
                        return true;
                    }
                });
            } else {
                builder.setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            mVersionInfoDialog = builder.create();
            mVersionInfoDialog.show();
        }
    }

    private void showDownloadDialog(final boolean isUpdate) {
        if (mContext != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.update_version);

            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.include_update_progress, null, false);
            mProgress = (ProgressBar) v.findViewById(R.id.progress);

            builder.setView(v);
            if (isUpdate) {
                builder.setCancelable(false);
                builder.setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            downloadDialog.dismiss();
                        }
                        return true;
                    }
                });
            } else {
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        interceptFlag = true;
                    }
                });
            }

            downloadDialog = builder.create();
            downloadDialog.show();

            downloadApk();
        }
    }

    private Runnable mDownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);

                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numRead = is.read(buf);
                    count += numRead;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numRead <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numRead);
                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */
    private void downloadApk() {
        Thread downLoadThread = new Thread(mDownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        if (mContext != null) {
            mContext.startActivity(i);
        }

        try {
            if (!isUpdate) {
                downloadDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
