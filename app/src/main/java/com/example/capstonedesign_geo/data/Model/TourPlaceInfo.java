package com.example.capstonedesign_geo.data.Model;

public class TourPlaceInfo {
    private int placeId;
    private String placeName;
    private String placeTel;
    private String placeAddress;
    private String placeCategory;
    private String placeDescription;
    private String placeUrl;
    private double latitude;
    private double longitude;

    public TourPlaceInfo(int placeId, String placeName, String placeTel, String placeAddress, String placeCategory, String placeDescription, String placeUrl, double latitude, double longitude) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeTel = placeTel;
        this.placeAddress = placeAddress;
        this.placeCategory = placeCategory;
        this.placeDescription = placeDescription;
        this.placeUrl = placeUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getPlaceId() {return placeId;}
    public void setPlaceId(int placeId) {this.placeId = placeId;}

    public String getPlaceName() {return placeName;}
    public void setPlaceName(String placeName) {this.placeName = placeName;}

    public String getPlaceTel() {return placeTel;}
    public void setPlaceTel(String placeTel) {this.placeTel = placeTel;}

    public String getPlaceAddress() {return placeAddress;}
    public void setPlaceAddress(String placeAddress) {this.placeAddress = placeAddress;}

    public String getPlaceCategory() {return placeCategory;}
    public void setPlaceCategory(String placeCategory) {this.placeCategory = placeCategory;}

    public String getPlaceDescription() {return placeDescription;}
    public void setPlaceDescription(String placeDescription) {this.placeDescription = placeDescription;}

    public String getPlaceUrl() {return placeUrl;}
    public void setPlaceUrl(String placeUrl) {this.placeUrl = placeUrl;}

    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}
}
