package com.example.capstonedesign_geo.data.Model;

import java.util.Set;

// 사용자의 데이터를 나타내는 데이터 모델 클래스
// 데이터베이스에서 가져온 사용자 데이터를 객체 형태로 관리
// 앱의 다른 곳에서 이 객체를 통해 사용자 데이터를 조작
public class UserInfo {
    private String userId;
    private String androidId;
    private String nickname;
    private boolean userType;
    private int age;
    private String location;
    private Set<String> favoriteTags;

    public UserInfo(String userId, String androidId, String nickname, boolean userType, int age, String location, Set<String> favoriteTags){
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

    public Set<String> getFavoriteTags() {return favoriteTags;}
    public void setFavoriteTags(Set<String> favoriteTags) {this.favoriteTags = favoriteTags;}

}
