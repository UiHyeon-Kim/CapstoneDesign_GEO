package com.example.capstonedesign_geo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String,
    val androidId: String,
    val nickname: String,
    val userType: Boolean,
    val age: Int,
    val location: String,
    // val favoriteTags: Set<String> = emptySet()
)