package com.fieldfreshmarket.api.data.user

import com.fieldfreshmarket.api.core.cognito.client.CognitoUpdateInfo


data class UpdateUserData (
   override val firstName: String? = null,
   override val lastName: String? = null ,
   override val phone: String? = null,
   override val email: String
) : CognitoUpdateInfo