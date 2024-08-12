package com.example.capstonedesign_geo.data.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserInfoTemp implements Parcelable {
    private String userId;
    private String androidId;
    private String nickname;
    private boolean userType;
    private int age;
    private String location;
    private String favoriteTags;

    public UserInfoTemp() {}

    public UserInfoTemp(String userId, String androidId, String nickname, boolean userType, int age, String location, String favoriteTags){
        this.userId = userId;
        this.androidId = androidId;
        this.nickname = nickname;
        this.userType = userType;
        this.age = age;
        this.location = location;
        this.favoriteTags = favoriteTags;
    }

    protected UserInfoTemp(Parcel in){
        userId = in.readString();
        androidId = in.readString();
        nickname = in.readString();
        userType = in.readByte() != 0;
        age = in.readInt();
        location = in.readString();
        favoriteTags = in.readString();
    }

    public static final Creator<UserInfoTemp> CREATOR = new Creator<UserInfoTemp>() {
        @Override
        public UserInfoTemp createFromParcel(Parcel in) {
            return new UserInfoTemp(in);
        }

        @Override
        public UserInfoTemp[] newArray(int size) {
            return new UserInfoTemp[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(androidId);
        dest.writeString(nickname);
        dest.writeByte((byte) (userType ? 1 : 0));
        dest.writeInt(age);
        dest.writeString(location);
        dest.writeString(favoriteTags);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isUserType() {
        return userType;
    }

    public void setUserType(boolean userType) {
        this.userType = userType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFavoriteTags() {
        return favoriteTags;
    }

    public void setFavoriteTags(String favoriteTags) {
        this.favoriteTags = favoriteTags;
    }
}
