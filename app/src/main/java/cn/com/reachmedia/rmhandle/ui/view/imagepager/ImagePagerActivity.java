package cn.com.reachmedia.rmhandle.ui.view.imagepager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.utils.ImageUtils;
import cn.com.reachmedia.rmhandle.utils.LogUtils;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 下午6:59
 * Description: 轮播大图
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ImagePagerActivity extends Activity implements ViewPager.OnPageChangeListener{

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private static final String STATE_POSITION = "STATE_POSITION";

    private static final String IMAGES = "images";
    private static final String IMAGES_LOCAL = "imagesLocal";

    private static final String IMAGE_POSITION = "image_index";

    HackyViewPager pager;
    PageIndicator mIndicator;
    private String[] typeName;
    // 拍照保存的绝对路径
    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";
    //    private String portrait_path;
    private ProgressDialog mSaveDialog = null;
    private String mSaveMessage;
    private Bitmap mBitmap[];
    private String mFileName;
    private Thread saveThread;
    private final static int SUCCESS = 0;//保存图片成功
    private final static int FAILED = 1;//保存图片失败
    private int SelectImage; // 选中的图片

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_pager);

        initImageLoader(this);

        Bundle bundle = getIntent().getExtras();
        String[] imageUrls = bundle.getStringArray(IMAGES);


        int pagerPosition = bundle.getInt(IMAGE_POSITION, 0);

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        pager = (HackyViewPager) findViewById(R.id.pager);
        if(imageUrls==null){
            pager.setAdapter(new ImagePagerAdapterLocal(this));
            pager.setOffscreenPageLimit(4);
        }else{
            mBitmap = new Bitmap[imageUrls.length];
            pager.setAdapter(new ImagePagerAdapter(imageUrls, this));
        }
        pager.setCurrentItem(pagerPosition);
        SelectImage = pagerPosition;
        pager.addOnPageChangeListener(this);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);




    }


    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }

    private class ImagePagerAdapter extends PagerAdapter {

        private String[] images;
        private LayoutInflater inflater;
        private Context mContext;

        ImagePagerAdapter(String[] images, Context context) {
            this.images = images;
            this.mContext = context;
            inflater = getLayoutInflater();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);

        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image,
                    view, false);

            final PhotoView imageView = (PhotoView) imageLayout
                    .findViewById(R.id.image);

            /**
             * 长按保存
             * */
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LogUtils.d("TAG","长按保存");
                    typeName = getResources().getStringArray(R.array.save);
                    new MaterialDialog.Builder(mContext)
                            .items(typeName)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                    if(getResources().getString(R.string.dialog_exit_save).equals(text.toString())){
                                        mFileName = "wj_photo_" +System.currentTimeMillis()+ ".jpg";
                                        mSaveDialog = ProgressDialog.show(ImagePagerActivity.this,"保存图片", "图片正在保存，请稍后...", true);
                                        saveThread = new Thread(saveFileRunnable);
                                        saveThread.start();
                                    }else {
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .show();
                    return false;
                }
            });
            /**
             * 单击退出
             * */
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            final ProgressBar spinner = (ProgressBar) imageLayout
                    .findViewById(R.id.loading);

            imageLoader.displayImage(images[position], imageView, options,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            spinner.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view,
                                                    FailReason failReason) {
                            String message = null;
                            switch (failReason.getType()) {
                                case IO_ERROR:
                                    message = "Input/Output error";
                                    break;
                                case DECODING_ERROR:
                                    message = "Image can't be decoded";
                                    break;
                                case NETWORK_DENIED:
                                    message = "Downloads are denied";
                                    break;
                                case OUT_OF_MEMORY:
                                    message = "Out Of Memory error";
                                    break;
                                case UNKNOWN:
                                    message = "Unknown error";
                                    break;
                            }
                            Toast.makeText(ImagePagerActivity.this, message,
                                    Toast.LENGTH_SHORT).show();

                            spinner.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri,
                                                      View view, Bitmap loadedImage) {
                            spinner.setVisibility(View.GONE);
                            mBitmap[position] = loadedImage;
                        }
                    });

            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }
    }


    private class ImagePagerAdapterLocal extends PagerAdapter {

        private LayoutInflater inflater;
        private Context mContext;
        ContentResolver resolver;

        ImagePagerAdapterLocal(Context context) {
            this.mContext = context;

            inflater = getLayoutInflater();
            resolver = context.getContentResolver();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public void finishUpdate(View container) {
        }

        @Override
        public int getCount() {
            return ImageUtils.photoBitmap.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image,
                    view, false);

            PhotoView imageView = (PhotoView) imageLayout
                    .findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout
                    .findViewById(R.id.loading);
            imageView.setImageBitmap(ImageUtils.photoBitmap.get(position));
            ((ViewPager) view).addView(imageLayout, 0);
            /**
             * 单击退出
             * */
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View container) {
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SelectImage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private Runnable saveFileRunnable = new Runnable() {

        @Override
        public void run() {
            try {
                LogUtils.d("TAG","=====~~~~选中的下标"+SelectImage);
                saveFile(mBitmap[SelectImage], mFileName);
                mSaveMessage = "图片保存成功！";
                messageHandler.sendEmptyMessage(SUCCESS);
            } catch (Exception e) {
                mSaveMessage = "图片保存失败！";
                messageHandler.sendEmptyMessage(FAILED);
                e.printStackTrace();
            }
//                        messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };


    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSaveDialog.dismiss();
            LogUtils.d("TAG", mSaveMessage);
            switch (msg.what) {
                case SUCCESS:
                    ToastHelper.showInfo(ImagePagerActivity.this, mSaveMessage);
                    break;
                case FAILED:
                    ToastHelper.showAlert(ImagePagerActivity.this, mSaveMessage);
                    break;
            }
        }
//        ToastHelper.showInfo(ImagePagerActivity.this, mSaveMessage);
    };
    /**
     * 保存文件
     */
    protected void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        LogUtils.d("TAG",myCaptureFile+"");
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        //通知系统有图片要更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        sendBroadcast(intent);//
    }

}
