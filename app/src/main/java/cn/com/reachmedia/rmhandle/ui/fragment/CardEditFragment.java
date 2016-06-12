package cn.com.reachmedia.rmhandle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.ui.view.ProportionImageView;
import cn.com.reachmedia.rmhandle.utils.ApartmentPointUtils;
import cn.com.reachmedia.rmhandle.utils.StringUtils;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/6/12 下午1:33
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/6/12          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class CardEditFragment extends BaseToolbarFragment {

    @Bind(R.id.iv_gate_photo_1)
    ImageView ivGatePhoto1;
    @Bind(R.id.iv_gate_photo_2)
    ImageView ivGatePhoto2;
    @Bind(R.id.iv_pest_photo_1)
    ImageView ivPestPhoto1;
    @Bind(R.id.iv_pest_photo_2)
    ImageView ivPestPhoto2;
    ImageView[] gatePhotos;
    ImageView[] pestPhotos;
    @Bind(R.id.et_gate_card)
    EditText etGateCard;
    @Bind(R.id.et_password)
    EditText etPassword;
    private PointListModel model;

    public static CardEditFragment newInstance() {
        CardEditFragment fragment = new CardEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CardEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card_edit, container, false);
        ButterKnife.bind(this, rootView);
        gatePhotos = new ImageView[]{ivGatePhoto1,ivGatePhoto2};
        pestPhotos = new ImageView[]{ivPestPhoto1,ivPestPhoto2};
        needTitle();
        model = ApartmentPointUtils.getIns().pointListModel;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (model != null) {
            if (!StringUtils.isEmpty(model.getCommunity())) {
                setTitle(model.getCommunity());
            }

            etGateCard.setText(model.getDoordesc());
            etPassword.setText(model.getCarddesc());

            setCardPhoto(model.getCGatePic(),model.getCPestPic());



        }
    }

    private void setCardPhoto(String cGatePic,String cPestPic){
        String[] gate = cGatePic.split("@&");
        String[] pest = cPestPic.split("@&");

        for(int i=0;i<gate.length;i++){
            if(i<gatePhotos.length){
                if(!StringUtils.isEmpty(gate[i])){
                    Picasso.with(getContext()).load(gate[i]).placeholder(R.drawable.abc).into(gatePhotos[i]);
                }
            }
        }
        for(int i=0;i<pest.length;i++){
            if(i<pestPhotos.length){
                if(!StringUtils.isEmpty(pest[i])){
                    Picasso.with(getContext()).load(pest[i]).placeholder(R.drawable.abc).into(pestPhotos[i]);
                }
            }
        }

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
