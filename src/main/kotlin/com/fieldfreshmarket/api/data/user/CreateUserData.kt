package com.fieldfreshmarket.api.data.user

data class CreateUserData (
    val firstName: String? = null,
    val lastName: String? = null,
    val cognitoSub: String? = null,
    val phone: String? = null,
    val email: String,
    val verified: Boolean
)