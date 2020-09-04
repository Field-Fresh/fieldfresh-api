package com.fieldfreshmarket.api.data.user

import com.fieldfreshmarket.api.core.cognito.client.CognitoChangePasswordInfo

data class ChangePasswordData (
    override val newPassword: String,
    override val oldPassword: String,
    override val token: String,
    override val sub: String
): CognitoChangePasswordInfo
