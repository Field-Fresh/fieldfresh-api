package com.fieldfreshmarket.api.core.cognito.client

interface CognitoSignupConfirmationInfo {
   val email: String
   val code: String
   val password: String?
}