package com.ahmettekin;

public class Album {

    private int albumId;
    private String isim;
    private int sarkiciID;

    public int getAlbumID() {
        return albumId;
    }

    public void setAlbumID(int albumId) {
        this.albumId = albumId;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public int getSarkiciID() {
        return sarkiciID;
    }

    public void setSarkiciID(int sarkiciID) {
        this.sarkiciID = sarkiciID;
    }
}
