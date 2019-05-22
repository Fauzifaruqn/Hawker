package com.example.learnmaps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Kategori implements Parcelable {

    private String title;
    private String kategori_id;


    public Kategori(String title, String kategori_id) {
        this.title = title;
        this.kategori_id = kategori_id;
    }

    public Kategori() {

    }

    protected Kategori(Parcel in) {
        title = in.readString();
        kategori_id = in.readString();
    }

    public static final Creator<Kategori> CREATOR = new Creator<Kategori>() {
        @Override
        public Kategori createFromParcel(Parcel in) {
            return new Kategori(in);
        }

        @Override
        public Kategori[] newArray(int size) {
            return new Kategori[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(String kategori_id) {
        this.kategori_id = kategori_id;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "title='" + title + '\'' +
                ", kategori_id='" + kategori_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(kategori_id);
    }
}