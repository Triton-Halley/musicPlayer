package com.example.MusicPlayer.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

public class PictureUtils {

    public static Bitmap getScaledBitmap(Uri musicUri, Activity activity, int maxWidth, int maxHeight) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(activity.getApplicationContext(), musicUri);
        byte[] data = mmr.getEmbeddedPicture();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = maxWidth;
        options.outHeight = maxHeight;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }
}
