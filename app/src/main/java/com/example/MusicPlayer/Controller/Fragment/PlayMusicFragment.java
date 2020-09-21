package com.example.MusicPlayer.Controller.Fragment;

import android.graphics.PorterDuff;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.MusicPlayer.Model.Music;
import com.example.MusicPlayer.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Thread.sleep;


public class PlayMusicFragment extends Fragment {

    private Button play;
    private Button next;
    private Button previous;
    private Button shuffle;
    private Button repeatOne;
    private Button repeatAll;
    private SeekBar mSeekBar;
    private ImageView mImageView;
    private MediaPlayer mMediaPlayer;
    private Music mMusic;
    private int position;
    private List<Music> mMusicList;
    private int pauseCurrentPosition;
    private TextView mMusicName;
    private Handler mHandler = new Handler();

    public PlayMusicFragment() {
        // Required empty public constructor
    }


    @NotNull
    public static PlayMusicFragment newInstance(Music music, List<Music> musicList, int pos) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putParcelable("ARGS_MUSIC", music);
        args.putParcelableArrayList("ARGS_MUSIC_LIST", (ArrayList<? extends Parcelable>) musicList);
        args.putInt("ARGS_MUSIC_POS", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mMusic = (Music) getArguments().getParcelable("ARGS_MUSIC");
        mMusicList = getArguments().getParcelableArrayList("ARGS_MUSIC_LIST");
        position = getArguments().getInt("ARGS_MUSIC_POS");
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        findViews(view);
        mMusicName.setText(mMusic.getMusicName());

        setListener();

        listener();

        return view;
    }

    //    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startSeekBar() {
        mSeekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.seekBarColor),
                PorterDuff.Mode.MULTIPLY);
        mSeekBar.getThumb().setColorFilter(getResources().getColor(R.color.seekBarColor),
                PorterDuff.Mode.SRC_IN);
//        mSeekBar.getThumb().setLayoutDirection();

    }

    private void findViews(View view) {
        play = view.findViewById(R.id.play_pause);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);
        mMusicName = view.findViewById(R.id.music_name);
        mSeekBar = view.findViewById(R.id.music_Seekbar);
        mImageView = view.findViewById(R.id.circularImageView);
    }

    private void setListener() {
        play.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (mMediaPlayer == null) {
                    play.setBackgroundResource(R.drawable.baseline_pause_black_18);
                    mMediaPlayer = MediaPlayer.create(Objects.requireNonNull(getActivity()).getApplicationContext(), mMusic.getMusicUri());
                    Uri coveMusic = Uri.parse(MediaMetadata.METADATA_KEY_ALBUM_ART_URI);
                    mImageView.setImageURI(coveMusic);
                    mSeekBar.setMax(mMediaPlayer.getDuration());
                    mMediaPlayer.start();
                    updateSeekbar();
                    startSeekBar();
                    automaticPlay();
                    pauseCurrentPosition = mMediaPlayer.getCurrentPosition();

                } else if (!mMediaPlayer.isPlaying()) {
                    play.setBackgroundResource(R.drawable.baseline_pause_black_18);
                    mMediaPlayer.seekTo(pauseCurrentPosition);
                    mSeekBar.setMax(mMediaPlayer.getDuration());
                    mMediaPlayer.start();
                    updateSeekbar();
                    startSeekBar();
                    automaticPlay();
                } else if (mMediaPlayer.isPlaying()) {
                    mHandler.removeCallbacks(update);
                    play.setBackgroundResource(R.drawable.baseline_play_arrow_black_18);
                    pauseCurrentPosition = mMediaPlayer.getCurrentPosition();
                    mSeekBar.setMax(mMediaPlayer.getDuration());
                    mMediaPlayer.pause();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                mMediaPlayer.stop();
                mMediaPlayer.release();
                position = (position + 1) % mMusicList.size();
                Uri musicUri = mMusicList.get(position).getMusicUri();
                Uri coveMusic = Uri.parse(MediaMetadata.METADATA_KEY_ALBUM_ART_URI);
                mImageView.setImageURI(coveMusic);
                mMediaPlayer = MediaPlayer.create(Objects.requireNonNull(getActivity()).getApplicationContext(), musicUri);
                mMusicName.setText(mMusicList.get(position).getMusicName());
                mMediaPlayer.start();


            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                position = ((position - 1) < 0) ? (mMusicList.size() - 1) : (position - 1);
                Uri coveMusic = Uri.parse(MediaMetadata.METADATA_KEY_ALBUM_ART_URI);
                mImageView.setImageURI(coveMusic);
                Uri musicUri = mMusicList.get(position).getMusicUri();
                mMediaPlayer = MediaPlayer.create(Objects.requireNonNull(getActivity()).getApplicationContext(), musicUri);
                mMusicName.setText(mMusicList.get(position).getMusicName());
                mMediaPlayer.start();

            }
        });

    }

    /*private Music findNextMusic() {
        for (int i = 0; i < mMusicList.size(); i++) {
            if (mMusicList.get(i).getMusicId() == mMusic.getMusicId()) {
                if (i == mMusicList.size() - 1) {
                    i = 0;
                    return mMusicList.get(i);
                } else {
                    return mMusicList.get(i + 1);
                }

            } else if (i == mMusicList.size() - 1) {

            }
        }
        return mMusic;
    }*/
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
//            long currentDuration = mMediaPlayer.getCurrentPosition();
        }

    };

    private void updateSeekbar() {

        mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
        mHandler.postDelayed(update, 1000);



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setPlay() {
        position = (position + 1) % mMusicList.size();
        Uri musicUri = mMusicList.get(position).getMusicUri();
        mMediaPlayer = MediaPlayer.create(Objects.requireNonNull(getActivity()).getApplicationContext(), musicUri);
        mMusicName.setText(mMusicList.get(position).getMusicName());
        Uri coveMusic = Uri.parse(MediaMetadata.METADATA_KEY_ALBUM_ART_URI);
        mImageView.setImageURI(coveMusic);
        mMediaPlayer.start();
    }

    private void listener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void automaticPlay() {
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCompletion(MediaPlayer mp) {
                setPlay();
            }
        });
    }
}

