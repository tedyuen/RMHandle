package cn.com.reachmedia.rmhandle.ui.view.imagepager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.service.task.LocalImageAsyncTask;
import cn.com.reachmedia.rmhandle.utils.StringUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/22 下午2:12
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/22          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ImageMergePagerActivity extends Activity implements ViewPager.OnPageChangeListener {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String IMAGES = "images";
    private static final String IMAGES_LOCAL = "images_local";
    private static final String IMAGES_MERGE_FLAG = "images_merge_flag";

    private static final String IMAGE_POSITION = "image_index";

    HackyViewPager pager;
    PageIndicator mIndicator;
    private String[] typeName;
    private Bitmap mBitmap[];

    private final static int SUCCESS = 0;//保存图片成功
    private final static int FAILED = 1;//保存图片失败
    private int SelectImage; // 选中的图片

    public static List<Bitmap> imageLocal;
    public static List<String> imagePaths;

    // 拍照保存的绝对路径
    private final static String path = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "RMHandle/";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_pager);
        initImageLoader(this);

        Bundle bundle = getIntent().getExtras();
        String[] imageUrls = bundle.getStringArray(IMAGES);
        mBitmap = new Bitmap[imageUrls.length];

        boolean[] imageMergeFlag = bundle.getBooleanArray(IMAGES_MERGE_FLAG);
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


        pager.setAdapter(new ImagePagerAdapterLocalMerge(this,imageUrls,imageMergeFlag,imageLocal,imagePaths));


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


    /**
     * 合并图片
     */
    private class ImagePagerAdapterLocalMerge extends PagerAdapter {

        private LayoutInflater inflater;
        private Context mContext;
        ContentResolver resolver;
        private String[] images;
        boolean[] imageMergeFlag;//true 网络 false 本地
        List<Bitmap> imageLocal;
        List<String> imagePaths;

        ImagePagerAdapterLocalMerge(Context context,String[] images,boolean[] imageMergeFlag,List<Bitmap> imageLocal,List<String> imagePaths) {
            this.mContext = context;
            this.images = images;
            this.imageMergeFlag = imageMergeFlag;
            this.imageLocal = imageLocal;
            this.imagePaths = imagePaths;
            System.out.println("path=====3==> "+imagePaths);
            inflater = getLayoutInflater();
            resolver = context.getContentResolver();
        }

        @Override
        public Object instantiateItem(ViewGroup view, final int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image,
                    view, false);

            PhotoView imageView = (PhotoView) imageLayout
                    .findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout
                    .findViewById(R.id.loading);

            /**
             * 单击退出
             * */
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });

            if(imageMergeFlag[position]){
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
                                Toast.makeText(ImageMergePagerActivity.this, message,
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
            }else{
                System.out.println("path=====4==> "+imagePaths);

                if(imagePaths!=null && !StringUtils.isEmpty(imagePaths.get(position))){
                    LocalImageAsyncTask task = new LocalImageAsyncTask(imageView,false);
                    task.execute(imagePaths.get(position));
//                    Picasso.with(mContext).load(new File(imagePaths.get(position))).into(imageView);

                }else{
                    imageView.setImageBitmap(imageLocal.get(position));
                }
            }



            view.addView(imageLayout, 0);

            return imageLayout;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageMergeFlag.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
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




}
