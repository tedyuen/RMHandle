package cn.com.reachmedia.rmhandle.utils.album.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.FileNotFoundException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
import cn.com.reachmedia.rmhandle.ui.base.BaseToolbarFragment;
import cn.com.reachmedia.rmhandle.utils.ImgCallBack;
import cn.com.reachmedia.rmhandle.utils.ToastHelper;
import cn.com.reachmedia.rmhandle.utils.album.FileTraversal;
import cn.com.reachmedia.rmhandle.utils.album.Util;
import cn.com.reachmedia.rmhandle.utils.album.adapter.ImgsAdapter;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/17 下午5:41
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/17          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ImgsFragment extends BaseToolbarFragment{

    private static final int INITIAL_DELAY_MILLIS = 300;

    FileTraversal fileTraversal;
    @Bind(R.id.gridView1)
    GridView imgGridView;
    ImgsAdapter imgsAdapter;
    @Bind(R.id.selected_image_layout)
    LinearLayout select_layout;
    Util util;
    HashMap<Integer, ImageView> hashImage;
    @Bind(R.id.button3)
    Button choise_button;
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    public int count;
//    ArrayList<String> filelist;

    public static ImgsFragment newInstance(FileTraversal fileTraversal, int count){
        ImgsFragment fragment = new ImgsFragment();
        Bundle args = new Bundle();
        args.putParcelable("data",fileTraversal);
        args.putInt("count",count);
        fragment.setArguments(args);
        return fragment;
    }

    public ImgsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileTraversal = getArguments().getParcelable("data");
            count = getArguments().getInt("count");
        }
        if(fileTraversal==null){
            fileTraversal = Util.localFile;
        }
        App.getIns().addAlbumActivity(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photogrally,container,false);
        ButterKnife.bind(this, rootView);
        needTitle();
        Util.filelist.clear();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpListView();
    }

    private void setUpListView(){
        imgsAdapter=new ImgsAdapter(getContext(), fileTraversal.filecontent,onItemClickClass,count,this);

//        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(imgsAdapter);
//        swingBottomInAnimationAdapter.setAbsListView(imgGridView);
//
//        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
//        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        imgGridView.setAdapter(imgsAdapter);
        hashImage=new HashMap<>();
//        filelist=new ArrayList<>();
        util=new Util(getContext());
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }


    class BottomImgIcon implements AdapterView.OnItemClickListener {

        int index;
        public BottomImgIcon(int index) {
            this.index=index;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {

        }
    }

    @SuppressLint("NewApi")
    public ImageView iconImage(String filepath,int index,CheckBox checkBox) throws FileNotFoundException {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(relativeLayout2.getMeasuredHeight(), relativeLayout2.getMeasuredHeight());
        ImageView imageView=new ImageView(getContext());
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.abc);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        float alpha=100;
        imageView.setAlpha(1.0f);
        util.imgExcute(imageView, imgCallBack, filepath);
        imageView.setOnClickListener(new ImgOnclick(filepath,checkBox));
        return imageView;
    }

    ImgCallBack imgCallBack=new ImgCallBack() {
        @Override
        public void resultImgCall(ImageView imageView, Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    };

    class ImgOnclick implements View.OnClickListener {
        String filepath;
        CheckBox checkBox;
        public ImgOnclick(String filepath,CheckBox checkBox) {
            this.filepath=filepath;
            this.checkBox=checkBox;
        }
        @Override
        public void onClick(View arg0) {
            checkBox.setChecked(false);
            select_layout.removeView(arg0);
            choise_button.setText("确定("+select_layout.getChildCount()+")");
            Util.filelist.remove(filepath);
        }
    }

    ImgsAdapter.OnItemClickClass onItemClickClass=new ImgsAdapter.OnItemClickClass() {
        @Override
        public void OnItemClick(View v, int Position, CheckBox checkBox) {
            String filapath=fileTraversal.filecontent.get(Position)[0];
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                select_layout.removeView(hashImage.get(Position));
                Util.filelist.remove(filapath);
                choise_button.setText("确定("+select_layout.getChildCount()+")");
            }else {
                if(count<=Util.filelist.size()){
                    ToastHelper.showAlert(getActivity(), "最多上传4张照片");
                }else {
                    try {
                        checkBox.setChecked(true);
                        Log.i("img", "img choise position->" + Position);
                        ImageView imageView = iconImage(filapath, Position, checkBox);
                        if (imageView != null) {
                            hashImage.put(Position, imageView);
                            Util.filelist.add(filapath);
                            select_layout.addView(imageView);
                            choise_button.setText("确定(" + select_layout.getChildCount() + ")");
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @OnClick(R.id.button3)
    public void enter(){
        getActivity().setResult(getActivity().RESULT_OK, null);
        getActivity().finish();
    }

    /**
     * FIXME
     * 亲只需要在这个方法把选中的文档目录已list的形式传过去即可
     * @param view
     */
    public void sendfiles(View view){
//        Intent intent =new Intent(this, MainActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putStringArrayList("files", filelist);
//        intent.putExtras(bundle);
//        startActivity(intent);

    }
}
