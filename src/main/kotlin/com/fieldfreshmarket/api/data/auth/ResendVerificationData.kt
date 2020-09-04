package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoResendVerificationInfo

data class ResendVerificationData (
   override val email: String
): CognitoResendVerificationInfo