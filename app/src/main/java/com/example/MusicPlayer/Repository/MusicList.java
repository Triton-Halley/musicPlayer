package com.example.MusicPlayer.Repository;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.example.MusicPlayer.Model.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MusicList  {
    private Context mContext ;
    @SuppressLint("StaticFieldLeak")
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    public List<Music>  getMusicList(){
        List<Music> musicList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri musicsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(musicsUri,
                null,
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()){
            int musicTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int musicDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            // TODO

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
                musicList.add(music);
            }while (cursor.moveToNext());
        }
        return musicList;
    }
    public List<String> getArtistList(){
         List<String> musicArtists = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri musicsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(musicsUri,
                null,
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()){
            int musicArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            // TODO

            do{
                String currentArtist = cursor.getString(musicArtist);
                musicArtists.add(currentArtist);
            }while (cursor.moveToNext());
        }
        return musicArtists;
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    public List<String> getAlbums(){
        List<String> musicAlbums = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri musicsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(musicsUri,
                null,
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()){
            int musicArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            // TODO

            do{
                String currentAlbum = cursor.getString(musicArtist);
                musicAlbums.add(currentAlbum);
            }while (cursor.moveToNext());
        }
        return musicAlbums;
    }
}
