package com.fieldfreshmarket.api.repository

import com.fieldfreshmarket.api.model.User

interface UsersRepository: BaseRepository<User> {
    fun findByEmail(email: String): User?
    fun findByCognitoSub(userId: String): User?
    fun findById(id: String): User?
}