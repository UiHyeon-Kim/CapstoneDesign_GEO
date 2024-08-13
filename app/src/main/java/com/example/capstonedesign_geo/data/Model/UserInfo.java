package com.example.capstonedesign_geo.data.Model;

// 사용자 정보를 저장할 클래스
public class UserInfo {
    private String userId;
    private String androidId;
    private String nickname;
    private boolean userType;
    private int age;
    private String location;
    private String favoriteTags;

    public UserInfo(String userId, String androidId, String nickname, boolean userType, int age, String location, String favoriteTags){
        this.userId = userId;
        this.androidId = androidId;
        this.nickname = nickname;
        this.userType = userType;
        this.age = age;
        this.location = location;
        this.favoriteTags = favoriteTags;
    }

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getAndroidId() {return androidId;}
    public void setAndroidId(String androidId) {this.androidId = androidId;}

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname=nickname;}

    public boolean UserType() {return userType;}
    public void setUserType(boolean userType) {this.userType = userType;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getFavoriteTags() {return favoriteTags;}
    public void setFavoriteTags(String favoriteTags) {this.favoriteTags = favoriteTags;}
}
