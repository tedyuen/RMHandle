package cn.com.reachmedia.rmhandle.ui.view.imagepager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.bean.PictureBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by tedyuen on 16-9-23.
 */
public class NewImageAllPagerActivity extends Activity implements ViewPager.OnPageChangeListener  {

    private static final String STATE_POSITION = "STATE_POSITION";
    private static final String IMAGE_POSITION = "image_index";

    public static List<PictureBean> resultDatas;


    HackyViewPager pager;
    PageIndicator mIndicator;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image_pager);

        Bundle bundle = getIntent().getExtras();
        int pagerPosition = bundle.getInt(IMAGE_POSITION, 0);

        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        pager = (HackyViewPager) findViewById(R.id.pager);


        pager.setAdapter(new ImagePagerAdapterLocalMerge(this));


        pager.setCurrentItem(pagerPosition);
//        SelectImage = pagerPosition;
        pager.addOnPageChangeListener(this);
        pager.setOffscreenPageLimit(4);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

    }


    private class ImagePagerAdapterLocalMerge extends PagerAdapter {

        private LayoutInflater inflater;
        private Context mContext;
        ContentResolver resolver;

        ImagePagerAdapterLocalMerge(Context context) {
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

            PictureBean bean = resultDatas.get(position);
            if(bean!=null){
                bean.displayImage(imageView);
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
            return resultDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

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
