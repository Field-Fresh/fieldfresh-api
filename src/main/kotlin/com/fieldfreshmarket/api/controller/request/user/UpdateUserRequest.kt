package com.fieldfreshmarket.api.controller.request.user

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.user.UpdateUserData

class UpdateUserRequest(
    val firstName: String?,
    val lastName: String?,
    val phone: String?

) {
    fun toData(grant: AccessGrant): UpdateUserData =
        UpdateUserData(
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            email = grant.accessTokenClaims.email
        )
}
