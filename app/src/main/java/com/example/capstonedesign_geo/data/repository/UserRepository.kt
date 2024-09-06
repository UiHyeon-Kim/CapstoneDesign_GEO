package com.example.capstonedesign_geo.data.repository

import com.example.capstonedesign_geo.data.model.User

interface UserRepository {
    fun getUserById(userId: String): User?
    fun insertUser(user: User)
}