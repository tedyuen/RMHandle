package cn.com.reachmedia.rmhandle.test;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.AppApiContact;
import cn.com.reachmedia.rmhandle.bean.PointBean;
import cn.com.reachmedia.rmhandle.dao.PointBeanDao;
import cn.com.reachmedia.rmhandle.dao.PointWorkBeanDao;
import cn.com.reachmedia.rmhandle.db.DatabaseLoader;
import cn.com.reachmedia.rmhandle.db.helper.PointBeanDaoHelper;
import cn.com.reachmedia.rmhandle.model.PointListModel;
import cn.com.reachmedia.rmhandle.model.param.PointListParam;
import cn.com.reachmedia.rmhandle.network.callback.UiDisplayListener;
import cn.com.reachmedia.rmhandle.network.controller.PointListController;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/11 上午10:13
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/11          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class DBTestActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_query)
    public void queryData(){
        PointListController pointListController = new PointListController(new UiDisplayListener<PointListModel>() {
            @Override
            public void onSuccessDisplay(PointListModel data) {
                if (data != null) {
                    if (AppApiContact.ErrorCode.SUCCESS.equals(data.rescode)) {
                        List<PointListModel.NewListBean> newList = data.getNewList();
                        PointBeanDaoHelper pointBeanDaoHelper = PointBeanDaoHelper.getInstance();
                        pointBeanDaoHelper.deleteAll();
                        for(PointListModel.NewListBean tempBean:newList){
                            PointBean bean = tempBean.toBean("abc","","","");
                            pointBeanDaoHelper.addData(bean);
                        }

                    }
                }
            }

            @Override
            public void onFailDisplay(String errorMsg) {

            }
        });

        PointListParam param = new PointListParam();
        param.communityid = "663";
        param.startime = "2016-05-05";
        param.endtime = "2016-05-11";
        param.space = "";
        param.customer = "";
        pointListController.getTaskIndex(param);

    }

    @OnClick(R.id.bt_select)
    public void selectData(){
        PointBeanDaoHelper pointBeanDaoHelper = PointBeanDaoHelper.getInstance();
        List<PointBean> list = pointBeanDaoHelper.getAllData();
        for(PointBean bean:list){
            System.out.println(bean.toString());
        }

    }


    @OnClick(R.id.bt_select2)
    public void selectData2(){
        String temp = PointBeanDao.TABLENAME + PointWorkBeanDao.TABLENAME;


        String sql = "select *,(select b.STATE from POINT_WORK_BEAN b where a.WORK_ID=b.WORK_ID and a.POINT_ID=b.POINT_ID ) bstate,(select case when b.STATE > a.STATE then b.STATE else a.STATE end from POINT_BEAN a,POINT_WORK_BEAN b) rstate from POINT_BEAN a";


//        String sql2 = "if not exists (select ";


        Cursor cursor = DatabaseLoader.getDaoSession().getDatabase().rawQuery(sql,null);
        for(int i=0;i<cursor.getColumnCount();i++){
            System.out.println(i+":"+cursor.getColumnName(i));
        }
        while (cursor.moveToNext()) {


            StringBuffer buffer = new StringBuffer();
            buffer.append("workid:"+cursor.getString(cursor.getColumnIndex("WORK_ID")));
            buffer.append("\t");
            buffer.append("pointid:"+cursor.getString(cursor.getColumnIndex("POINT_ID")));
            buffer.append("\t");
            buffer.append("cname:"+cursor.getString(cursor.getColumnIndex("CNAME")));
            buffer.append("\t");
            buffer.append("A state:"+cursor.getString(cursor.getColumnIndex("STATE")));
            buffer.append("\t");
            buffer.append("B state:"+cursor.getString(cursor.getColumnIndex("bstate")));
            buffer.append("\t");
            buffer.append("Result state:"+cursor.getString(cursor.getColumnIndex("rstate")));


            System.out.println(buffer.toString());
        }

    }
}
