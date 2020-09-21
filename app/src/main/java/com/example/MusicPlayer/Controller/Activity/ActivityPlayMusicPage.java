package com.example.MusicPlayer.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.MusicPlayer.Controller.Fragment.PlayMusicFragment;
import com.example.MusicPlayer.Model.Music;
import com.example.MusicPlayer.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityPlayMusicPage extends AppCompatActivity {
    private static Music sMusic;
    public static Intent newIntent(Context context , Music music, List<Music> musicList,int position){
        Intent intent = new Intent(context,ActivityPlayMusicPage.class);
        intent.putParcelableArrayListExtra("EXTRA_MUSIC_LIST", (ArrayList<? extends Parcelable>) musicList);
        intent.putExtra("position",position);
        sMusic = music ;
        return intent ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music_page);
        Intent intent = getIntent();
        List<Music>musicList = intent.getParcelableArrayListExtra("EXTRA_MUSIC_LIST");
//        Music music = new Music(""," ",3);
        int position = intent.getIntExtra("position",0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.container_player, PlayMusicFragment.newInstance(sMusic,musicList,position))
                .commit();
    }

}