package com.example.learnmaps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Detail implements Parcelable {

    private String user_id;
    private String deskripsi;
    private String no_hp;
    private String detail_id;

    public Detail(String user_id, String deskripsi, String no_hp, String detail_id) {
        this.user_id = user_id;
        this.deskripsi = deskripsi;
        this.no_hp = no_hp;
        this.detail_id = detail_id;
    }

    public Detail() {

    }

    protected Detail(Parcel in) {
        user_id = in.readString();
        deskripsi = in.readString();
        no_hp = in.readString();
        detail_id = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    @Override
    public String toString() {
        return "UserPedagang{" +
                "user_id='" + user_id + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", no_hp='" + no_hp + '\'' +
                ", detail_id='" + detail_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(deskripsi);
        dest.writeString(no_hp);
        dest.writeString(detail_id);
    }
}

