package com.example.capstonedesign_geo.data.model;

public class Place {
    private String placeId;
    private String placeImg;
    private String name;
    private String category;
    private String address;
    private String open;

    public Place(String placeId, String placeImg, String name, String category, String address, String open) {
        this.placeId = placeId;
        this.placeImg = placeImg;
        this.name = name;
        this.category = category;
        this.address = address;
        this.open = open;
    }

    public String getPlaceId() { return placeId; }

    public String getPlaceImg() {
        return placeImg;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getOpen() {
        return open;
    }
}
