package com.example.capstonedesign_geo.data.repository

import com.example.capstonedesign_geo.data.model.Place

interface PlaceRepository {
    fun insertPlace(place: Place)
}