package cn.com.reachmedia.rmhandle.utils.pictureutils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.com.reachmedia.rmhandle.utils.pictureutils.ui.PhotoPickerActivity;


/**
 * Created by tedyuen on 16-9-18.
 */
public class PhotoPickerIntent extends Intent {

    private PhotoPickerIntent() {
    }

    private PhotoPickerIntent(Intent o) {
        super(o);
    }

    private PhotoPickerIntent(String action) {
        super(action);
    }

    private PhotoPickerIntent(String action, Uri uri) {
        super(action, uri);
    }

    private PhotoPickerIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public PhotoPickerIntent(Context packageContext) {
        super(packageContext, PhotoPickerActivity.class);
    }

    public void setPhotoCount(int photoCount) {
        this.putExtra(PhotoPickerActivity.EXTRA_MAX_COUNT, photoCount);
    }


    public void setShowCamera(boolean showCamera) {
        this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
    }
}
