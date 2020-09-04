package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.SignUpConfirmationData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignUpConfirmationRequest (

   val password: String?,

   @get:NotNull
   @get:Email
   val email: String?,

   @get:NotNull
   val code: String?

) {
   fun toData(): SignUpConfirmationData =
      SignUpConfirmationData(
         email = email!!,
         password = password,
         code = code!!
      )
}