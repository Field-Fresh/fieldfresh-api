package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.SignOutData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignOutRequest (

   @get:NotNull
   @get:Email
   val email: String?

) {
   fun toData(): SignOutData =
      SignOutData(
         email = email!!
      )
}