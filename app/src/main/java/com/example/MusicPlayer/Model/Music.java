package com.example.MusicPlayer.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class Music implements Parcelable{
    private String musicName ;
    private String ArtistName ;
    private UUID musicId;
    private long duration ;
    private Uri musicUri ;


    protected Music(Parcel in) {
        musicName = in.readString();
        ArtistName = in.readString();
        duration = in.readLong();
        musicUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public UUID getMusicId() {
        return musicId;
    }

    public void setMusicId(UUID musicId) {
        this.musicId = musicId;
    }

    // pic


    public Uri getMusicUri() {
        return musicUri;
    }

    public void setMusicUri(Uri musicUri) {
        this.musicUri = musicUri;
    }

    public Music(String musicName, String artistName, long duration,UUID musicId) {
        this.musicName = musicName;
        ArtistName = artistName;
        this.duration = duration;
        this.musicId = musicId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicName);
        dest.writeString(ArtistName);
        dest.writeLong(duration);
        dest.writeParcelable(musicUri, flags);
    }
}
