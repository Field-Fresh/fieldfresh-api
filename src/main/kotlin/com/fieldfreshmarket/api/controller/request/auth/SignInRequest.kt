package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.SignInData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignInRequest (

   @get:NotNull
   @get:Email
   val email: String?,

   @get:NotNull
   val password: String?

) {
   fun toData(): SignInData =
      SignInData(
         email = email!!,
         password = password!!
      )
}