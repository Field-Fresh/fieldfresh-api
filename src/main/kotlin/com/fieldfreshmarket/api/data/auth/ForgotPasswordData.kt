package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoForgotPasswordInfo


data class ForgotPasswordData (
    override val email: String
): CognitoForgotPasswordInfo