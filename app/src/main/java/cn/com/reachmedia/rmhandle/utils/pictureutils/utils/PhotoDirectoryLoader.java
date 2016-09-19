package cn.com.reachmedia.rmhandle.utils.pictureutils.utils;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import static android.provider.MediaStore.MediaColumns.MIME_TYPE;

/**
 * Created by tedyuen on 16-9-19.
 */
public class PhotoDirectoryLoader  extends CursorLoader {

    final String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
    };

    public PhotoDirectoryLoader(Context context) {
        super(context);

        setProjection(IMAGE_PROJECTION);
        setUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setSortOrder(MediaStore.Images.Media.DATE_ADDED +  " DESC");

        setSelection(MIME_TYPE + "=? or " + MIME_TYPE + "=?");
        setSelectionArgs(new String[] { "image/jpeg", "image/png" });
    }


    private PhotoDirectoryLoader(Context context, Uri uri, String[] projection, String selection,
                                 String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
