package cn.com.reachmedia.rmhandle.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.com.reachmedia.rmhandle.R;
import cn.com.reachmedia.rmhandle.app.App;
//import id.zelory.compressor.Compressor;

/**
 * Author:    tedyuen
 * Version    V1.0
 * Date:      16/5/9 下午7:05
 * Description: 图片操作工具
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/5/9          tedyuen             1.0             1.0
 * Why & What is modified:
 */
public class ImageUtils {
    public static List<Bitmap> photoBitmap;
    public static List<Bitmap> cacheBitmap;//提交了id，没有提交图片
    public static List<Bitmap> cacheLoaclBitmap;//id和图片都没提交
    public static List<String> cacheImgPath;//提交了id，没有提交图片
    public static List<String> cacheLocalImgPath;//id和图片都没提交
    public static Bitmap doorPhotoBitmap;

    private final static String SDCARD_MNT = "/mnt/sdcard";
    private final static String SDCARD = "/sdcard";

    public static final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    public static BitmapFactory.Options getBitmapOption(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;//图片高宽度都为原来的二分之一，即图片大小为原来的大小的四分之一
        options.inTempStorage = new byte[5*1024];
        return options;
    }
    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @param mUri
     * @return
     */
    public static String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        if(mUri==null){
            return filePath;
        }
        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre1 = "file://" + SDCARD + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        }
        return filePath;
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        if(uri==null){
            return "";
        }
        Cursor cursor = context.managedQuery(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }

    public static Bitmap getRoundBitmap(Bitmap bitmap){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPX = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outBitmap;
    }

    public static Bitmap getBitmapByPath(String path) {
        if (path == null)
            return null;
        Bitmap temp = null;
        if (temp == null) {
            try {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inSampleSize = 3;
                temp = compNew(BitmapFactory.decodeFile(path,options));
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    public static Bitmap getBitmapByPathNoComp(String path) throws Exception,Error{
        if (path == null)
            return null;
        Bitmap temp = null;
        if (temp == null) {
            temp = BitmapFactory.decodeFile(path);
        }
        return temp;
    }


    /**
     * 保存方法实现
     *
     * @param bitmap
     */
    public static String saveCompressPicPath(Bitmap bitmap,String path,String basePath) {
        if (PhotoSavePathUtil.checkSDCard()) {
            File savedir = new File(basePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        if (bitmap != null) {
            File file = new File(path);
            if (!file.exists()) {
                try {
                    @SuppressWarnings("unused")
                    boolean flag = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                    out.flush();
                    out.close();
                    return path;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存方法实现
     *
     * @param bitmap
     */
    private static File saveCompressPic(Bitmap bitmap,String path) {
        if (bitmap != null) {
            File file = new File(path);
            if (!file.exists()) {
                try {
                    @SuppressWarnings("unused")
                    boolean flag = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                    out.flush();
                    out.close();
                    return file;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 图片按质量压缩
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        image.recycle();
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片按比例压缩(大图)
     * @param image
     * @return
     */
    public static Bitmap compLocal(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>2048) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1920f;//这里设置高度为800f
        float ww = 1080f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap compNew(Bitmap image) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 40;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = null;
        try{
            bitmap = BitmapFactory.decodeStream(isBm, null, null);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 图片按比例压缩
     * @param image
     * @return
     */
    public static Bitmap comp(Bitmap image) throws Exception{
//        compressedImage = new Compressor.Builder(App.getIns().getContext())
//                .setMaxWidth(640)
//                .setMaxHeight(480)
//                .setQuality(75)
//                .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                .build()
//                .compressToFile(image);
//        long time1 = System.currentTimeMillis();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 50;
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
//        long time2 = System.currentTimeMillis();
//        System.out.println("  comp==> 1 "+( baos.toByteArray().length / 1024));
        if ( (baos.toByteArray().length / 1024)>500 ) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 10, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }

//        long time3 = System.currentTimeMillis();
//        System.out.println("  comp==> 2 "+(time3-time2));
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 4;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 4;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = null;
        try{
            bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }
        long time4 = System.currentTimeMillis();
//        System.out.println("  comp==> 3 "+(time4-time3));
//        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
        return bitmap;//压缩好比例大小后再进行质量压缩
    }


    public static byte[] readStream(InputStream inStream) throws Exception {
        System.gc();
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }




    /*    4.4以上获取图片      */
    //旧方法
    public static String selectImage(Context context, Intent data){
        Uri selectedImage = data.getData();
//      Log.e(TAG, selectedImage.toString());
        if(selectedImage!=null){
            String uriStr=selectedImage.toString();
            String path=uriStr.substring(10,uriStr.length());
            if(path.startsWith("com.sec.android.gallery3d")){
//                Log.e(TAG, "It's auto backup pic path:"+selectedImage.toString());
                return null;
            }
        }
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }



    //新方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static void testImageExifInfo(String filepath){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            String dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
            System.out.println("TAG_DATETIME: "+dateTime);
            String TAG_GPS_LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            System.out.println("TAG_GPS_LATITUDE: "+TAG_GPS_LATITUDE);

            String TAG_GPS_LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            System.out.println("TAG_GPS_LONGITUDE: "+TAG_GPS_LONGITUDE);

            String TAG_GPS_LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            System.out.println("TAG_GPS_LATITUDE_REF: "+TAG_GPS_LATITUDE_REF);

            String TAG_GPS_LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            System.out.println("TAG_GPS_LONGITUDE_REF: "+TAG_GPS_LONGITUDE_REF);


        }

    }

    /**
     * 获得图片旋转角度
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
//        testImageExifInfo(filepath);
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch(orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }

    /**
     * 设置图片旋转
     * @param bp
     * @return
     */
    public static Bitmap rotateBitMap(Bitmap bp,float rotate) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postRotate(rotate);
        Bitmap nowBp = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
                bp.getHeight(), matrix, true);
        if (bp.isRecycled()) {
            bp.recycle();
        }
        return nowBp;
    }


    public static String compressBmpFromBmp(String srcPath,String toPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 70;
//        BitmapFactory.Options newOpts = new BitmapFactory.Options();
//        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap image = BitmapFactory.decodeFile(srcPath, null);
//        System.out.println("srcPath:"+srcPath+":"+image);
        image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 400) {
//            System.out.println("baos:"+(baos.toByteArray().length / 1024)+"\toptions:"+options);
            baos.reset();
            options -= 30;
            if(options<=0){
                options = 5;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        try {
            File file = new File(toPath);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return toPath;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String getPointPicId(String workId,String pointId,String index,String userId){
        return workId + "_" + pointId + "_" + userId + "_" + index + "_" + pointId+"_"+Math.round(getRandomFour());
    }

    public static String getGatePicId(String community,String userId,int index){
        return community + "_" +  userId + "_" +index+ "_" +Math.round(getRandomFour());
    }



    public static double getRandomFour(){
        return Math.random()*9000+1000;
    }

    public static String getPointPicPath(String picId,String path){
        return path+picId+".jpg";
    }



    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 2;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }


//    /**
//     * 将图片按照某个角度进行旋转
//     *
//     * @param bm     需要旋转的图片
//     * @param degree 旋转角度
//     * @return 旋转后的图片
//     */
//    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
//        Bitmap returnBm = null;
//
//        // 根据旋转角度，生成旋转矩阵
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degree);
//        try {
//            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
//            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
//        } catch (OutOfMemoryError e) {
//        }
//        if (returnBm == null) {
//            returnBm = bm;
//        }
//        if (bm != returnBm) {
//            bm.recycle();
//        }
//        return returnBm;
//    }



    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }


    /**
     * 通过Uri获取文件
     * @param ac
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context ac, Uri uri) {
        if(uri.getScheme().toString().compareTo("content") == 0){
            ContentResolver cr = ac.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    return new File(filePath);
                }
            }
        }else if(uri.getScheme().toString().compareTo("file") == 0){
            return new File(uri.toString().replace("file://",""));
        }
        return null;
    }

    //============================= 图片水印 ==================================

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 绘制文字到右下角
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingRight
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text1,String text2,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(color);
        paint1.setTextSize(dp2px(context, size));
        Rect bounds1 = new Rect();
        paint1.getTextBounds(text1, 0, text1.length(), bounds1);
        paint1.setAntiAlias(true);//去除锯齿。
        paint1.setShadowLayer(5f, 5.0f, 5.0f, Color.BLACK);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));


        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(color);
        paint2.setTextSize(dp2px(context, size));
        Rect bounds2 = new Rect();
        paint2.getTextBounds(text2, 0, text2.length(), bounds2);
        paint2.setAntiAlias(true);
        paint2.setShadowLayer(5f, 5.0f, 5.0f, Color.BLACK);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        int x1 = bitmap.getWidth() - bounds1.width() - dp2px(context, paddingRight);
        int y1 = bitmap.getHeight() - dp2px(context, paddingBottom)-dp2px(context, 25);
        int x2 = bitmap.getWidth() - bounds2.width() - dp2px(context, paddingRight);
        int y2 = bitmap.getHeight() - dp2px(context, paddingBottom);
//        System.out.println("====>  "+x1+":"+y1+"\t"+x2+":"+y2);
        return drawTextToBitmapTwoLine(context, bitmap, text1,text2, paint1, bounds1,paint2, bounds2,
                x1,y1,x2,y2);
    }

    private static Bitmap drawTextToBitmapTwoLine(Context context, Bitmap bitmap, String text1,String text2,
                                           Paint paint1, Rect bounds1,
                                           Paint paint2, Rect bounds2,
                                                  int paddingLeft1, int paddingTop1,int paddingLeft2, int paddingTop2) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        paint1.setDither(true); // 获取跟清晰的图像采样
        paint1.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text1, paddingLeft1, paddingTop1, paint1);
        canvas.drawText(text2, paddingLeft2, paddingTop2, paint2);
        return bitmap;
    }

    /**
     * dip转pix
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
