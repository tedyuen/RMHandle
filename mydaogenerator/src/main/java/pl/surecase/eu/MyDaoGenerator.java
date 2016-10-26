package pl.surecase.eu;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    private static void testSpilt(){
        String str = "url1";

        System.out.println(str.split("@&")[0]);
    }

    private static String testMsg(String msg,String keyword,int keylength){
        String temp = msg.substring(msg.indexOf(keyword)+keyword.length());
//        String regEx="[A-Z,a-z,0-9]*";
//        String regEx="^[a-z0-9A-Z]+$";
        String regEx="^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";

        for(int i=0;i<temp.length()-keylength+1;i++){
            String subStr = temp.substring(i,i+keylength);
//            boolean flag = Pattern.compile(regEx).matcher(subStr).find();
            boolean flag = subStr.matches(regEx);
            System.out.println("substr: "+i+":"+flag+" : "+subStr);
            if(flag){
                return subStr;
            }
        }
        return null;

//        String msg = "本次获取的验证码，为(123Ab679)。请保存好不要丢失123ej";
//        String keyword = "";
//        int keylength = 8;
//        testMsg(msg,keyword,keylength);
    }

    private static void hashCodeTest(){
        String url1 = "abcefghjil";
        String url2 = "abcefghjil";
        String url3 = "abcefghjil1abcefghjil1abcefghjil1abcefghjil1";
        System.out.println(url1.hashCode());
        System.out.println(url2.hashCode());
        System.out.println(url3.hashCode());
    }

    public static String generateFileName(String url){
        if(url!=null){
            try{
                String result = url.substring(url.lastIndexOf("/")+1);
                return result;
            }catch (Exception e){
                return null;
            }
        }
        return null;
//        String url = "http://120.26.65.65:8085/img/res/images/40/t_20161009142318664.jpg";
//        System.out.println("=>filename:"+generateFileName(url));
    }

    public static String getDataContent(double dataLength){
        long M = 1024*1024;
        if((int)(dataLength/M)>0){
            double mtemp = dataLength/M;
            return new DecimalFormat("#.00").format(mtemp)+" Mb";
        }else if((int)(dataLength/1024)>0){
            double ktemp = dataLength/1024;
            return new DecimalFormat("#.00").format(ktemp)+" Kb";
        }else{
            return (int)dataLength+" B";
        }
//        System.out.println("==>"+getDataContent(53202321));
    }


    public static void main(String args[]) throws Exception {
        String msg = "尊敬的客户，您好！您将订购中国移动的天上西藏手机报业务，5元/月，请在24小时内回复'是'确认订购，回复其他内容和不回复则不订购。中国移动";
        String keyword = "请在24小时内回复";
        int keylength = 1;
        System.out.println(testMsg(msg,keyword,keylength));;

//        Schema schema = new Schema(3, "cn.com.reachmedia.rmhandle.bean");
//        schema.setDefaultJavaPackageDao("cn.com.reachmedia.rmhandle.dao");
//
//        initPointBean(schema);
//        initPointWorkBean(schema);
//        initCommDoorPicBean(schema);
//        initImageCacheBean(schema);
//        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initImageCacheBean(Schema schema){
        Entity imageCacheBean = schema.addEntity("ImageCacheBean");//table name
        imageCacheBean.addLongProperty("id").primaryKey().index().autoincrement();
        imageCacheBean.addStringProperty("url");
        imageCacheBean.addStringProperty("path");
        imageCacheBean.addDateProperty("start_time");
        imageCacheBean.addDateProperty("create_time");
        imageCacheBean.addLongProperty("index");
        imageCacheBean.addStringProperty("community_id");
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
        pointBean.addIntProperty("workUpPhone");
        pointBean.addIntProperty("workDown");
        pointBean.addIntProperty("workDownPhone");
        pointBean.addIntProperty("workCheck");
        pointBean.addStringProperty("pointId");
        pointBean.addIntProperty("ground");
        pointBean.addStringProperty("cDoorPic");
        pointBean.addStringProperty("errorDesc");
        pointBean.addStringProperty("stateTypeDesc");
        pointBean.addIntProperty("checkState");
        pointBean.addIntProperty("isPhoto");
        pointBean.addIntProperty("state");
        pointBean.addIntProperty("stateType");
        pointBean.addStringProperty("updateTime");
        pointBean.addStringProperty("pictime");
        pointBean.addStringProperty("worktime");
        pointBean.addDateProperty("starttime");
        pointBean.addDateProperty("endtime");
        pointBean.addStringProperty("communityid");
        pointBean.addStringProperty("communityname");
        pointBean.addStringProperty("fileId"); //文件标识 fileid1@#@fileid2
        pointBean.addStringProperty("fileUrlB");
        pointBean.addStringProperty("fileUrlS");
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
        pointWorkBean.addIntProperty("checkState");
        pointWorkBean.addStringProperty("errorDesc");
        pointWorkBean.addStringProperty("lon");
        pointWorkBean.addStringProperty("lat");
        pointWorkBean.addDateProperty("workTime");
        pointWorkBean.addStringProperty("onlineTime");
        pointWorkBean.addDateProperty("starttime");
        pointWorkBean.addStringProperty("communityid");
        pointWorkBean.addStringProperty("nativeState");//0:未同步，1:已同步未同步图片，2:全部同步
        pointWorkBean.addIntProperty("fileCount");
        pointWorkBean.addStringProperty("filedelete");//文件删除列表  321,372,111
        pointWorkBean.addStringProperty("fileXY");//文件xy  fileid1@#@fileid2
        pointWorkBean.addStringProperty("fileTime");//文件time  fileid1@#@fileid2
        pointWorkBean.addStringProperty("fileIdData");//文件标识  fileid1@#@fileid2
        pointWorkBean.addStringProperty("filePathData");//文件路径  filePath1@#@filePath2
        pointWorkBean.addStringProperty("doorpicid");//门洞照片标识
        pointWorkBean.addStringProperty("doorpic");//门洞照片
        pointWorkBean.addStringProperty("doorpicXY");//门洞坐标
        pointWorkBean.addStringProperty("doorpicTime");//门洞时间
        pointWorkBean.addStringProperty("communityname");
        pointWorkBean.addStringProperty("cname");


    }

    private static void initCommDoorPicBean(Schema schema){
        Entity commDoorPicBean = schema.addEntity("CommDoorPicBean");//table name
        commDoorPicBean.addLongProperty("id").primaryKey().index().autoincrement();
        commDoorPicBean.addStringProperty("userId");
        commDoorPicBean.addStringProperty("communityId");
        commDoorPicBean.addStringProperty("communityFile1");
        commDoorPicBean.addStringProperty("communityFileId1");
        commDoorPicBean.addStringProperty("communitySpace1");
        commDoorPicBean.addStringProperty("communitySpaceId1");
        commDoorPicBean.addStringProperty("communityFile2");
        commDoorPicBean.addStringProperty("communityFileId2");
        commDoorPicBean.addStringProperty("communitySpace2");
        commDoorPicBean.addStringProperty("communitySpaceId2");
        commDoorPicBean.addDateProperty("workTime");
        commDoorPicBean.addStringProperty("nativeState");//0:未同步，1:已同步未同步图片，2:全部同步
    }



}
