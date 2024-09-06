package com.example.capstonedesign_geo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstonedesign_geo.data.model.User
import com.example.capstonedesign_geo.domain.usecase.GetUserUseCase

class UserViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _user = MutableLiveData<User>() // 사용자 정보를 저장하는 LiveData
    val user: LiveData<User> get() = _user // LiveData를 읽기 전용으로 제공

    fun loadUser(userId: String) {
        _user.value = getUserUseCase.execute(userId) // Use case를 통해 데이터 가져오기
    }
}