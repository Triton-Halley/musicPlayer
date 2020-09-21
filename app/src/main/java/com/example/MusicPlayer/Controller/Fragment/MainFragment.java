package com.example.MusicPlayer.Controller.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.MusicPlayer.Controller.Activity.ActivityMainPage;
import com.example.MusicPlayer.Controller.Activity.ActivityPlayMusicPage;
import com.example.MusicPlayer.Model.Music;
import com.example.MusicPlayer.R;
import com.example.MusicPlayer.Repository.MusicList;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    private RecyclerView mRecyclerView ;
    private List<Music> mMusicList ;
    private MusicAdapter mAdapter;
    private MusicList mRepository ;
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
        mRepository = MusicList.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        findViews(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view ;
    }
    private void findViews(View view){
        mRecyclerView = view.findViewById(R.id.recyclerview);
    }
    private void getMusicList(){
        mMusicList =mRepository.getMusicList();
    }
    private void updateUI(){
        getMusicList();
        List<Music> musicList = mMusicList;
        if (mAdapter == null){
            mAdapter = new MusicAdapter(musicList);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setMusics(musicList);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
    class musicHolder extends RecyclerView.ViewHolder{
        private Music mMusic ;
        private int position;
        private TextView mMusicTitle;
        private TextView mMusicArtist;
        public musicHolder(@NonNull View itemView) {
            super(itemView);
            mMusicTitle = itemView.findViewById(R.id.music_title);
            mMusicArtist = itemView.findViewById(R.id.Artist_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = ActivityPlayMusicPage.newIntent(getActivity(),mMusic,mMusicList,position);
                    startActivity(intent);
                }
            });
        }
        public void bindMusic(Music music,int pos){
            mMusic = music ;
            position = pos ;
            mMusicTitle.setText(music.getMusicName());
            mMusicArtist.setText(music.getArtistName());
        }
    }
    class MusicAdapter extends RecyclerView.Adapter<musicHolder>{
        List<Music> mMusics ;

        public List<Music> getMusics() {
            return mMusics;
        }

        public void setMusics(List<Music> musics) {
            mMusics = musics;
        }

        public MusicAdapter(List<Music> musics) {
            mMusics = musics;
        }

        @NonNull
        @Override
        public musicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.music_task,parent,false);
            return new musicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull musicHolder holder, int position) {
            Music music = mMusics.get(position);
            holder.bindMusic(music,position);
        }

        @Override
        public int getItemCount() {
            return mMusics.size();
        }
    }
}