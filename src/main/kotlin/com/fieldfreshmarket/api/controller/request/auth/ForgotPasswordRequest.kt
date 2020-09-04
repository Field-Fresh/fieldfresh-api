package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.ForgotPasswordData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class ForgotPasswordRequest (

    @get:Email
    @get:NotNull
    val email: String?

) {
  fun toData(): ForgotPasswordData =
      ForgotPasswordData(
          email = email!!
      )
}