package com.example.capstonedesign_geo.ui.fragment;

import com.google.gson.annotations.SerializedName;

public class NaverMapData {
    @SerializedName("num")
    private int num;

    @SerializedName("addr1")
    private String addr1;

    @SerializedName("addr2")
    private String addr2;

    @SerializedName("hours")
    private String hours;

    @SerializedName("mapx")
    private double mapx;

    @SerializedName("mapy")
    private double mapy;

    @SerializedName("tel")
    private String tel;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("amenity")
    private String amenity;

    @SerializedName("disabled")
    private String disabled;

    public int getnum() {
        return num;
    }

    public String getaddr1() {
        return addr1;
    }

    public String getaddr2() {
        return addr2;
    }

    public String gethours() {
        return hours;
    }

    public double getmapx() {
        return mapx;
    }

    public double getmapy() { return mapy; }

    public String gettel() {
        return tel;
    }

    public String gettitle() {
        return title;
    }

    public String getcontent() {
        return content;
    }

    public String getamenity() {
        return amenity;
    }

    public String getdisabled() {
        return disabled;
    }
}
