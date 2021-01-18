package com.fieldfreshmarket.api.controller.request.auth

import javax.validation.constraints.NotNull

class RefreshRequest (
   @get:NotNull
   val refreshToken: String
)