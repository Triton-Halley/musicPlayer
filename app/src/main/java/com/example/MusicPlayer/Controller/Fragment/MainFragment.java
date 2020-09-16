package com.example.MusicPlayer.Controller.Fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MusicPlayer.R;

import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerView mRecyclerView ;
    private MediaPlayer mMediaPlayer;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String tabs) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view ;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getMusicList(){
        ContentResolver contentResolver = Objects.requireNonNull(getActivity()).getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(musicUri,
                null,
                null,
                null,
                null);
        if (cursor!=null && cursor.moveToFirst()){
            int musicTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int musicDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int musicCover = cursor.getColumnIndex(MediaMetadata.METADATA_KEY_ALBUM_ART_URI);

            do{
                String currentTitle = cursor.getString(musicTitle);
                String currentArtist = cursor.getString(musicArtist);
                long currentDuration = cursor.getLong(musicDuration);
            }while (cursor.moveToNext());
        }

    }
}