package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.app.AppSpContact;
import cn.com.reachmedia.rmhandle.bean.ImageCacheBean;
import cn.com.reachmedia.rmhandle.db.helper.ImageCacheDaoHelper;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.bean.ImageCacheResBean;
import cn.com.reachmedia.rmhandle.utils.ImageCacheUtils;
import cn.com.reachmedia.rmhandle.utils.PhotoSavePathUtil;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import cn.com.reachmedia.rmhandle.utils.TimeUtils;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheFragment extends BaseToolbarFragment {

    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.tv_community_count)
    TextView tvCommunityCount;
    @Bind(R.id.tv_detail_url)
    TextView tv_detail_url;
    @Bind(R.id.tv_total_data)
    TextView tv_total_data;


    @Bind(R.id.rl_right_text)
    RelativeLayout rlRightText;
    @Bind(R.id.iv_info)
    ImageView ivInfo;
    @Bind(R.id.rl_right_img)
    RelativeLayout rlRightImg;
    @Bind(R.id.iv_top_shadow)
    ImageView ivTopShadow;
    @Bind(R.id.tv_detail2)
    TextView tvDetail2;
    @Bind(R.id.tv_image_count)
    TextView tvImageCount;
    private ImageCacheUtils imageCacheUtils;

    public static ImageCacheFragment newInstance() {
        ImageCacheFragment fragment = new ImageCacheFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ImageCacheFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_cache_fragment, container, false);
        ButterKnife.bind(this, rootView);
        needTitle();
        imageCacheUtils = ImageCacheUtils.getInstance();
        imageCacheUtils.mergeCommunityAB();//合并小区数据
        totalCommunityCount = imageCacheUtils.getCommunityIds().size();
        if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
            imageCacheUtils.getImageCacheResBeens().clear();//清除图片数据
            getPointData();
        }
        return rootView;
    }

    private int totalCommunityCount;

    private void setCommunityCount() {
        int tempCount = totalCommunityCount - imageCacheUtils.getCommunityIds().size();
        tvCommunityCount.setText("小区: " + tempCount + "/" + totalCommunityCount);
    }

    public void getPointData() {
        if (getActivity() == null) return;
        setCommunityCount();
        if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {

            String communityId = imageCacheUtils.getCommunityIds().get(0);
            final String[] initData = new String[]{mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME), communityId};

            PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
                @Override
                public void onSuccessDisplay(PointListModel data) {
                    if (data != null) {
                        if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                            String[] gate = data.getCGatePics().split("@&");
                            String[] pest = data.getCPestPics().split("@&");
                            for (int i = 0; i < gate.length; i++) {
                                if (!StringUtils.isEmpty(gate[i])) {
                                    ImageCacheResBean tempBean = new ImageCacheResBean(initData);
                                    tempBean.setUrl(gate[i]);
                                    imageCacheUtils.addPointBean(tempBean);
                                }
                            }
                            for (int i = 0; i < pest.length; i++) {
                                if (!StringUtils.isEmpty(pest[i])) {
                                    ImageCacheResBean tempBean = new ImageCacheResBean(initData);
                                    tempBean.setUrl(pest[i]);
                                    imageCacheUtils.addPointBean(tempBean);
                                }
                            }
                            List<PointListModel.NewListBean> tempList = data.getNewList();
                            for (PointListModel.NewListBean tempBean : tempList) {
                                if (!StringUtils.isEmpty(tempBean.getCDoorPic())) {
                                    ImageCacheResBean tempBean2 = new ImageCacheResBean(initData);
                                    tempBean2.setUrl(tempBean.getCDoorPic());
                                    imageCacheUtils.addPointBean(tempBean2);
                                }
                                List<PointListModel.NewListBean.PFileListBean> fileBeans = tempBean.getpFileList();
                                for (PointListModel.NewListBean.PFileListBean fileBean : fileBeans) {
                                    if (!StringUtils.isEmpty(fileBean.getFileUrlS())) {
                                        ImageCacheResBean fileBean2 = new ImageCacheResBean(initData);
                                        fileBean2.setUrl(fileBean.getFileUrlS());
                                        imageCacheUtils.addPointBean(fileBean2);
                                    }
                                }
                            }
                        }
                    }
                    if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
                        imageCacheUtils.getCommunityIds().remove(0);
                    }
                    getPointData();
                }

                @Override
                public void onFailDisplay(String errorMsg) {
                    if (imageCacheUtils.getCommunityIds() != null && imageCacheUtils.getCommunityIds().size() > 0) {
                        imageCacheUtils.getCommunityIds().remove(0);
                    }
                    getPointData();
                }
            });
            PointListParam param = new PointListParam();
            param.communityid = communityId;
            param.startime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_STARTTIME);
            param.endtime = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_INDEX_ENDTIME);
            param.space = "";
            param.customer = "";
            pointListController.getTaskIndex(param);

        } else {//点位数据加载完毕
            tvDetail2.setText("小区数据加载完毕,总共" + imageCacheUtils.getImageCacheResBeens().size() + "张图片需要缓存");
            tvDetail.setText("正在下载图片...");
            tvImageCount.setVisibility(View.VISIBLE);
            DownloadImgTask task = new DownloadImgTask();
            task.execute(imageCacheUtils.getImageCacheResBeens());


        }


    }


    public class DownloadImgTask extends AsyncTask<LinkedHashSet<ImageCacheResBean>,Integer,Boolean>{

        private int fileSize = 0;
        private int totalSize;
        private double totalData;

        private ImageCacheDaoHelper imageCacheDaoHelper;

        @Override
        protected void onPreExecute() {
            imageCacheDaoHelper = ImageCacheDaoHelper.getInstance();
        }

        @Override
        protected Boolean doInBackground(LinkedHashSet<ImageCacheResBean>... linkedHashSets) {
            if(linkedHashSets!=null && linkedHashSets.length>0){
                totalSize = fileSize = linkedHashSets[0].size();
                Iterator<ImageCacheResBean> iterator = linkedHashSets[0].iterator();
                while (iterator.hasNext()){
                    ImageCacheResBean bean = iterator.next();
                    System.out.println("url==>"+bean.getUrl());
                    int length = downloadFile(bean);
                    if(length>0){
                        System.out.println("bean==>"+bean);
                        bean.setCreateTime(TimeUtils.getNowStr());
                        imageCacheDaoHelper.addData(bean.returnBean(imageCacheDaoHelper));
                        publishProgress(length);
                    }
                }
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0]>0){
                fileSize--;
                int tempSize = totalSize-fileSize;
                tvImageCount.setText("图片: "+tempSize+"/"+totalSize);
                totalData += values[0];
                tv_total_data.setText("已下载容量: "+getDataContent(totalData));
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                tv_detail_url.setText("图片下载完成,可以关闭本页");
            }else{
                tv_detail_url.setText("图片下载失败,请重试!");
            }
        }
    }

    public static String getDataContent(double dataLength){
        long M = 1024*1024;
        if((int)(dataLength/M)>0){
            double mtemp = dataLength/M;
            return new DecimalFormat("#.00").format(mtemp)+" Mb";
        }else if((int)(dataLength/1024)>0){
            double ktemp = dataLength/1024;
            return new DecimalFormat("#.00").format(ktemp)+" Kb";
        }else{
            return (int)dataLength+" B";
        }
    }


    public static int downloadFile(ImageCacheResBean imageCacheResBean){
        String newFilename = generateFileName(imageCacheResBean.getUrl());
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(path);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }else{
            return -1;
        }

        if(!StringUtils.isEmpty(newFilename)){
            File file = new File(newFilename);
            //如果目标文件已经存在，则删除。产生覆盖旧文件的效果
            if(file.exists()){
                file.delete();
            }
            try {
                // 构造URL
                URL url = new URL(imageCacheResBean.getUrl());
                // 打开连接
                URLConnection con = url.openConnection();
                //获得文件的长度
                int contentLength = con.getContentLength();
                System.out.println("长度 :"+contentLength);
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                OutputStream os = new FileOutputStream(newFilename);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();
                imageCacheResBean.setPath(newFilename);
                return contentLength;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }else{
            return -1;
        }
    }

    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/cache/";

    public static String generateFileName(String url){
        if(url!=null){
            try{
                String result = url.substring(url.lastIndexOf("/")+1);
                return path+result;
            }catch (Exception e){
                return null;
            }
        }
        return null;
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
