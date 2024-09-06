package com.example.capstonedesign_geo.data.repository

import com.example.capstonedesign_geo.data.dao.UserDao
import com.example.capstonedesign_geo.data.model.User

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override fun getUserById(userId: String): User? { return userDao.getUserById(userId) }
    override fun insertUser(user: User) = userDao.insertUser(user)
}