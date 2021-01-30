package com.fieldfreshmarket.api.data.proxy

import com.fieldfreshmarket.api.core.cognito.client.CognitoUpdateInfo


data class CreateProxyData(
    val userId: String,
    val name: String,
    val description: String?,
    val streetAddress: String,
    val city: String,
    val province: String,
    val country: String,
    val postalCode: String,
    val lat: Double,
    val long: Double,
)