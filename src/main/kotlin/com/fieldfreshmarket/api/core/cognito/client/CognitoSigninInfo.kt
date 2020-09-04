package com.fieldfreshmarket.api.core.cognito.client

interface CognitoSigninInfo {
   val email: String
   val password: String?
}