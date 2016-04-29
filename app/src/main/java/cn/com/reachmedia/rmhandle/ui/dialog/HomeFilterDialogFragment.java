package cn.com.reachmedia.rmhandle.ui.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/29 下午3:37
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/29          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class HomeFilterDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
////        layoutParams.width = dm.widthPixels;
////        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_four_items);
//        layoutParams.gravity = Gravity.TOP;
//        getDialog().getWindow().setAttributes(layoutParams);
        getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_fragment_home_filter, container);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
