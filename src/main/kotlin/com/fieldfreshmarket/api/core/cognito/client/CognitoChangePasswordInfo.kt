package com.fieldfreshmarket.api.core.cognito.client

interface CognitoChangePasswordInfo {
  val newPassword: String
  val oldPassword: String
  val token: String
  val sub: String
}