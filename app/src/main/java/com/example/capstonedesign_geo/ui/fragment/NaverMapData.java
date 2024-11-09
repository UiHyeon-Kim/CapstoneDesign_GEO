package com.example.capstonedesign_geo.ui.fragment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "naver_map_data")
public class NaverMapData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("category")
    private String category;

    @SerializedName("addr1")
    private String addr1;

    @SerializedName("addr2")
    private String addr2;

    @SerializedName("hours")
    private String hours;

    @SerializedName("firstimage")
    private String firstimage;

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

    @SerializedName("disabled_content")
    private String disabled_content;

    @SerializedName("amenity")
    private String amenity;

    @SerializedName("disabled")
    private String disabled;

    protected NaverMapData(Parcel in) {
        id = in.readInt();
        category = in.readString();
        addr1 = in.readString();
        addr2 = in.readString();
        hours = in.readString();
        firstimage = in.readString();
        mapx = in.readDouble();
        mapy = in.readDouble();
        tel = in.readString();
        title = in.readString();
        content = in.readString();
        disabled_content = in.readString();
        amenity = in.readString();
        disabled = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(category);
        dest.writeString(addr1);
        dest.writeString(addr2);
        dest.writeString(hours);
        dest.writeString(firstimage);
        dest.writeDouble(mapx);
        dest.writeDouble(mapy);
        dest.writeString(tel);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(disabled_content);
        dest.writeString(amenity);
        dest.writeString(disabled);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NaverMapData> CREATOR = new Creator<NaverMapData>() {
        @Override
        public NaverMapData createFromParcel(Parcel in) {
            return new NaverMapData(in);
        }

        @Override
        public NaverMapData[] newArray(int size) {
            return new NaverMapData[size];
        }
    };

    public NaverMapData() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(String firstimage) {this.firstimage = firstimage;}

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisabled_content() {
        return disabled_content;
    }

    public void setDisabled_content(String content) {
        this.disabled_content = disabled_content;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }
}
