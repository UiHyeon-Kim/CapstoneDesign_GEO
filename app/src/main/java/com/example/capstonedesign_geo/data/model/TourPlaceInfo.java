package com.example.capstonedesign_geo.data.model;

public class TourPlaceInfo {
    private int placeId;        // 장소 아이디
    private String placeName;   // 장소 이름
    private String placeTel;    // 장소 전화번호
    private String placeAddress;// 장소 주소
    private String placeDescription; // 장소 설명
    private int placeAreaCode;  // 장소 지역 코드
    private int placeCategory1; // 장소 대분류
    private int placeCategory2; // 장소 중분류
    private int placeCategory3; // 장소 소분류
    private int placeType;      // 장소 타입(관광지, 숙박 등)
    private int placeRadius;    // 장소 반경
    private String placeUrl;    // 장소 URL
    private String placeImgUrl; // 이미지 URL
    private String placeThumbnailUrl; // 썸네일 이미지 URL
    private String placeKeyword;// 장소 키워드
    private double latitude;    // 위도(y)
    private double longitude;   // 경도(x)
    // 운영시간 추가하기

    public TourPlaceInfo(int placeId, String placeName, String placeTel, String placeAddress, String placeDescription, int placeAreaCode, int placeCategory1, int placeCategory2, int placeCategory3, int placeType, int placeRadius, String placeUrl, String placeImgUrl, String placeThumbnailUrl, String placeKeyword, double latitude, double longitude) {
        this.placeId = placeId;
        this.placeName = placeName;
        this.placeTel = placeTel;
        this.placeAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeAreaCode = placeAreaCode;
        this.placeCategory1 = placeCategory1;
        this.placeCategory2 = placeCategory2;
        this.placeCategory3 = placeCategory3;
        this.placeType = placeType;
        this.placeRadius = placeRadius;
        this.placeUrl = placeUrl;
        this.placeImgUrl = placeImgUrl;
        this.placeThumbnailUrl = placeThumbnailUrl;
        this.placeKeyword = placeKeyword;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceTel() {
        return placeTel;
    }

    public void setPlaceTel(String placeTel) {
        this.placeTel = placeTel;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) { this.placeDescription = placeDescription; }

    public int getPlaceAreaCode() {
        return placeAreaCode;
    }

    public void setPlaceAreaCode(int placeAreaCode) {
        this.placeAreaCode = placeAreaCode;
    }

    public int getPlaceCategory1() {
        return placeCategory1;
    }

    public void setPlaceCategory1(int placeCategory1) {
        this.placeCategory1 = placeCategory1;
    }

    public int getPlaceCategory2() {
        return placeCategory2;
    }

    public void setPlaceCategory2(int placeCategory2) {
        this.placeCategory2 = placeCategory2;
    }

    public int getPlaceCategory3() {
        return placeCategory3;
    }

    public void setPlaceCategory3(int placeCategory3) {
        this.placeCategory3 = placeCategory3;
    }

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public int getPlaceRadius() {
        return placeRadius;
    }

    public void setPlaceRadius(int placeRadius) {
        this.placeRadius = placeRadius;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getPlaceImgUrl() {
        return placeImgUrl;
    }

    public void setPlaceImgUrl(String placeImgUrl) {
        this.placeImgUrl = placeImgUrl;
    }

    public String getPlaceThumbnailUrl() {
        return placeThumbnailUrl;
    }

    public void setPlaceThumbnailUrl(String placeThumbnailUrl) { this.placeThumbnailUrl = placeThumbnailUrl; }

    public String getPlaceKeyword() {
        return placeKeyword;
    }

    public void setPlaceKeyword(String placeKeyword) {
        this.placeKeyword = placeKeyword;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

