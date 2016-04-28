package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.OfflineMapActivity;
import cn.com.reachmedia.rmhandle.ui.TaskInforActivity;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 上午11:41
 * Description: 客户及拍照要求
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CustomerPhotoTabFragment extends BaseFragment {
    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    TaskInforActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_customer_photo_list, container, false);
        ButterKnife.bind(this, rootView);
        activity = (TaskInforActivity)getActivity();

        Bundle args = getArguments();

        setUpViewComponent();

        return rootView;
    }

    private void setUpViewComponent() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }
}
