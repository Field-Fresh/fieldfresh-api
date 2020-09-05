package com.fieldfreshmarket.api.core.cognito.client

interface CognitoConfirmForgotPasswordInfo {
  val email: String
  val newPassword: String
  val code: String
}