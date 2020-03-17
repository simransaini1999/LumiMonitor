package com.example.lumimonitor;

public class Select_Song {

    private String SongName;
    private String SongState;
    private String CurrSongTime;
    private String SongLen;


    public Select_Song() {
    }

    public Select_Song(String songName,String songState){
        this.SongName = songName;
        this.SongState = songState;
    }

    public Select_Song(String songName, String songState, String currSongTime, String songLen) {
        this.SongName = songName;
        this.SongState = songState;
        this.CurrSongTime = currSongTime;
        this.SongLen = songLen;

    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        this.SongName = songName;
    }

    public String getSongState() {
        return SongState;
    }

    public void setSongState(String songState) {
        SongState = songState;
    }

    public String getCurrSongTime() {
        return CurrSongTime;
    }

    public void setCurrSongTime(String currSongTime) {
        CurrSongTime = currSongTime;
    }

    public String getSongLen() {
        return SongLen;
    }

    public void setSongLen(String songLen) {
        SongLen = songLen;
    }
}
