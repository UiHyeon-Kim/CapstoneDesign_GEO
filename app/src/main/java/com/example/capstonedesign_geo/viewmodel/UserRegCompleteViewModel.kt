package com.example.capstonedesign_geo.viewmodel

import com.example.capstonedesign_geo.data.dao.UserDao
import com.example.capstonedesign_geo.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRegCompleteViewModel(private val userDao: UserDao) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }
}