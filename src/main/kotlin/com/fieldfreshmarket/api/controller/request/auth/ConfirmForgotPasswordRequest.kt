package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.data.auth.ConfirmForgotPasswordData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class ConfirmForgotPasswordRequest (

    @get:Email
    @get:NotNull
    val email: String?,
    @get:NotNull
    val newPassword: String?,
    @get:NotNull
    val code: String?

) {
  fun toData(): ConfirmForgotPasswordData =
      ConfirmForgotPasswordData(
          email = email!!,
          code = code!!,
          newPassword = newPassword!!
      )
}