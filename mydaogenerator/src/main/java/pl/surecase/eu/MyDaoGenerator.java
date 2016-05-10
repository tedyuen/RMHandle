package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "cn.com.reachmedia.rmhandle.bean");
        schema.setDefaultJavaPackageDao("cn.com.reachmedia.rmhandle.dao");

        initPointBean(schema);
        initPointWorkBean(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initPointBean(Schema schema){
        Entity pointBean = schema.addEntity("PointBean");//table name
        pointBean.addLongProperty("id").primaryKey().index().autoincrement();
        pointBean.addStringProperty("userId");
        pointBean.addStringProperty("workId");
        pointBean.addStringProperty("cid");
        pointBean.addStringProperty("cname");
        pointBean.addStringProperty("doorId");
        pointBean.addStringProperty("door");
        pointBean.addIntProperty("workUp");
        pointBean.addIntProperty("workDown");
        pointBean.addIntProperty("workDownPhone");
        pointBean.addIntProperty("workCheck");
        pointBean.addStringProperty("pointId");
        pointBean.addIntProperty("ground");
        pointBean.addStringProperty("cDoorPic");
        pointBean.addStringProperty("errorDesc");
        pointBean.addIntProperty("isPhoto");
        pointBean.addIntProperty("state");
        pointBean.addIntProperty("stateType");
        pointBean.addStringProperty("updateTime");
    }

    private static void initPointWorkBean(Schema schema){
        Entity pointWorkBean = schema.addEntity("PointWorkBean");//table name
        pointWorkBean.addLongProperty("id").primaryKey().index().autoincrement();
        pointWorkBean.addLongProperty("lastId");//point表id
        pointWorkBean.addStringProperty("userId");
        pointWorkBean.addStringProperty("workId");
        pointWorkBean.addStringProperty("pointId");
        pointWorkBean.addIntProperty("state");
        pointWorkBean.addIntProperty("repairType");
        pointWorkBean.addStringProperty("repairDesc");
        pointWorkBean.addIntProperty("errorType");
        pointWorkBean.addStringProperty("errorDesc");
        pointWorkBean.addStringProperty("lon");
        pointWorkBean.addStringProperty("lat");
        pointWorkBean.addStringProperty("workTime");
        pointWorkBean.addStringProperty("onlineTime");
        pointWorkBean.addStringProperty("nativeState");//0:未同步，1:已同步未同步图片，2:全部同步
        pointWorkBean.addIntProperty("fileCount");
        pointWorkBean.addStringProperty("file1");//文件路径123
        pointWorkBean.addStringProperty("file2");
        pointWorkBean.addStringProperty("file3");
    }




}
