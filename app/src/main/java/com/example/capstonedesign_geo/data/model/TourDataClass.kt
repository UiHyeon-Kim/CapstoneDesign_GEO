package com.example.capstonedesign_geo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class TourApiResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)
data class Header (
    val resultCode: String,
    val resultMsg: String
)

data class Body (
    val items: Items
)

data class Items (
    val item: List<Item>
)

@Parcelize
data class Item (
    val addr1: String,
    val addr2: String?,
    val areacode: String,
    val booktour: String?,
    val cat1: String?,
    val cat2: String?,
    val cat3: String?,
    val contentid: String,
    val contenttypeid: String,
    val createdtime: String,
    val firstimage: String?,
    val firstimage2: String?,
    val cpyrhtDivCd: String?,
    val mapx: String,
    val mapy: String,
    val mlevel: String,
    val modifiedtime: String,
    val sigungucode: String?,
    val tel: String?,
    val title: String,
    val zipcode: String?
): Parcelable


/*
data class TourDetaileResponse(
    val firstImage: String?,
    val title: String,
    val addr1: String,
    val addr2: String
)*/
