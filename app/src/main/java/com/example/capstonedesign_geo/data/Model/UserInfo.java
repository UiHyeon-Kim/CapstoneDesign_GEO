package com.example.capstonedesign_geo.data.Model;

public class UserInfo {
    private int userId;
    private String androidId;
    private String nickname;
    private boolean userType;
    private int age;
    private String location;
    private boolean hasPet;
    private String favoriteTags;

    public UserInfo(String nickname, boolean userType, int age, String location, boolean hasPet, String favoriteTags){
        this.nickname = nickname;
        this.userType = userType;
        this.age = age;
        this.location = location;
        this.hasPet = hasPet;
        this.favoriteTags = favoriteTags;
    }

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname=nickname;}

    public boolean UserType() {return userType;}
    public void setUserType(boolean userType) {this.userType = userType;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public boolean HasPet() {return hasPet;}
    public void setHasPet(boolean hasPet) {this.hasPet = hasPet;}

    public String getFavoriteTags() {return favoriteTags;}
    public void setFavoriteTags(String favoriteTags) {this.favoriteTags = favoriteTags;}
}
