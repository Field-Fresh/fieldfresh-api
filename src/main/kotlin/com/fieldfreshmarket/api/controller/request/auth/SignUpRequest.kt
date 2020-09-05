package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.SignUpData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignUpRequest (

   @get:Email
   @get:NotNull
   val email: String?,

   @get:NotNull
   val password: String?

) {
   fun toData(): SignUpData =
      SignUpData(
         email = email!!,
         password = password!!
      )
}