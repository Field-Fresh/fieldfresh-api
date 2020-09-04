package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.ResendVerificationData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class ResendVerificationRequest (

   @get:NotNull
   @get:Email
   val email: String?

) {
   fun toData(): ResendVerificationData =
      ResendVerificationData(
         email = email!!
      )
}