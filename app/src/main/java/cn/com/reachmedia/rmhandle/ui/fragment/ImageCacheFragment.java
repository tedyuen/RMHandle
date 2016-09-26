package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;

/**
 * Created by tedyuen on 16-9-26.
 */
public class ImageCacheFragment extends BaseToolbarFragment {

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

        return rootView;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
