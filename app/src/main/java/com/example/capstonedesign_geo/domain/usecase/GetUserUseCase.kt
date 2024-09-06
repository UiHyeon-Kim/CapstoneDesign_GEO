package com.example.capstonedesign_geo.domain.usecase

import com.example.capstonedesign_geo.data.model.User
import com.example.capstonedesign_geo.data.repository.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {

    fun execute(userId: String): User? {
        return userRepository.getUserById(userId)
    }
}