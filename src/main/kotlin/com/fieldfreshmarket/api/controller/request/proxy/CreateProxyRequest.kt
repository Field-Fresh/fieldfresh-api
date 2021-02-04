package com.fieldfreshmarket.api.controller.request.proxy

import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import org.springframework.lang.NonNull

class CreateProxyRequest(
    @get:NonNull
    val userId: String,
    @get:NonNull
    val name: String,

    val description: String?,
    @get:NonNull
    val streetAddress: String,
    @get:NonNull
    val city: String,
    @get:NonNull
    val province: String,
    @get:NonNull
    val country: String,
    @get:NonNull
    val postalCode: String,
    @get:NonNull
    val lat: Double,
    @get:NonNull
    val long: Double
) {
    fun toData(): CreateProxyData =
        CreateProxyData(
            userId = userId,
            name = name,
            description = description,
            streetAddress = streetAddress,
            city = city,
            province = province,
            country = country,
            postalCode = postalCode,
            lat = lat,
            long = long,
        )
}
