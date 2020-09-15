package com.example.MusicPlayer.Model;

import java.io.Serializable;

public class Music implements Serializable {
    private String musicName ;
    private String ArtistName ;
    // pic
    // time

    public Music(String musicName, String artistName) {
        this.musicName = musicName;
        ArtistName = artistName;
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
}
