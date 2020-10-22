package com.example.MusicPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

public class MusicCover<T> extends HandlerThread {

    public static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    private Context mContext;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private ConcurrentHashMap<T, Uri> mTargetUrl = new ConcurrentHashMap<>();
    private MusicCoverListener mListener;

    public MusicCoverListener getListener() {
        return mListener;
    }

    public void setListener(MusicCoverListener listener) {
        mListener = listener;
    }

    public MusicCover(Handler responseHandler, Context context) {
        super(TAG);
        mContext =context;
        mResponseHandler = responseHandler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        mRequestHandler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                //download the image from flicker. url is in msg
                if (msg.what == MESSAGE_DOWNLOAD) {
                    final T target = (T) msg.obj;
                    final Uri photoUri = mTargetUrl.get(target);

                    if (photoUri == null)
                        return;
                    mmr.setDataSource(mContext,photoUri);
                    byte[] data = mmr.getEmbeddedPicture();
                    if (data == null)
                        return;
                    final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
/*
                        byte[] photoBytes = new FlickrFetcher().getBytes(photoUri);
                        final Bitmap bitmap = BitmapFactory
                                .decodeByteArray(photoBytes, 0, photoBytes.length);
*/

                    mResponseHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mTargetUrl.get(target) != photoUri)
                                return;

                            mListener.onDownloadCompleted(target, bitmap);
                        }
                    });
                }
            }
        };
    }

    public void queueThumbnailMessage(T target, Uri musicUri) {
        if (musicUri == null) {
            mTargetUrl.remove(target);
        } else {
            mTargetUrl.put(target, musicUri);

            //1. create a message with a handler (what, obj, target)
            //2. send message to queue
            Message message = mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target);
            message.sendToTarget();
        }
    }

    public interface MusicCoverListener<T>{
        void onDownloadCompleted(T target, Bitmap bitmap);
    }
}
