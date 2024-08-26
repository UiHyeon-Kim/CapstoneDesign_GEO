package com.example.capstonedesign_geo.data.model;

public class Place {
    private String img;
    private String name;
    private String category;
    private String address;
    private String open;

    public Place(String img, String name, String category, String address, String open) {
        this.img = img;
        this.name = name;
        this.category = category;
        this.address = address;
        this.open = open;
    }

    public String getImg() {
        return img;
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
