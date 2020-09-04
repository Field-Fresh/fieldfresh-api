package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoConfirmForgotPasswordInfo

data class ConfirmForgotPasswordData (
    override val email: String,
    override val newPassword: String,
    override val code: String
): CognitoConfirmForgotPasswordInfo