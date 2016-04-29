package cn.com.reachmedia.rmhandle.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.ui.recycleview.SpaceItemDecoration;


/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/4/28 下午4:39
 * Description: RecyclerView 测试
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/4/28          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class TestRecyclerViewActivity extends AppCompatActivity {


    @Bind(R.id.id_recyclerview)
    RecyclerView idRecyclerview;

    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_test);
        ButterKnife.bind(this);

        idRecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_space);
        idRecyclerview.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        idRecyclerview.setAdapter(mAdapter = new HomeAdapter());
        idRecyclerview.setNestedScrollingEnabled(false);
    }



    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    TestRecyclerViewActivity.this).inflate(R.layout.item_recyclerview_test, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (ImageView) view.findViewById(R.id.tv_test_img);
            }
        }
    }

}
