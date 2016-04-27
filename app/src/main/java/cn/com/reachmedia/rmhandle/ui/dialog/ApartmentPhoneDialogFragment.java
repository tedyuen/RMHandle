package cn.com.reachmedia.rmhandle.ui.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.adapter.DialogApartmentPhoneAdapter;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/27 下午4:14
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/27          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ApartmentPhoneDialogFragment extends SupportBlurDialogFragment {

    @Bind(R.id.lv_list)
    ListView lvList;

    private DialogApartmentPhoneAdapter mAdapter;

    @Override
    public void onStart() {
        super.onStart();
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
//        layoutParams.width = dm.widthPixels;
//        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_four_items);
        layoutParams.gravity = Gravity.CENTER;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_fragment_apartment_phone, container);
        ButterKnife.bind(this, view);
        mAdapter = new DialogApartmentPhoneAdapter(getActivity());
        lvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected float getDownScaleFactor() {
        // Allow to customize the down scale factor.
        return 8.0f;
    }

    @Override
    protected int getBlurRadius() {
        // Allow to customize the blur radius factor.
        return 7;
    }

}
