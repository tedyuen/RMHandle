package cn.com.reachmedia.rmhandle.ui.view.imagepager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by tedyuen on 16-10-13.
 */
public class PictureImagePagerActivity extends Activity implements ViewPager.OnPageChangeListener {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String IMAGES = "images";

    private static final String IMAGE_POSITION = "image_index";

    public static List<PictureBean> imageData;

    HackyViewPager pager;
    PageIndicator mIndicator;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_pager);
        initImageLoader(this);

        Bundle bundle = getIntent().getExtras();

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


        pager.setAdapter(new ImagePagerAdapter(this));


        pager.setCurrentItem(pagerPosition);
//        SelectImage = pagerPosition;
        pager.addOnPageChangeListener(this);
        pager.setOffscreenPageLimit(3);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
    }

    private class ImagePagerAdapter extends PagerAdapter{

        private LayoutInflater inflater;
        private Context mContext;
        ContentResolver resolver;

        ImagePagerAdapter(Context context){
            this.mContext = context;

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

            PictureBean bean = imageData.get(position);
            if(bean!=null){
                bean.displayImage(imageView,true);
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
            return imageData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
