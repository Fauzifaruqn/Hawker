package com.example.learnmaps.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserPedagang implements Parcelable {

    private String email;
    private String user_id;
    private String username;
    private String avatar;
    private String kategori_id;
    private String deskripsi;
    private String no_hp;

    public UserPedagang(String email, String user_id, String username, String avatar, String kategori_id, String deskripsi, String no_hp) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.avatar = avatar;
        this.kategori_id = kategori_id;
        this.deskripsi = deskripsi;
        this.no_hp = no_hp;
    }

    public UserPedagang() {

    }

    protected UserPedagang(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        avatar = in.readString();
        kategori_id = in.readString();
        deskripsi = in.readString();
        no_hp = in.readString();
    }

    public static final Creator<UserPedagang> CREATOR = new Creator<UserPedagang>() {
        @Override
        public UserPedagang createFromParcel(Parcel in) {
            return new UserPedagang(in);
        }

        @Override
        public UserPedagang[] newArray(int size) {
            return new UserPedagang[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static Creator<UserPedagang> getCREATOR() {
        return CREATOR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(String kategori_id) {
        this.kategori_id = kategori_id;
    }

    @Override
    public String toString() {
        return "UserPedagang{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", kategori_id='" + kategori_id + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", no_hp='" + no_hp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(avatar);
        dest.writeString(kategori_id);
        dest.writeString(deskripsi);
        dest.writeString(no_hp);
    }
}

