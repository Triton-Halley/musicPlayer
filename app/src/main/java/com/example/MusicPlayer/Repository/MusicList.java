package com.example.MusicPlayer.Repository;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.MusicPlayer.Model.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MusicList  {
    private Context mContext ;
    private static MusicList sMusicList ;
    public static MusicList getInstance(Context context){

        if (sMusicList == null){
            sMusicList=new MusicList(context);
        }
        return sMusicList ;
    }
    private MusicList(Context context) {
        mContext = context ;
    }
    private List<Music> mMusicList ;
    public List<Music>  getMusicList(){
        mMusicList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri musicsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(musicsUri,
                null,
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()){
            int musicTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int musicDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            // TODO
            Uri cover = Uri.parse(MediaMetadata.METADATA_KEY_ALBUM_ART_URI) ;

            do{
                String currentTitle = cursor.getString(musicTitle);
                String currentArtist = cursor.getString(musicArtist);
                String trackId = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long currentDuration = cursor.getLong(musicDuration);
                Music music = new Music(currentTitle,currentArtist,currentDuration, UUID.randomUUID());
                Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                        ,Long.parseLong(trackId));
                music.setMusicUri(musicUri);
//                music.setMusicUri(MediaStore.Audio.Media.getContentUri(currentTitle));
                mMusicList.add(music);
            }while (cursor.moveToNext());
        }
        return mMusicList ;
    }
}
