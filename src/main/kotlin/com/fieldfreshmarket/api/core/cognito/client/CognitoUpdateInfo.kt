package com.fieldfreshmarket.api.core.cognito.client

interface CognitoUpdateInfo {
  val firstName: String?
  val lastName: String?
  val phone: String?
  val email: String
}