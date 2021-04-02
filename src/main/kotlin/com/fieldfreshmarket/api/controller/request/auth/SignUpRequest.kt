package com.fieldfreshmarket.api.controller.request.auth

import com.fieldfreshmarket.api.controller.request.proxy.CreateProxyRequest
import com.fieldfreshmarket.api.data.auth.SignUpData
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignUpRequest(

    @get:Email
    @get:NotNull
    val email: String?,

    @get:NotNull
    val password: String?,

    val firstName: String?,
    val lastName: String?,
    val phone: String?,

    @get:NotNull
    val proxy: CreateProxyRequest

) {
    fun toData(): SignUpData =
        SignUpData(
            email = email!!,
            password = password!!,
            proxy = proxy.toData(),
            firstName = firstName,
            lastName = lastName,
            phone = phone
        )
}