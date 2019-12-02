package com.example.lumimonitor;

public class Select_Song {

    private String SongName;

    public Select_Song() {
    }

    public Select_Song(String songName) {
        this.SongName = songName;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }
}
